package org.aston.ecommerce.home;

import org.aston.ecommerce.basket.Basket;
import org.aston.ecommerce.basket.BasketRepository;
import org.aston.ecommerce.user.CustomUserDetails;
import org.aston.ecommerce.user.User;
import org.aston.ecommerce.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;


@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private BasketRepository basketRepository;

    @GetMapping("/")
    public String index() { return "home"; }

    @GetMapping("/home")
    public String viewHome(HttpSession session) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //If user is logged in, then see if the session corresponds to a basket so that this basket can be connected to the user
        if (principal instanceof CustomUserDetails) {
            String basketId = (String) session.getAttribute("BASKET_ID");
            if(!this.checkSafeBasketIdAttribute(basketId)){
                //Basket Id no longer needed
                session.setAttribute("BASKET_ID", null);
                return "home";
            }
            Optional<Basket> optBasket = this.basketRepository.findById(Long.parseLong(basketId));
            Basket basket = optBasket.get();
            String username = ((CustomUserDetails) principal).getUsername();
            User loggedInUser = userRepo.findByEmail(username);
            loggedInUser.setBasket(basket);
            userRepo.save(loggedInUser);
            basket.setUser(loggedInUser);
            this.basketRepository.save(basket);
            //Basket Id no longer needed
            session.setAttribute("BASKET_ID", null);
        }

        return "home";
    }

    private Boolean checkSafeBasketIdAttribute(String basketId){
        Boolean returnBool = true;

        if(basketId == null){
            return false;
        }
        Optional<Basket> optBasket = this.basketRepository.findById(Long.parseLong(basketId));
        if(!optBasket.isPresent()){
            returnBool = false;
        }

        return returnBool;
    }
}
