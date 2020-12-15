package ru.edjll.shop.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.edjll.shop.domain.*;
import ru.edjll.shop.model.Attribute;
import ru.edjll.shop.model.Search;
import ru.edjll.shop.model.cart.ProductDataCart;
import ru.edjll.shop.service.*;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ShopController {

    @Autowired
    private ProductDataService productDataService;

    @Autowired
    private BasketService basketService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private AttributeService attributeService;

    @GetMapping("/shop")
    public String getShopPage(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false, name = "search") String search,
            Map<String, Object> model
    ) {
        List<Attribute> attributes = null;
        ProductCategory productCategory = null;

        Search searchModel = new Search();
        searchModel.setSort(Order.ASCENDING.ordinal());
        searchModel.setMin(0);
        searchModel.setPage(0);

        try {
            Reader reader = new StringReader(search);
            Gson gson = new Gson();
            searchModel = gson.fromJson(reader, Search.class);
        } catch (Exception ex) { }

        List<ProductDataCart> productDataCarts = productDataService.search(searchModel);
        List<ProductDataCart> productDataCartsSorted;

        if (searchModel.getSort() == Order.ASCENDING.ordinal()) {
            productDataCartsSorted = productDataCarts.stream().sorted(Comparator.comparing(ProductDataCart::getDiscountPrice)).collect(Collectors.toList());
        } else if (searchModel.getSort() == Order.DESCENDING.ordinal()) {
            productDataCartsSorted = productDataCarts.stream().sorted((a, b) -> b.getDiscountPrice().compareTo(a.getDiscountPrice())).collect(Collectors.toList());
        } else {
            productDataCartsSorted = productDataCarts;
        }

        int size = 4;
        int page = searchModel.getPage();
        Pageable pageable = PageRequest.of(page, size);

        Page<ProductDataCart> products = productDataService.search(productDataCartsSorted, pageable);
        if (searchModel.getCategory() != null) {
            attributes = attributeService.getAttributesByProductCategoryWithValue(searchModel.getCategory())
                    .stream()
                    .sorted(Comparator.comparing(Attribute::getName))
                    .collect(Collectors.toList());
            productCategory = productCategoryService.getCategoryById(searchModel.getCategory());
        }
        Double maxPrice = productDataService.getMaxPrice(productDataCarts);

        if (searchModel.getMax() == null) {
            searchModel.setMax(maxPrice.intValue());
        }
        if (searchModel.getAttributes() == null) {
            searchModel.setAttributes(new ArrayList<>());
        }

        List<Basket> baskets = null;

        if (user != null) {
            baskets = basketService.getBasketsByUser(user.getId());
        }

        model.put("baskets", baskets);
        model.put("orders", Order.values());
        model.put("productsPage", products);
        model.put("attributes", attributes);
        model.put("category", productCategory);
        model.put("maxPrice", maxPrice);
        model.put("search", searchModel);

        return "main/shop/shop";
    }

    @GetMapping("/shop/cart")
    public String getShopCartPage() {
        return "main/shop/cart";
    }

    @GetMapping("/shop/catalog")
    public String getCatalogPage(Map<String, Object> model) {
        List<ProductCategory> productCategories = productCategoryService.getCategoriesWithoutParent();
        model.put("categories", productCategories);
        return "main/catalog/index";
    }

    @GetMapping("/shop/catalog/{id}")
    public ModelAndView getCatalogPage(
            @PathVariable Long id,
            Map<String, Object> model
    ) {
        ProductCategory productCategory = productCategoryService.getCategoryById(id);
        List<ProductCategory> productCategories = productCategoryService.getChildCategories(id);

        if (productCategories.isEmpty()) {
            Search search = new Search();
            search.setCategory(productCategory.getId());
            search.setPage(0);
            search.setMin(0);
            search.setSort(Order.ASCENDING.ordinal());
            Gson gson = new Gson();
            model.put("search", gson.toJson(search));
            return new ModelAndView("redirect:/shop", model);
        }

        model.put("category", productCategory);
        model.put("categories", productCategories);
        return new ModelAndView("main/catalog/index", model);
    }

    @GetMapping("/promotions")
    public String getPromotionsPage(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            Map<String, Object> model
    ) {
        Pageable pageable = PageRequest.of(page, 15, Sort.by("dateStart").descending());
        Page<Promotion> promotionsPage = promotionService.getPagePromotion(pageable);
        model.put("promotionsPage", promotionsPage);
        return "main/shop/promotions";
    }
}
