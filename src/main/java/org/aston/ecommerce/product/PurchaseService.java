package org.aston.ecommerce.product;

import org.aston.ecommerce.basket.Basket;
import org.aston.ecommerce.basket.BasketRepository;
import org.aston.ecommerce.user.CustomUserDetails;
import org.aston.ecommerce.user.User;
import org.aston.ecommerce.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Service
public class PurchaseService {

    @Autowired private ProductRepository productRepository;
    @Autowired private BasketRepository basketRepository;
    @Autowired private UserRepository userRepository;

    public void processPurchase(Purchase purchase, Object principal, RedirectAttributes redirectAttrs) {
        if (!(principal instanceof CustomUserDetails)) {
            throw new AccessDeniedException("You must be logged in to purchase a product.");
        }
        Optional<Product> optProduct = productRepository.findById(Long.parseLong(purchase.getProduct_id()));
        Product product = optProduct.get();

        if (Integer.parseInt(purchase.getNum_ordered()) > product.getAmountAvailable()) {
            redirectAttrs.addFlashAttribute("purchase_fail", "Error! You tried to order more products than there is currently available in stock.");
        } else {
            product.setAmountAvailable(product.getAmountAvailable() - Integer.parseInt(purchase.getNum_ordered()));
            productRepository.save(product);

            String username = ((CustomUserDetails) principal).getUsername();
            User loggedInUser = userRepository.findByEmail(username);

            Basket addToBasket = new Basket();
            addToBasket.setAmount(Integer.parseInt(purchase.getNum_ordered()));
            addToBasket.setProduct(product);
            addToBasket.setUser(loggedInUser);

            basketRepository.save(addToBasket);
            redirectAttrs.addFlashAttribute("purchase_success", "yes");
        }
    }
}
