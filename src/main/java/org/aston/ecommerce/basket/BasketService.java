package org.aston.ecommerce.basket;

import java.util.Optional;
import javax.transaction.Transactional;
import org.aston.ecommerce.product.Product;
import org.aston.ecommerce.product.ProductRepository;
import org.aston.ecommerce.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasketService {

    @Autowired private final ProductRepository productRepository;
    @Autowired private final BasketRepository basketRepository;

    public BasketService(ProductRepository productRepository, BasketRepository basketRepository) {
        this.productRepository = productRepository;
        this.basketRepository = basketRepository;
    }

    @Transactional
    public boolean addItemToBasket(Long productId, Integer numOrdered, User user) {
        boolean success = false;

        Optional<Product> optProduct = this.productRepository.findById(productId);
        Product product = optProduct.get();

        Basket basket = user.getBasket();

        if (!(numOrdered > product.getAmountAvailable())) {
            product.setAmountAvailable(product.getAmountAvailable() - numOrdered);
            product = this.productRepository.save(product);

            BasketItem basketItem = basket.getBasketItemForProduct(product);
            if (basketItem == null) {
                basketItem = new BasketItem();
                basketItem.setBasket(basket);
                basketItem.setProduct(product);
            } else {
                numOrdered += basketItem.getAmount();
            }
            basketItem.setAmount(numOrdered);
            basket.getBasketItems().add(basketItem);

            this.basketRepository.save(basket);

            success = true;
        }
        return success;
    }

    @Transactional
    public boolean editBasketItemAmount(Long productId, Integer newAmount, User user) {
        boolean success = false;

        Optional<Product> optProduct = this.productRepository.findById(productId);
        Product product = optProduct.get();

        Basket basket = user.getBasket();

        BasketItem basketItem = basket.getBasketItemForProduct(product);
        Integer oldAmount = basketItem.getAmount();
        //No need to do anything if the same amount is being dealt with
        if(oldAmount.equals(newAmount)) return true;

        if(!(newAmount > (product.getAmountAvailable() + oldAmount))){
            basketItem.setAmount(newAmount);
            basket.getBasketItems().set(basket.getBasketItems().indexOf(basketItem), basketItem);
            this.basketRepository.save(basket);

            if(newAmount < oldAmount){
                product.setAmountAvailable(product.getAmountAvailable() + (oldAmount - newAmount));
            }else if(newAmount > oldAmount){
                product.setAmountAvailable(product.getAmountAvailable() - (newAmount - oldAmount));
            }
            this.productRepository.save(product);

            success = true;
        }

        return success;
    }

    public Double getTotalOfBasket(Basket basket) {
        Double returnDouble = 0.0;

        for (BasketItem basketItem : basket.getBasketItems()) {
            double productPrice = basketItem.getProduct().getAmount();

            if (basketItem.getProduct().getOnSale()) {
                productPrice = productPrice * (1 - basketItem.getProduct().getSale());
            }
            returnDouble += (basketItem.getAmount() * productPrice);
        }
        return returnDouble;
    }
}