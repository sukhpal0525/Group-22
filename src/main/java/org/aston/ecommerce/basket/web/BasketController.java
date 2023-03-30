package org.aston.ecommerce.basket.web;

import org.aston.ecommerce.basket.Basket;
import org.aston.ecommerce.basket.BasketItem;
import org.aston.ecommerce.basket.BasketService;
import org.aston.ecommerce.user.CustomUserDetails;
import org.aston.ecommerce.user.User;
import org.aston.ecommerce.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Optional;

@Controller
@RequestMapping("/basket")
public class BasketController {

    @Autowired private UserRepository userRepo;
    @Autowired private BasketService basketService;

    @GetMapping
    public String viewBasket(Model model, HttpSession session) {
        System.out.println(session.getAttribute("BASKET_ID"));
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails) {
            String username = ((CustomUserDetails) principal).getUsername();
            User loggedInUser = userRepo.findByEmail(username);
            //See if the currently logged-in user has an empty basket or not
            if (loggedInUser.isBasketEmpty()) {
                model.addAttribute("empty", "yes");
            } else {
                model.addAttribute("listBaskets", loggedInUser.getBasket().getBasketItems());
                model.addAttribute("basketTotal", this.basketService.getTotalOfBasket(loggedInUser.getBasket()));
            }
        } else {
            model.addAttribute("isNotLoggedIn", "yes");
        }
        return "basket";
    }

    @GetMapping("/delete/{id}")
    public String deleteBasketItem(Model model, @PathVariable("id") String id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails) {
            String username = ((CustomUserDetails) principal).getUsername();
            User loggedInUser = userRepo.findByEmail(username);
            Basket basket = loggedInUser.getBasket();
            BasketItem toDelete = basket.getBasketItems().stream()
                    .filter((item) -> item.getId().equals(Long.parseLong(id)))
                    .findFirst()
                    .orElse(null);
            if (toDelete != null) {
                basket.getBasketItems().remove(toDelete);
                userRepo.save(loggedInUser);
            }
        } else {
            model.addAttribute("isNotLoggedIn", "yes");
        }
        return "redirect:/basket";
    }

    @PostMapping("/add")
    public String processRegister(
            @RequestParam("numOrdered") String numOrderedStr,
            @RequestParam("productId") String productIdStr,
            RedirectAttributes redirectAttrs,
            HttpServletRequest request) {

        //See if user is logged in
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //If user is not logged in, then send them to the login page because they need to be logged-in in order to add a product to their basket
        if (!(principal instanceof CustomUserDetails)) {
            request.getSession().setAttribute("BASKET_ID", "zzz");
            return "redirect:/login";
        }
        //Find currently logged-in user
        String username = ((CustomUserDetails) principal).getUsername();
        User loggedInUser = userRepo.findByEmail(username);

        //If user is admin, then do not allow them to add item to basket
        if(loggedInUser.isAdmin()){
            redirectAttrs.addFlashAttribute("purchase_fail", "Error! Admin users cannot add products to their basket.");
            return "redirect:/product/" + Long.parseLong(productIdStr);
        }

        Integer numOrdered = Integer.parseInt(numOrderedStr);
        Long productId = Long.parseLong(productIdStr);

        boolean purchaseSuccess = this.basketService.addItemToBasket(productId, numOrdered, loggedInUser);
        if (purchaseSuccess) {
            redirectAttrs.addFlashAttribute("purchase_success", "yes");
        } else {
            redirectAttrs.addFlashAttribute("purchase_fail", "Error! You tried to order more products than there is currently available in stock.");
        }
        return "redirect:/product/" + productId;
    }

    @GetMapping("/edit/{id}")
    public String editBasketItem(Model model, @PathVariable("id") String id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails) {
            String username = ((CustomUserDetails) principal).getUsername();
            User loggedInUser = userRepo.findByEmail(username);
            Basket basket = loggedInUser.getBasket();
            BasketItem toEdit = basket.getBasketItems().stream()
                    .filter((item) -> item.getId().equals(Long.parseLong(id)))
                    .findFirst()
                    .orElse(null);
            if(toEdit == null) return "redirect:/basket";
            model.addAttribute("basketItem", toEdit);
            return "basket_edit";
        }else{
            return "redirect:/basket";
        }
    }

    @PostMapping("/edit")
    public String postEditBasketItem(
            @RequestParam("newAmount") String newAmountStr,
            @RequestParam("productId") String productIdStr,
            @RequestParam("basketItemId") String basketItemIdStr,
            RedirectAttributes redirectAttrs) {

        //See if user is logged in
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //If user is not logged in, then send them to the login page because they need to be logged-in in order to add a product to their basket
        if (!(principal instanceof CustomUserDetails)) {
            return "redirect:/basket";
        }
        //Find currently logged-in user
        String username = ((CustomUserDetails) principal).getUsername();
        User loggedInUser = userRepo.findByEmail(username);

        Integer newAmount = Integer.parseInt(newAmountStr);
        Long productId = Long.parseLong(productIdStr);

        boolean editSuccess = this.basketService.editBasketItemAmount(productId, newAmount, loggedInUser);
        if (!editSuccess) {
            redirectAttrs.addFlashAttribute("edit_fail", "Error! You tried to order more products than there is currently available in stock.");
            return "redirect:/basket/edit/" + basketItemIdStr;
        }

        return "redirect:/basket";
    }


}