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

    <!--== Start Page Content Wrapper ==-->
    <div class="page-content-wrapper sp-y">
        <div class="cart-page-content-wrap">
            <div class="container container-wide">
                <div class="row">
                    <div class="col-lg-8">
                        <div class="shopping-cart-list-area">
                            <form class="shopping-cart-table table-responsive" name="cart">
                                <table class="table table-bordered text-center mb-0">
                                    <thead>
                                    <tr>
                                        <th>Товар</th>
                                        <th>Стоимость</th>
                                        <th>Количество</th>
                                        <th>Итого</th>
                                    </tr>
                                    </thead>

                                    <tbody class="products">
                                        <tr>
                                            <td colspan="4">Пусто</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </form>

                        </div>
                    </div>

                    <div class="col-lg-4">
                        <!-- Cart Calculate Area -->
                        <div class="cart-calculate-area mt-sm-40 mt-md-60">
                            <h5 class="cal-title">Итого</h5>

                            <div class="cart-cal-table table-responsive">
                                <table class="table table-borderless">
                                    <tr class="cart-sub-total">
                                        <th>Сумма</th>
                                        <td class="cart-price">0₽</td>
                                    </tr>
                                </table>
                            </div>

                            <div class="proceed-checkout-btn">
                                <#if security.isAuthorized>
                                    <a href="/shop/cart/payment" class="btn btn-brand d-block">Оплатить</a>
                                <#else>
                                    <a href="/shop/cart/payment" class="btn btn-brand d-block">Войти и оплатить</a>
                                </#if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--== End Page Content Wrapper ==-->
</#assign>

<#assign scripts>
    <script src="/static/js/shop/cart/main.js"></script>
    <@edjllForm.scripts></@edjllForm.scripts>
    <script src="/static/js/shop/cart/update.js"></script>
    <script src="/static/js/shop/cart/delete.js"></script>
    <script src="/static/js/shop/cart/load.js"></script>
</#assign>

<@template.page
    links = links
body = body
scripts = scripts
>
</@template.page>