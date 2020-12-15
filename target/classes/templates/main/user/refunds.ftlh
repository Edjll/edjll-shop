<#import "../macro/template.ftlh" as template>
<#import "../../macro/security.ftlh" as security>
<#import "../../macro/pagination.ftlh" as pagination>

<#assign title = "Профиль">

<#assign links>
    <link rel="stylesheet" href="/static/css/edjllForm/edjllPagination.css">
    <link rel="stylesheet" href="/static/css/table/table.css">
    <link rel="stylesheet" href="/static/css/user/profile.css">
</#assign>

<#assign body>
    <main>
        <div class="container user my-5">
            <ul id="myTab" class="nav nav-tabs user__header">
                <li class="nav-item"><a href="/user/profile" class="nav-link">Информация</a></li>
                <li class="nav-item"><a href="/user/profile/sales" class="nav-link">Покупки</a></li>
                <li class="nav-item"><a href="/user/profile/reviews" class="nav-link">Отзывы</a></li>
                <li class="nav-item"><a href="/user/profile/support" class="nav-link">Поддержка</a></li>
                <li class="nav-item"><a href="/user/profile/refunds" class="nav-link active">Возвраты</a></li>
            </ul>
            <div class="user__body p-0">
                <div class="table">
                    <div class="table__head">
                        <div class="table__head__item table__head__item--id">#</div>
                        <div class="table__head__item">Продажа</div>
                        <div class="table__head__item">Статус</div>
                        <div class="table__head__item">Дата</div>
                        <div class="table__head__item">Комментарий</div>
                        <div class="table__head__item">Подробнее</div>
                    </div>
                    <div class="table__body">
                        <#if 0 < refundsPage.totalPages>
                            <#list refundsPage.getContent() as refund>
                                <div class="table__body__row">
                                    <div class="table__body__row__item table__body__row__item--id">
                                        ${refund.id}
                                    </div>
                                    <div class="table__body__row__item table__body__row__item--truncate">
                                        ${refund.saleProduct.id}
                                    </div>
                                    <div class="table__body__row__item table__body__row__item--truncate">
                                        <#if refund.statusRefund.ordinal() == 0>
                                            <span class="status-processing">
                                        <#elseif refund.statusRefund.ordinal() == 1>
                                            <span class="status-success">
                                        <#else>
                                            <span class="status-error">
                                        </#if>
                                            ${refund.statusRefund.text}</span>
                                    </div>
                                    <div class="table__body__row__item table__body__row__item--truncate">
                                        ${refund.date}
                                    </div>
                                    <div class="table__body__row__item table__body__row__item--truncate">
                                        <#if refund.comment??>${refund.comment}</#if>
                                    </div>
                                    <div class="table__body__row__item">
                                        <a class="edjll-button-style mt-0" href="/user/profile/refunds/${refund.id}">Подробнее</a>
                                    </div>
                                </div>
                            </#list>
                        <#else>
                            <div class="table__body__row justify-content-center">Пусто</div>
                        </#if>
                    </div>
                </div>
                <@pagination.template
                page = refundsPage
                url = "/user/profile/refunds"
                ></@pagination.template>
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