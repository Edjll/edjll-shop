<#import "../../macro/security.ftlh" as security>
<#import "../../macro/logout.ftlh" as logout>

<#macro template>
    <header class="header-area">
        <div class="container container-wide">
            <div class="row align-items-center">
                <div class="col-sm-4 col-lg-2">
                    <div class="site-logo text-center text-sm-left">
                        <a href="/">edjll</a>
                    </div>
                </div>

                <div class="col-lg-7 d-none d-lg-block">
                    <div class="site-navigation">
                        <ul class="main-menu nav">
                            <li><a href="/">Главная</a></li>
                            <li><a href="/about">О нас</a></li>
                            <li><a href="/shop/catalog">Каталог</a></li>
                            <li><a href="/shop">Товары</a></li>
                            <li><a href="/promotions">Акции</a></li>
                            <li><a href="/delivery">Доставка</a></li>
                        </ul>
                    </div>
                </div>

                <div class="col-sm-8 col-lg-3">
                    <div class="site-action d-flex justify-content-center justify-content-sm-end align-items-center">
                        <#if security.user??>
                            <div class="dropdown-toggle user-menu" id="user" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <i class="ion-ios-person-outline user-menu__icon"></i>
                            </div>
                            <div class="dropdown-menu dropdown-menu-right" aria-labelledby="user">
                                <a class="dropdown-item" href="/user/profile">Мой профиль</a>
                                <#if security.isAdmin || security.isSupport><a class="dropdown-item" href="/admin">Админка</a></#if>
                                <@logout.template></@logout.template>
                            </div>
                        <#else>
                            <ul class="login-reg-nav nav">
                                <li><a href="/user/login">Вход</a></li>
                                <li><a href="/user/registration">Регистрация</a></li>
                            </ul>
                        </#if>

                        <div class="mini-cart-wrap">
                            <a href="/shop/cart" class="btn-mini-cart">
                                <i class="ion-bag"></i>
                                <span class="cart-total">0</span>
                            </a>
                        </div>

                        <div class="responsive-menu d-lg-none">
                            <button class="btn-menu">
                                <i class="fa fa-bars"></i>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </header>
</#macro>