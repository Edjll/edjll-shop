<#import "../macro/template.ftlh" as template>
<#import "../macro/slider.ftlh" as slider>
<#import "../macro/product.ftlh" as product>
<#import "../../macro/edjllForm.ftlh" as edjllForm>
<#import "../../macro/security.ftlh" as security>

<#assign links>
    <link rel="stylesheet" href="/static/css/shop/cart.css">
</#assign>

<#assign body>
    <!--== Start Page Header Area ==-->
    <div class="page-header-wrap bg-img" data-bg="/static/main/img/slider/slider-2.jpg">
        <div class="container">
            <div class="row">
                <div class="col-12 text-center">
                    <div class="page-header-content">
                        <div class="page-header-content-inner">
                            <h1>Корзина</h1>

                            <ul class="breadcrumb">
                                <li><a href="/">Главная</a></li>
                                <li class="current"><a href="/shop/cart">Корзина</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--== End Page Header Area ==-->

    <div class="col-lg-6 col-xl-5 mx-auto">
        <!-- Checkout Page Order Details -->
        <div class="order-details-area-wrap">
            <h2>Ваш заказ</h2>

            <div class="order-details-table table-responsive">
                <table class="table table-borderless">
                    <thead>
                    <tr>
                        <th>Продукты</th>
                        <th>Цена</th>
                        <th>Общая</th>
                    </tr>
                    </thead>
                    <tbody>
                        <#assign total = 0>
                        <#assign discountTotal = 0>
                        <#if cartProducts??>
                            <#list cartProducts as cp>
                                <tr class="cart-item">
                                    <td><span class="product-title">${cp.name}</span> <span
                                                class="product-quantity">&#215;  ${cp.count}</span></td>
                                    <td><#if cp.price != cp.discountPrice><span class="line-through pr-1">${cp.price}₽</span></#if>${cp.discountPrice}₽</td>
                                    <td><#if cp.price != cp.discountPrice><span class="line-through pr-1">${cp.price * cp.count}₽</span></#if>${cp.discountPrice * cp.count}₽</td>
                                </tr>
                                <#assign total = total + cp.price * cp.count>
                                <#assign discountTotal = discountTotal + cp.discountPrice * cp.count>
                            </#list>
                        </#if>
                    </tbody>
                    <tfoot>
                    <tr class="final-total">
                        <th>Итого</th>
                        <td><span class="total-amount"></span></td>
                        <td><span class="total-amount"><#if total != discountTotal><span class="line-through pr-1">${total}₽</span></#if>${discountTotal}₽</span></td>
                    </tr>
                    </tfoot>
                </table>
            </div>

            <div class="order-details-footer">
                <form name="payment">
                    <div class="edjll-select">
                        <div class="edjll-select__settings">
                            <span class="edjll-select__settings__attributes">{"name": "address", "label": "Адрес", "inputValueFormat": { "type":"var", "pattern": ["name"] }, "outputValueFormat": { "type": "var", "pattern": ["name"] }}</span>
                            <span class="edjll-select__settings__request">{"ajax":true, "url":"http://localhost:8080/delivery/user/addresses"}</span>
                            <span class="edjll-select__settings__validation">{"required":true}</span>
                        </div>
                    </div>
                    <button class="btn btn-bordered mt-80">Оплатить</button>
                </form>
            </div>
        </div>
    </div>
</#assign>

<#assign scripts>
    <script src="/static/js/shop/cart/payment.js"></script>
    <@edjllForm.scripts></@edjllForm.scripts>
</#assign>

<@template.page
links = links
body = body
scripts = scripts
>
</@template.page>