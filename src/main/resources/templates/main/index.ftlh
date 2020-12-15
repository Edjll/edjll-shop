<#import "macro/template.ftlh" as template>
<#import "macro/slider.ftlh" as slider>
<#import "macro/product.ftlh" as product>
<#import "../macro/security.ftlh" as security>

<#assign body>
    <@slider.template></@slider.template>

    <div class="call-to-action-area sm-top">
        <div class="container">
            <div class="row">
                <div class="col-md-4 col-lg-4">
                    <div class="call-to-action-item mt-0">
                        <div class="call-to-action-item__icon">
                            <img src="/static/main/img/icons/delivery.png" alt="fast delivery">
                        </div>
                        <div class="call-to-action-item__info">
                            <h3>Бесплатная доставка</h3>
                            <p>Бесплатно доставим ваши покупки прямо до двери</p>
                        </div>
                    </div>
                </div>

                <div class="col-md-4 col-lg-4">
                    <div class="call-to-action-item">
                        <div class="call-to-action-item__icon">
                            <img src="/static/main/img/icons/quality.png" alt="quality">
                        </div>
                        <div class="call-to-action-item__info">
                            <h3>Качественные продукты</h3>
                            <p>Мы всегда проверяем все товары на качество</p>
                        </div>
                    </div>
                </div>

                <div class="col-md-4 col-lg-4">
                    <div class="call-to-action-item">
                        <div class="call-to-action-item__icon">
                            <img src="/static/main/img/icons/support.png" alt="support">
                        </div>
                        <div class="call-to-action-item__info">
                            <h3>Поддержка</h3>
                            <p>Мы стараемся в короткие сроки ответить на заданные вами вопросы</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="best-seller-products-area sm-top">
        <div class="container container-wide">
            <div class="row">
                <div class="col-lg-5 m-auto text-center">
                    <div class="section-title">
                        <h2 class="h3">Популярные товары</h2>
                        <p>Эти товары покупали чаще всего за последний месяц</p>
                    </div>
                </div>
            </div>

            <#if products??>
                <div class="row">
                    <div class="col-12">
                        <div class="product-wrapper">
                            <div class="product-carousel">
                                <!-- Start Product Item -->
                                <#list products as p>
                                    <#if baskets??>
                                        <@product.card
                                        prod = p
                                        baskets = baskets
                                        ></@product.card>
                                    <#else>
                                        <@product.card
                                        prod = p
                                        ></@product.card>
                                    </#if>
                                </#list>
                                <!-- End Product Item -->
                            </div>
                        </div>
                    </div>
                </div>
            </#if>
        </div>
    </div>

    <div class="call-to-action-area">
        <div class="call-to-action-content-area bg-img">
            <div class="container">
                <div class="row">
                    <div class="col-12 text-center">
                        <div class="call-to-action-txt">
                            <h2>У нас вы найдёте все <br> нужные вам запчасти</h2>
                            <a href="shop.html" class="btn btn-brand">За покупками</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="call-to-action-image-area">
            <div class="container">
                <div class="row">
                    <div class="col-12 text-center">
                        <img src="static/main/img/slider/slider-1.jpg" alt="Car" />
                    </div>
                </div>
            </div>
        </div>
    </div>

    <#if promotion??>
        <div class="flash-deals-area bg-img">
            <div class="container">
                <div class="row">
                    <div class="col-xl-11 m-auto">
                        <div class="row align-items-center">
                            <div class="col-md-5 col-lg-6">
                                <div class="flash-deals-thumb text-center text-md-left">
                                    <img src="/image/promotionBanner/${promotion.banner.filename}" alt="banner" />
                                </div>
                            </div>

                            <div class="col-md-7 col-lg-6 text-center">
                                <div class="flash-deals-content">
                                    <h2>${promotion.name}</h2>
                                    <h3>${promotion.description}</h3>
                                    <a href="/promotions/${promotion.id}/products" class="btn btn-brand">За покупками</a>

                                    <div class="deals-countdown-area">
                                        <div class="ht-countdown" data-date="${promotion.dateEnd?string('MM/dd/yyyy HH:mm:ss')}"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </#if>

    <#if !security.isAuthorized>
        <div class="newsletter-area-wrapper <#if !promotion??>mt-5</#if>">
            <div class="container container-wide">
                <div class="newsletter-area-inner bg-img" data-bg="/static/main/img/bg/newsletter-bg.jpg">
                    <div class="row">
                        <div class="col-lg-8 col-xl-5 m-auto">
                            <div class="newsletter-content text-center">
                                <h4>Узнавайте о наших <span>акциях</span> первым</h4>
                                <p>Подпишитесь на нашу почтовую рассылку, чтобы узнавать о новых предлжениях</p>

                                <div class="newsletter-form-wrap">
                                    <form action="/add/email" method="post">
                                        <div class="form-content">
                                            <input type="email" name="email" placeholder="Введите свою почту" />
                                            <input type="hidden" name="_csrf" value="${_csrf.token}">
                                            <button class="btn-newsletter">Подписаться</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </#if>

    <div class="brand-logo-area sm-top sm-bottom">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="brand-logo-content">
                        <#list manufacturers as manufacturer>
                            <div class="brand-logo-item">
                                <a href="#"><img src="/image/manufacturerLogo/${manufacturer.logo.filename}" alt="logo" /></a>
                            </div>
                        </#list>
                    </div>
                </div>
            </div>
        </div>
    </div>
</#assign>

<#assign scripts>
    <script src="/static/js/shop/product/addToCart.js"></script>
</#assign>

<@template.page
    body = body
    scripts = scripts
>
</@template.page>