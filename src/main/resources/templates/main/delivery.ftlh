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
                                <li class="current"><a href="/delivery">Доставка</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="contact-info-wrapper">
        <div class="container">
            <div class="row align-items-center my-5">
                <div class="col-lg-6 order-1 order-md-0 my-4">
                    <div class="about-content">
                        <h2 class="h3 mb-sm-20">CDEK</h2>
                        <p>Является одной из ведущих транспортных компаний.</p>
                        <p>Обрабатывает как большие заказы, так и маленькие посылки.</p>
                        <p>Приемлимые цены.</p>
                        <p>Короткие сроки.</p>
                    </div>
                </div>

                <div class="col-lg-6 order-0">
                    <div class="about-thumb mb-sm-30">
                        <img src="/static/main/img/delivery/cdek.png" alt="About" />
                    </div>
                </div>
            </div>

            <div class="row align-items-center my-5">
                <div class="col-lg-6 order-0">
                    <div class="about-thumb mb-sm-30">
                        <img src="/static/main/img/delivery/ems.png" alt="About" />
                    </div>
                </div>

                <div class="col-lg-6 order-1 order-md-0 my-4">
                    <div class="about-content">
                        <h2 class="h3 mb-sm-20">EMS</h2>
                        <p>Является одной из ведущих транспортных компаний.</p>
                        <p>Обрабатывает как большие заказы, так и маленькие посылки.</p>
                        <p>Приемлимые цены.</p>
                        <p>Короткие сроки.</p>
                    </div>
                </div>
            </div>

            <div class="row align-items-center my-5">
                <div class="col-lg-6 order-1 order-md-0 my-4">
                    <div class="about-content">
                        <h2 class="h3 mb-sm-20">Почта России</h2>
                        <p>Является одной из ведущих транспортных компаний.</p>
                        <p>Обрабатывает как большие заказы, так и маленькие посылки.</p>
                        <p>Приемлимые цены.</p>
                        <p>Короткие сроки.</p>
                    </div>
                </div>

                <div class="col-lg-6 order-0">
                    <div class="about-thumb mb-sm-30">
                        <img src="/static/main/img/delivery/rf.png" alt="About" />
                    </div>
                </div>
            </div>
        </div>
    </div>

</#assign>

<@template.page
body = body
>
</@template.page>