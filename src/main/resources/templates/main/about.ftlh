<#import "macro/template.ftlh" as template>
<#import "macro/slider.ftlh" as slider>

<#assign body>
    <div class="page-header-wrap bg-img" data-bg="/static/main/img/slider/slider-2.jpg">
        <div class="container">
            <div class="row">
                <div class="col-12 text-center">
                    <div class="page-header-content">
                        <div class="page-header-content-inner">
                            <h1>О нас</h1>

                            <ul class="breadcrumb">
                                <li><a href="/">Главная</a></li>
                                <li class="current"><a href="/about">О нас</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="page-content-wrapper sm-top">
        <div class="about-page-content">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-lg-6 order-1 order-md-0">
                        <div class="about-content">
                            <h2 class="h3 mb-sm-20">edjll</h2>
                            <p>Это интернет-магазин автомобильных запчастей.</p>
                            <p>В нём можно найти все необходимые вам элементы для улучшения вашего авто.</p>
                            <p>Сотрудничает только с качественными и популярными производителями автозапчастей.</p>
                        </div>
                    </div>

                    <div class="col-lg-6 order-0">
                        <div class="about-thumb mb-sm-30">
                            <img src="/static/main/img/slider/slider-1.jpg" alt="About" />
                        </div>
                    </div>
                </div>

                <div class="row align-items-center sm-top">
                    <div class="col-lg-6">
                        <div class="about-thumb video-play mb-sm-30">
                            <img src="/static/main/img/slider/slider-2.jpg" alt="About" />
                        </div>
                    </div>

                    <div class="col-lg-6">
                        <div class="about-content">
                            <h2 class="h3">Чего мы хотим?</h2>
                            <p>Мы хотим, чтобы наши покупатели были счастливы.</p>
                            <p>мы хотим, чтобы проданные нами товары долго и качественно служили их владельцам.</p>
                            <p>Мы хотим, чтобы нас вспоминали только тёплыми словами.</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="contact-map-wrapper sm-top">
        <div id="map_content" class="h-100" data-lat="51.53060606673002" data-lng="45.97887498345618" data-zoom="20"></div>
    </div>
</#assign>

<@template.page
body = body
>
</@template.page>