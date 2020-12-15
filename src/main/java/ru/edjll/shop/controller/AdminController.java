package ru.edjll.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.edjll.shop.domain.*;
import ru.edjll.shop.model.cart.Sale;
import ru.edjll.shop.service.*;

import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CountryService countryService;

    @Autowired
    private CityService cityService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private RefundService refundService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private SaleService saleService;

    @Autowired
    private ProductDataService productDataService;

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private UserService userService;

    @Autowired
    private SupplyService supplyService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ReviewService reviewService;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPPORT')")
    @GetMapping
    public String getAdminPanel() {
        return "admin/index";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/country/all")
    public String getCountriesPage(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            Map<String, Object> model
    ) {
        Pageable pageable = PageRequest.of(page, 15);
        Page<Country> countriesPage = countryService.getPageCountries(pageable);
        model.put("countriesPage", countriesPage);
        return "admin/country/all";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/city/all")
    public String getCitiesPage(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            Map<String, Object> model
    ) {
        Pageable pageable = PageRequest.of(page, 15);
        Page<City> citiesPage = cityService.getPageCities(pageable);
        model.put("citiesPage", citiesPage);
        return "admin/city/all";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/manufacturer/all")
    public String getManufacturersPage(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            Map<String, Object> model
    ) {
        Pageable pageable = PageRequest.of(page, 15);
        Page<Manufacturer> manufacturersPage = manufacturerService.getPageManufacturers(pageable);
        model.put("manufacturersPage", manufacturersPage);
        return "admin/manufacturer/all";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/category/all")
    public String getCategoriesPage(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            Map<String, Object> model
    ) {
        Pageable pageable = PageRequest.of(page, 15);
        Page<ProductCategory> categoriesPage = productCategoryService.getPageCategories(pageable);
        model.put("categoriesPage", categoriesPage);
        return "admin/category/all";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/storage/all")
    public String getStoragesPage(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            Map<String, Object> model
    ) {
        Pageable pageable = PageRequest.of(page, 15);
        Page<Storage> storagesPage = storageService.getPageStorages(pageable);
        model.put("storagesPage", storagesPage);
        return "admin/storage/all";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/sale/all")
    public String getSalesPage(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            Map<String, Object> model
    ) {
        Pageable pageable = PageRequest.of(page, 15);
        Page<Sale> sale = saleService.getSaleModel(pageable);
        model.put("salePage", sale);
        return "admin/sale/all";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/product/all")
    public String getProductsPage(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            Map<String, Object> model
    ) {
        Pageable pageable = PageRequest.of(page, 15);
        Page<ProductData> productsPage = productDataService.getPageProducts(pageable);
        model.put("productsPage", productsPage);
        return "admin/product/all";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/promotion/all")
    public String getPromotionsPage(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            Map<String, Object> model
    ) {
        Pageable pageable = PageRequest.of(page, 15);
        Page<Promotion> promotionsPage = promotionService.getPagePromotion(pageable);
        model.put("promotionsPage", promotionsPage);
        return "admin/promotion/all";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPPORT')")
    @GetMapping("/user/all")
    public String getUsersPage(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            Map<String, Object> model
    ) {
        Pageable pageable = PageRequest.of(page, 15);
        Page<User> usersPage = userService.getAllOnlyUsersPage(pageable);
        model.put("usersPage", usersPage);
        return "admin/user/all";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/employee/all")
    public String getEmployeesPage(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            Map<String, Object> model
    ) {
        Pageable pageable = PageRequest.of(page, 15);
        Page<Employee> employeesPage = employeeService.getPageEmployees(pageable);
        model.put("employeesPage", employeesPage);
        return "admin/employee/all";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/statistics/all")
    public String getStatistics() {
        return "admin/statistics/all";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/supply/all")
    public String getSupplyPage(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            Map<String, Object> model
    ) {
        Pageable pageable = PageRequest.of(page, 15);
        Page<Supply> supplyPage = supplyService.getPageSupply(pageable);
        model.put("supplyPage", supplyPage);
        return "admin/supply/all";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPPORT')")
    @GetMapping("/question/all")
    public String getQuestionsPage(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            Map<String, Object> model
    ) {
        Pageable pageable = PageRequest.of(page, 15);
        Page<Question> questionsPage = questionService.getPageQuestion(pageable);
        model.put("questionsPage", questionsPage);
        return "admin/support/question/all";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPPORT')")
    @GetMapping("/review/all")
    public String getReviewsPage(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            Map<String, Object> model
    ) {
        Pageable pageable = PageRequest.of(page, 15);
        Page<Review> reviewsPage = reviewService.getPageReview(pageable);
        model.put("reviewsPage", reviewsPage);
        model.put("statuses", StatusReviewAndRefund.values());
        return "admin/support/review/all";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPPORT')")
    @GetMapping("/refund/all")
    public String getRefundsPage(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            Map<String, Object> model
    ) {
        Pageable pageable = PageRequest.of(page, 15);
        Page<Refund> refundsPage = refundService.getRefundPage(pageable);
        model.put("refundPage", refundsPage);
        return "admin/refund/all";
    }
}
