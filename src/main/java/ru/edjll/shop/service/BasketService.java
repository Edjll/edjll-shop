package ru.edjll.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.edjll.shop.domain.*;
import ru.edjll.shop.model.cart.CartProduct;
import ru.edjll.shop.model.cart.ProductDataCart;
import ru.edjll.shop.repository.BasketRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BasketService {

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private ProductDataService productDataService;

    @Autowired
    private PromotionProductService promotionProductService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    public String save(User authUser, CartProduct product) {
        String basketAction = BasketAction.UPDATE.getText();

        User user = userService.getUserById(authUser.getId());
        List<Basket> baskets = basketRepository.getAllByUserId(user.getId());

        Basket basket = baskets.stream()
                .filter(p -> p.getProductData().getId().equals(product.getId()))
                .findFirst().orElse(new Basket(user, productDataService.getProductDataById(product.getId())));

        if (basket.getCount() != null && basket.getCount().equals(product.getCount())) {
            return null;
        }

        basket.setCount(product.getCount());

        if (basket.getId() == null) {
            basketAction = BasketAction.ADD.getText();
        }

        basketRepository.save(basket);

        return basketAction;
    }

    public List<CartProduct> getAllCartProduct(User user) {
        List<Basket> baskets = basketRepository.getAllByUserId(user.getId());

        return baskets.stream().map(basket -> {
            CartProduct cartProduct = new CartProduct();
            cartProduct.setId(basket.getProductData().getId());
            cartProduct.setCount(basket.getCount());
            return cartProduct;
        }).collect(Collectors.toList());
    }

    public List<ProductDataCart> getProductsInBasket(User user) {
        List<Basket> baskets = basketRepository.getAllByUserId(user.getId());

        List<CartProduct> cartProducts = baskets.stream()
                .filter(b -> {
                    Long maxCount = productService.getProductMaxCount(b.getProductData().getId());
                    return maxCount != null && maxCount > 0;
                })
                .map(basket -> new CartProduct(basket.getProductData().getId(), basket.getCount()))
                .collect(Collectors.toList());

        return getProductsInBasket(cartProducts);
    }

    public List<ProductDataCart> getAllProductsInBasket(User user) {
        List<Basket> baskets = basketRepository.getAllByUserId(user.getId());

        List<CartProduct> cartProducts = baskets.stream()
                .map(basket -> new CartProduct(basket.getProductData().getId(), basket.getCount()))
                .collect(Collectors.toList());

        return getProductsInBasket(cartProducts);
    }

    public List<ProductDataCart> getProductsInBasket(List<CartProduct> products) {

        return products.stream().map(product -> {
            ProductDataCart productDataCart = new ProductDataCart();

            ProductData productData = productDataService.getProductDataById(product.getId());

            productDataCart.setId(productData.getId());
            productDataCart.setName(productData.getName());
            Image image = imageService.getFirstImageByProductDataId(productData.getId());
            if (image != null) {
                productDataCart.setImage(image.getFilename());
            }
            productDataCart.setCount(product.getCount());
            productDataCart.setPrice(productData.getCost());
            productDataCart.setMaxCount(productService.getProductMaxCount(productData.getId()));

            PromotionProduct promotionProduct = promotionProductService.getPromotionProductByProductData(productData);
            Double discountPrice = productData.getCost();
            if (promotionProduct != null) {
                if (new Date().getTime() < promotionProduct.getPromotion().getDateEnd().getTime()) {
                    discountPrice = productData.getCost() * (100 - promotionProduct.getDiscount()) / 100;
                    productDataCart.setDiscount(promotionProduct.getDiscount());
                }
            }
            productDataCart.setDiscountPrice(discountPrice);

            return productDataCart;
        }).collect(Collectors.toList());
    }

    public Basket getBasketByProductData(Long id) {
        return basketRepository.getByProductDataId(id);
    }

    public String delete(User authUser, CartProduct product) {
        User user = userService.getUserById(authUser.getId());
        List<Basket> baskets = basketRepository.getAllByUserId(user.getId());

        Basket basket = baskets.stream()
                .filter(p -> p.getProductData().getId().equals(product.getId()))
                .findFirst().orElse(null);

        if (basket != null) {
            basketRepository.delete(basket);

            return BasketAction.REMOVE.getText();
        }
        return null;
    }

    public void deleteById(Long id) {
        basketRepository.deleteById(id);
    }

    public List<Basket> getBasketsByUser(Long id) {
        return basketRepository.getAllByUserId(id);
    }

    public Integer getCountProduct(User authUser) {
        User user = userService.getUserById(authUser.getId());
        return user.getBaskets().size();
    }
}
