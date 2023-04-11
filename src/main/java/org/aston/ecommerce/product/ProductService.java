package org.aston.ecommerce.product;

import java.util.*;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.aston.ecommerce.file.FileService;
import org.aston.ecommerce.file.ImageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final FileService fileService;
    private final double markupMultiplier = 0.80;

    @Autowired
    public ProductService(ProductRepository productRepository, FileService fileService) {
        this.productRepository = productRepository;
        this.fileService = fileService;
    }

    public List<Product> search(String query) {
        return productRepository.findByNameContainingIgnoreCase(query);
    }

    public List<Product> search(Pageable pageRequest) {
        Page<Product> productsPage = this.productRepository.findAll(pageRequest);
        return productsPage.getContent();
    }

    public List<Product> findByCategory(String categoryStr) {
        Category category = Category.parseCategoryStr(categoryStr.toUpperCase());
        if (category != null) {
            return this.productRepository.findAllByCategory(category);
        }
        return null;
    }

    public Category returnCategoryFromString(String categoryStr){
        return Category.parseCategoryStr(categoryStr.toUpperCase());
    }

    public Optional<List<Product>> findAll() {
        return Optional.of(this.productRepository.findAll());
    }

    //Find all products alongside their image
    public List<ProductImage> findAllProductImage(List<Product> products){
        List<ProductImage> returnProductImages = new ArrayList<>();

        for(Product product : products){
            ImageInfo imageInfo = this.fileService.getImageByName(product.getUrl());
            if(imageInfo != null){
                imageInfo.setUrl(imageInfo.getUrl().replaceAll("http://localhost:10293", ""));
            }
            ProductImage productImage = new ProductImage(product, imageInfo);
            returnProductImages.add(productImage);
        }

        return returnProductImages;
    }

    //Find all products by ascending order such that the products with the highest stock are shown first.
    public List<Product> findAllProductsInAscendingOrder(){
        List<Product> returnProducts = this.productRepository.findAll();
        Collections.sort(returnProducts, (Product p1, Product p2) -> p2.getAmountAvailable() - p1.getAmountAvailable());
        return returnProducts;
    }

    //Find all products by ascending order within their categories
    public List<Product> findAllProductsInAscendingOrderCategory(){
        List<Product> returnProducts = this.productRepository.findAll();
        Collections.sort(returnProducts, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                if(p1.getCategory() == p2.getCategory()){
                    //DESCENDING
                    return p2.getAmountAvailable().compareTo(p1.getAmountAvailable());
                }else{
                    return p1.getCategory().compareTo(p2.getCategory());
                }
            }
        });
        return returnProducts;
    }

    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    //Find products that should be amber as per the admin alert system in ASCENDING order such that the products with the lowest stock are shown first.
    public List<Product> findAmberProducts(){
        List<Product> returnProducts = this.productRepository.findAll()
                .stream()
                .filter(c -> c.getAmountAvailable() <= 20 && c.getAmountAvailable() >= 11)
                .collect(Collectors.toList());
        Collections.sort(returnProducts, (Product p1, Product p2) -> p1.getAmountAvailable() - p2.getAmountAvailable());
        return returnProducts;
    }

    //Find products that should be red as per the admin alert system in ASCENDING order such that the products with the lowest stock are shown first.
    public List<Product> findRedProducts(){
        List<Product> returnProducts = this.productRepository.findAll()
                .stream()
                .filter(c -> c.getAmountAvailable() <= 10)
                .collect(Collectors.toList());
        Collections.sort(returnProducts, (Product p1, Product p2) -> p1.getAmountAvailable() - p2.getAmountAvailable());
        return returnProducts;
    }
    
    public double getMarkupMultiplier() {
        return markupMultiplier;
    }
}
