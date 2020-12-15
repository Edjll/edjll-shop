<#import "../macro/template.ftlh" as template>
<#import "../../macro/security.ftlh" as security>
<#import "../../macro/pagination.ftlh" as pagination>
<#import "../../macro/edjllForm.ftlh" as edjllForm>

<#assign title = "Профиль">

<#assign links>
    <link rel="stylesheet" href="/static/css/edjllForm/edjllPagination.css">
    <link rel="stylesheet" href="/static/css/table/table.css">
    <link rel="stylesheet" href="/static/css/user/profile.css">
    <link rel="stylesheet" href="/static/css/bootstrap/bootstrapCustom.css">
</#assign>

<#assign body>
    <main>
        <div class="container user my-5">
            <ul id="myTab" class="nav nav-tabs user__header">
                <li class="nav-item"><a href="/user/profile" class="nav-link">Информация</a></li>
                <li class="nav-item"><a href="/user/profile/sales" class="nav-link">Покупки</a></li>
                <li class="nav-item"><a href="/user/profile/reviews" class="nav-link active">Отзывы</a></li>
                <li class="nav-item"><a href="/user/profile/support" class="nav-link">Поддержка</a></li>
                <li class="nav-item"><a href="/user/profile/refunds" class="nav-link">Возвраты</a></li>
            </ul>
            <div class="user__body p-0">
                <div class="table">
                    <div class="table__head">
                        <div class="table__head__item table__head__item--id">#</div>
                        <div class="table__head__item">Дата</div>
                        <div class="table__head__item">Продукт</div>
                        <div class="table__head__item">Статус</div>
                        <div class="table__head__item">Действия</div>
                    </div>
                    <div class="table__body">
                        <#if 0 < reviewsPage.totalPages>
                            <#list reviewsPage.getContent() as review>
                                <div class="table__body__row">
                                    <div class="table__body__row__item table__body__row__item--id">
                                        ${review.id}
                                    </div>
                                    <div class="table__body__row__item table__body__row__item--truncate">
                                        ${review.date}
                                    </div>
                                    <div class="table__body__row__item table__body__row__item--truncate">
                                        <a href="/shop/product/${review.productData.id}">${review.productData.name}</a>
                                    </div>
                                    <div class="table__body__row__item table__body__row__item--truncate">
                                        <#if review.statusReview.ordinal() == 0>
                                            <span class="status-processing">
                                        <#elseif review.statusReview.ordinal() == 1>
                                            <span class="status-success">
                                        <#else>
                                            <span class="status-error">
                                        </#if>
                                            ${review.statusReview.text}</span>
                                    </div>
                                    <div class="table__body__row__item">
                                        <div class="dropdown">
                                            <button class="btn-sm btn-custom" type="button" id="dropdownMenuButton"
                                                    data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                <i class="ion-more"></i>
                                            </button>
                                            <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenuButton">
                                                <a class="dropdown-item" href="/shop/product/${review.productData.id}">Изменить</a>
                                                <form class="dropdown-item" name="deleteReview">
                                                    <div class="edjll-input">
                                                        <div class="edjll-input__settings">
                                                            <span class="edjll-input__settings__attributes">{"name": "id", "type": "hidden", "defaultValue": "${review.id}"}</span>
                                                        </div>
                                                    </div>
                                                    <button>Удалить</button>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </#list>
                        <#else>
                            <div class="table__body__row justify-content-center">Пусто</div>
                        </#if>
                    </div>
                </div>
                <@pagination.template
                page = reviewsPage
                url = "/user/profile/reviews"
                ></@pagination.template>
            </div>
        </div>
    </main>
</#assign>

<#assign scripts>
    <script src="/static/js/support/review/delete.js"></script>
    <@edjllForm.scripts></@edjllForm.scripts>
</#assign>

<@template.page
title = title
links = links
body = body
scripts = scripts
>
</@template.page>