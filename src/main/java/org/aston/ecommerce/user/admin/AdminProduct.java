package org.aston.ecommerce.user.admin;

import org.aston.ecommerce.file.FileStorage;
import org.aston.ecommerce.product.Category;
import org.aston.ecommerce.product.ProductRepository;
import org.aston.ecommerce.product.Product;
import org.aston.ecommerce.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class AdminProduct {

    private final ProductService productService;

    private final ProductRepository productRepository;

    private final FileStorage fileStorageService;

    @Autowired
    public AdminProduct(ProductRepository productRepository, ProductService productService, FileStorage fileStorageService) {
        this.productRepository = productRepository;
        this.productService = productService;
        this.fileStorageService = fileStorageService;
    }
    @GetMapping("/admin/products")
    public String displayProducts(Model model) {
        model.addAttribute("products", this.productService.findAll().get());
        model.addAttribute("amberProducts", this.productService.findAmberProducts());
        model.addAttribute("emptyProducts", this.productService.findProductsOutOfStock());
        return "admin_products";
    }
    //TODO: Finish This
    @GetMapping("admin/products/get")
    public String getProducts(Model model, @RequestParam(name="searchQuery", required = false)String query) {
        List<Product> products = null;
        if(query == null) {
            //Display All
            Pageable limit = PageRequest.of(0,100);
            products = this.productService.search(limit);
        } else {
            //For future searching? if time
        }
        return "admin_products";
    }

    @GetMapping("/admin/products/{id}")
    public String getProduct(Model model, @PathVariable("id") String id) {

        Long longId = null;
        //If number is not supplied for url param 'id', then immediately redirect back to admin products
        try{
            longId = Long.parseLong(id);
        }catch(Exception e){
            return "redirect:/admin/products";
        }

        Optional<Product> optProduct = this.productRepository.findById(longId);
        if(!optProduct.isPresent()){
            return "redirect:/admin/products";
        }

        Product product = optProduct.get();
        model.addAttribute("productId", id);
        model.addAttribute("productName", product.getName());
        model.addAttribute("productDescription", product.getDescription());
        model.addAttribute("productCategory", product.getCategory().name().toLowerCase());
        model.addAttribute("productAmount", product.getAmount());
        model.addAttribute("productAmountAvailable", product.getAmountAvailable());
        return "admin_product_edit";
    }

    @PostMapping("/admin/products/{id}")
    public String updateProduct(@PathVariable("id") String id,
                                @RequestParam("name") String nameStr,
                                @RequestParam("description") String descriptionStr,
                                @RequestParam("amount") Double amountD,
                                @RequestParam("amountAvailable") Integer amountAvailableI,
                                @RequestParam("category") String categoryStr,
                                @RequestParam("file") MultipartFile file,
                                RedirectAttributes redirectAttrs) {
        Category category = this.productService.returnCategoryFromString(categoryStr);

        Optional<Product> optProduct = this.productRepository.findById(Long.parseLong(id));
        Product editProduct = optProduct.get();
        editProduct.setName(nameStr); editProduct.setDescription(descriptionStr);
        editProduct.setAmount(amountD); editProduct.setAmountAvailable(amountAvailableI);
        editProduct.setCategory(category);

        if(!this.fileStorageService.isFileAcceptable(file)){
            redirectAttrs.addFlashAttribute("fail_msg", "Error! File was not either .png or .jpg");
            return "redirect:/admin/products/" + id;
        }

        editProduct.setUrl(file.isEmpty() ? null : file.getOriginalFilename());

        try{
            this.productRepository.save(editProduct);
        }catch(Exception e){
            redirectAttrs.addFlashAttribute("fail_msg", "Error! Failed to add product");
            return "redirect:/admin/products/" + id;
        }

        //File upload is optional, so return now if image upload was not given
        if(file.isEmpty()){
            redirectAttrs.addFlashAttribute("success_msg", "true");
            return "redirect:/admin/products/" + id;
        }

        try {
            this.fileStorageService.save(file);
        } catch (Exception e) {
            //Do not return error if the file already exists. Just do not upload that's all.
            if(!(e.getMessage().equals("That filename already exists."))){
                redirectAttrs.addFlashAttribute("fail_msg", "Could not upload the image: " + file.getOriginalFilename() + ". Error: " + e.getMessage());
                return "redirect:/admin/products/" + id;
            }
        }
        
        redirectAttrs.addFlashAttribute("success_msg", "true");
        return "redirect:/admin/products/" + id;
    }

    @GetMapping("/admin/products/new")
    public String showAddForm(Model model, Authentication authentication) {
//        if(authentication != null) {
//            System.out.println(authentication.getAuthorities());
//        }
        //model.addAttribute("product", new Product());
        return "admin_add_product";
    }
    //TODO: Handle category input
    @PostMapping("/admin/products/new")
    public String addProduct(Model model, @RequestParam("name") String nameStr,
                                     @RequestParam("description") String descriptionStr,
                                     @RequestParam("amount") Double amountD,
                                     @RequestParam("amountAvailable") Integer amountAvailableI,
                                     @RequestParam("file") MultipartFile file,
                                     @RequestParam("category") String categoryStr) {

        Category category = this.productService.returnCategoryFromString(categoryStr);

        Product newProduct = new Product();
        newProduct.setName(nameStr); newProduct.setDescription(descriptionStr);
        newProduct.setAmount(amountD); newProduct.setAmountAvailable(amountAvailableI);
        newProduct.setCategory(category);

        if(!this.fileStorageService.isFileAcceptable(file)){
            model.addAttribute("fail_msg", "Error! File was not either .png or .jpg");
            return "admin_add_product";
        }

        newProduct.setUrl(file.isEmpty() ? null : file.getOriginalFilename());

        try{
            this.productRepository.save(newProduct);
        }catch(Exception e){
            model.addAttribute("fail_msg", "Error! Failed to add product");
            return "admin_add_product";
        }

        //File upload is optional, so return now if image upload was not given
        if(file.isEmpty()) return "redirect:/admin/products";

        try {
            this.fileStorageService.save(file);
        } catch (Exception e) {
            //Do not return error if the file already exists. Just do not upload that's all.
            if(!(e.getMessage().equals("That filename already exists."))){
                model.addAttribute("fail_msg", "Could not upload the image: " + file.getOriginalFilename() + ". Error: " + e.getMessage());
                return "admin_add_product";
            }
        }

        return "redirect:/admin/products";
    }

    @GetMapping("/admin/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") String id,
                                RedirectAttributes redirectAttrs){

        Long longId = null;
        //If number is not supplied for url param 'id', then immediately redirect back to admin products
        try{
            longId = Long.parseLong(id);
        }catch(Exception e){
            return "redirect:/admin/products";
        }

        //Also immediately redirect back if 'id' is not valid
        Optional<Product> optProduct = this.productRepository.findById(longId);
        if(!optProduct.isPresent()){
            return "redirect:/admin/products";
        }

        try{
            this.productRepository.deleteById(longId);
        }catch(Exception e){
            redirectAttrs.addFlashAttribute("fail_msg", "Error! Failed to delete product.");
            return "redirect:/admin/products/" + id;
        }

        return "redirect:/admin/products";
    }

}
