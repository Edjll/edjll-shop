<#import "../macro/template.ftlh" as template>
<#import "../../macro/security.ftlh" as security>

<#assign title = "Профиль">

<#assign links>
    <link rel="stylesheet" href="/static/css/table/table.css">
    <link rel="stylesheet" href="/static/css/user/profile.css">
</#assign>

<#assign body>
    <main>
        <div class="container user my-5">
            <ul id="myTab" class="nav nav-tabs user__header">
                <li class="nav-item"><a href="/user/profile" class="nav-link">Информация</a></li>
                <li class="nav-item"><a href="/user/profile/sales" class="nav-link active">Покупки</a></li>
                <li class="nav-item"><a href="/user/profile/reviews" class="nav-link">Отзывы</a></li>
                <li class="nav-item"><a href="/user/profile/support" class="nav-link">Поддержка</a></li>
                <li class="nav-item"><a href="/user/profile/refunds" class="nav-link">Возвраты</a></li>
            </ul>
            <div class="user__body p-0">
                <div class="table">
                    <div class="table__head">
                        <div class="table__head__item table__head__item--id">#</div>
                        <div class="table__head__item">Продукт</div>
                        <div class="table__head__item">Статус</div>
                        <div class="table__head__item">Возврат</div>
                    </div>
                    <div class="table__body">
                        <#list sales as sale>
                            <div class="table__body__row">
                                <div class="table__body__row__item table__body__row__item--id">
                                    ${sale.id}
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate">
                                    <a href="/shop/product/${sale.product.productData.id}">${sale.product.productData.name}</a>
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate">
                                    <#if sale.delivery.statusDelivery.ordinal() == 0>
                                        <span class="status-error">
                                    <#elseif sale.delivery.statusDelivery.ordinal() == 1>
                                        <span class="status-processing">
                                    <#else>
                                        <span class="status-success">
                                    </#if>
                                        ${sale.delivery.statusDelivery.text}</span>
                                </div>
                                <div class="table__body__row__item">
                                    <#if sale.delivery.statusDelivery.ordinal() == 2 && !sale.refund??>
                                        <a class="edjll-button-style mt-0" href="/user/profile/sales/refund/${sale.id}">Запросить</a>
                                    </#if>
                                    <#if sale.refund??>
                                        <a class="edjll-button-style mt-0" href="/user/profile/refunds/${sale.refund.id}">Запрошено</a>
                                    </#if>
                                </div>
                            </div>
                        </#list>
                    </div>
                </div>
            </div>
        </div>
    </main>
</#assign>

<@template.page
title = title
links = links
body = body
scripts = scripts
>
</@template.page>