<#import "../macro/template.ftlh" as template>
<#import "../macro/slider.ftlh" as slider>

<#assign links>
    <link rel="stylesheet" href="/static/css/shop/catalog/index.css">
</#assign>

<#assign body>
    <div class="page-header-wrap bg-img" data-bg="/static/main/img/slider/slider-2.jpg">
        <div class="container">
            <div class="row">
                <div class="col-12 text-center">
                    <div class="page-header-content">
                        <div class="page-header-content-inner">
                            <h1>Каталог</h1>

                            <ul class="breadcrumb">
                                <li><a href="/">Главная</a></li>
                                <li class="current"><a href="/about">Каталог</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="page-content-wrapper sm-top">
        <div class="container catalog">
            <div class="catalog__header"><#if category??>${category.name}<#else>Каталог</#if></div>
            <#if category??><div class="catalog__description">${category.description}</div></#if>
            <div class="catalog__body">
                <#list categories as c>
                    <a href="/shop/catalog/${c.id}" class="catalog__body__item">
                        ${c.name}
                        <p>${c.description}</p>
                    </a>
                </#list>
            </div>
        </div>
    </div>
</#assign>

<@template.page
    links = links
body = body
>
</@template.page>