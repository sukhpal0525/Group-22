package org.aston.ecommerce.basket;

import java.util.Optional;
import javax.transaction.Transactional;
import org.aston.ecommerce.product.Product;
import org.aston.ecommerce.product.ProductRepository;
import org.aston.ecommerce.user.User;
import org.springframework.stereotype.Service;

@Service
public class BasketService {

    private final ProductRepository productRepository;
    private final BasketRepository basketRepository;

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

}
