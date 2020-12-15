<#import "../macro/template.ftlh" as template>
<#import "../macro/slider.ftlh" as slider>
<#import "../macro/product.ftlh" as product>
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
                                <h1>Товары</h1>

                                <ul class="breadcrumb">
                                    <li><a href="/">Главная</a></li>
                                    <li class="current"><a href="/shop">Товары</a></li>
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
                    <div class="col-lg-9">
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

                                <div class="col-sm-6">
                                    <div class="edjll-select">
                                        <div class="edjll-select__settings">
                                            <span class="edjll-select__settings__attributes">{"name": "sort", "itemVisibleValue":["name"], "inputValueFormat":{"type":"json", "pattern":["id", "name"]}, "outputValueFormat":{"type":"var", "pattern":["id"]}, "defaultValue": {"id":<#if search??>${search.sort}<#else>${orders?first.ordinal()}</#if>, "name":<#if search??><#list orders as order><#if order.ordinal() == search.sort>"${order.text}"</#if></#list><#else>"${orders?first.text}"</#if>}, "options":[<#list orders as order>{"id":${order.ordinal()}, "name":"${order.text}"}<#if order?has_next>,</#if></#list>]}</span>
                                            <span class="edjll-select__settings__request">{"pagination":false, "searchParam":false}</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="product-wrapper product-layout layout-grid">
                            <div class="row mtn-30">
                                <#if productsPage??>
                                    <#assign startIndex = productsPage.number * productsPage.size>
                                    <#assign lastIndex = startIndex + productsPage.size>
                                    <#list productsPage.getContent() as p>
                                        <#if startIndex <= p?index && p?index < lastIndex>
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
                                        </#if>
                                    </#list>
                                </#if>
                            </div>
                        </div>

                        <div class="action-bar-inner mt-30">
                            <div class="row align-items-center">
                                <div class="col-sm-12 pag">

                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-3 shop-filter">
                        <div class="sidebar-item">
                            <h4 class="sidebar-title collapsed" data-toggle="collapse" role="button" aria-expanded="false" aria-controls="category" data-target=".category">Категория</h4>
                            <div class="collapse multi-collapse sidebar-body category">
                                <div class="edjll-select">
                                    <div class="edjll-select__settings">
                                        <span class="edjll-select__settings__attributes">{"name": "category", "itemVisibleValue":["name"]<#if category??>, "defaultValue":{"id":${category.id}, "name":"${category.name}"}</#if>}</span>
                                        <span class="edjll-select__settings__request">{"ajax":true, "url":"http://localhost:8080<#if category??>/category/${category.id}/children/all<#else>/category/children/all</#if>"}</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="sidebar-item">
                            <h4 class="sidebar-title collapsed" data-toggle="collapse" role="button" aria-expanded="false" aria-controls="cost" data-target=".cost">Стоимость</h4>
                            <div class="collapse multi-collapse sidebar-body cost">
                                <div class="edjll-input" id="name">
                                    <div class="edjll-input__settings">
                                        <span class="edjll-input__settings__attributes">{"name": "min", "label": "Мин", "type":"number", "defaultValue":<#if search??><#if 0 < search.min?number>${search.min?c}<#else>"0"</#if><#else>"0"</#if>}</span>
                                        <span class="edjll-input__settings__validation">{"min": 0}}</span>
                                    </div>
                                </div>
                                <div class="edjll-input" id="name">
                                    <div class="edjll-input__settings">
                                        <span class="edjll-input__settings__attributes">{"name": "max", "label": "Макс", "type":"number", "defaultValue":<#if search??>${search.max?c}<#else>${maxPrice?c}</#if>}</span>
                                        <span class="edjll-input__settings__validation">{"min": 0}</span>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <#if attributes??>
                            <#assign att = search.attributes>
                            <#list attributes as attribute>
                                <#if attribute.values??>
                                    <div class="sidebar-item edjll-form-group">
                                        <div class="edjll-form-group__settings">
                                            <span class="edjll-form-group__settings__attributes">{"name":"attributes[]"}</span>
                                        </div>
                                        <div class="edjll-input">
                                            <div class="edjll-input__settings">
                                                <span class="edjll-input__settings__attributes">{"name": "id", "type": "hidden", "defaultValue": "${attribute.id}"}</span>
                                            </div>
                                        </div>
                                        <#assign values = []>
                                        <#assign attr = []>
                                        <#assign attr = att?filter(att -> att.id == attribute.id)>
                                        <#if attr?first?? && attr?first.values??><#assign values = attr?first.values></#if>
                                        <h4 class="sidebar-title collapsed" data-toggle="collapse" role="button" aria-expanded="false" aria-controls="attribute${attribute?index}" data-target=".attribute${attribute?index}">${attribute.name}</h4>
                                        <div class="collapse multi-collapse sidebar-body attribute${attribute?index}">
                                            <#list attribute.values?sort_by("value") as value>
                                                <#assign val = []>
                                                <#if values??><#assign val = values?filter(v -> v.id == value.id)></#if>
                                                <div class="sidebar-item edjll-form-group">
                                                    <div class="edjll-form-group__settings">
                                                        <span class="edjll-form-group__settings__attributes">{"name":"values[]"}</span>
                                                    </div>
                                                    <div class="edjll-input">
                                                        <div class="edjll-input__settings">
                                                            <span class="edjll-input__settings__attributes">{"name": "id", "type": "hidden", "defaultValue": "${value.id}"}</span>
                                                        </div>
                                                    </div>
                                                    <div class="edjll-checkbox">
                                                        <div class="edjll-checkbox__settings">
                                                            <span class="edjll-checkbox__settings__attributes">{"name": "status", "label": "${value.value + "  " + value.unit}"<#if val?first??>,"defaultValue":${(val?first.status)?then("true", "false")}</#if>}</span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </#list>
                                        </div>
                                    </div>
                                </#if>
                            </#list>
                        </#if>

                        <button class="edjll-button">Показать</button>
                    </div>
                </div>
            </div>
        </div>
    </form>
</#assign>

<#assign scripts>
    <script src="/static/js/shop/main.js"></script>
    <@edjllForm.scripts></@edjllForm.scripts>
    <script src="/static/js/shop/product/countInput.js"></script>
    <script src="/static/js/shop/product/addToCart.js"></script>
    <script>
        const page = ${productsPage.number};
        const totalPages = ${productsPage.content?size};
        const pageSize = ${productsPage.size};
    </script>
    <script src="/static/js/shop/pagination.js"></script>
    <script src="/static/js/shop/select.js"></script>
</#assign>

<@template.page
    links = links
body = body
scripts = scripts
>
</@template.page>