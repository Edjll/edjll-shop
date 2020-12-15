<#import "../../macro/admin.ftlh" as admin>
<#import "../../macro/template.ftlh" as template>
<#import "../../../macro/pagination.ftlh" as pagination>

<#assign links>
    <link rel="stylesheet" href="/static/css/edjllForm/edjllPagination.css">
    <link rel="stylesheet" href="/static/css/table/table.css">
</#assign>

<#assign body>
    <main>
        <div class="container table-wrapper">
            <div class="table">
                <div class="table__head">
                    <div class="table__head__item table__head__item--id">#</div>
                    <div class="table__head__item">Пользователь</div>
                    <div class="table__head__item">Дата</div>
                    <div class="table__head__item">Продукт</div>
                    <div class="table__head__item">Статус</div>
                    <div class="table__head__item">Действия</div>
                </div>
                <div class="table__body">
                    <#if reviewsPage??>
                        <#list reviewsPage.getContent() as review>
                            <div class="table__body__row">
                                <div class="table__body__row__item table__body__row__item--id">
                                    ${review.id}
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate">
                                    ${review.user.email}
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate">
                                    ${review.date}
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate">
                                    <a href="/shop/product/${review.productData.id}" target="_blank">${review.productData.name}</a>
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
                                        <button class="btn btn-sm btn-custom" type="button" id="dropdownMenuButton"
                                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                            <i class="ion-more"></i>
                                        </button>
                                        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenuButton">
                                            <a class="dropdown-item" href="/admin/review/update/${review.id}">Изменить</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </#list>
                    </#if>
                </div>
            </div>
            <@pagination.template
            page = reviewsPage
            url = "/admin/review/all"
            ></@pagination.template>
        </div>
    </main>
</#assign>

<@template.adminPage
bodyBody = body
links=links
page = "Обзоры"
></@template.adminPage>