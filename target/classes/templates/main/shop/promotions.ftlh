<#import "../macro/template.ftlh" as template>
<#import "../macro/slider.ftlh" as slider>
<#import "../macro/promotion.ftlh" as promotion>
<#import "../../macro/pagination.ftlh" as pagination>
<#import "../../macro/edjllForm.ftlh" as edjllForm>

<#assign links>
    <link rel="stylesheet" href="/static/css/shop/index.css">
</#assign>

<#assign body>
    <form name="shop">
        <div class="page-header-wrap bg-img" data-bg="/static/main/img/slider/slider-2.jpg">
            <div class="container">
                <div class="row">
                    <div class="col-12 text-center">
                        <div class="page-header-content">
                            <div class="page-header-content-inner">
                                <h1>Акции</h1>

                                <ul class="breadcrumb">
                                    <li><a href="/">Главная</a></li>
                                    <li class="current"><a href="/promotions">Акции</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="page-content-wrapper sp-y">
            <div class="container container-wide">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="action-bar-inner mb-30">
                            <div class="row align-items-center">
                                <div class="col-sm-6">
                                    <div class="shop-layout-switcher mb-15 mb-sm-0">
                                        <ul class="layout-switcher nav">
                                            <li data-layout="grid" class="active"><i class="fa fa-th"></i></li>
                                            <li data-layout="list"><i class="fa fa-th-list"></i></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="product-wrapper product-layout layout-grid">
                            <div class="row mtn-30">
                                <#if promotionsPage??>
                                    <#list promotionsPage.getContent() as p>
                                        <@promotion.card
                                        prom = p
                                        ></@promotion.card>
                                    </#list>
                                </#if>
                            </div>
                        </div>

                        <div class="action-bar-inner mt-30">
                            <div class="row align-items-center">
                                <div class="col-sm-12">
                                    <@pagination.template
                                        page = promotionsPage
                                        url = "/promotions"
                                    >
                                    </@pagination.template>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</#assign>

<#assign scripts>
    <script src="/static/js/shop/main.js"></script>
    <@edjllForm.scripts></@edjllForm.scripts>
    <script src="/static/js/shop/product/addToCart.js"></script>
</#assign>

<@template.page
links = links
body = body
scripts = scripts
>
</@template.page>