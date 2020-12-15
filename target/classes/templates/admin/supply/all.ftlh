<#import "../macro/admin.ftlh" as admin>
<#import "../macro/template.ftlh" as template>
<#import "../../macro/pagination.ftlh" as pagination>

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
                    <div class="table__head__item">Склад</div>
                    <div class="table__head__item">Дата</div>
                    <div class="table__head__item">Действия</div>
                </div>
                <div class="table__body">
                    <#if supplyPage??>
                        <#list supplyPage.getContent() as supply>
                            <div class="table__body__row">
                                <div class="table__body__row__item table__body__row__item--id">
                                    ${supply.id}
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate">
                                    ${supply.storage.name}
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate">
                                    ${supply.date}
                                </div>
                                <div class="table__body__row__item">
                                    <div class="dropdown">
                                        <button class="btn btn-sm btn-custom" type="button" id="dropdownMenuButton"
                                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                            <i class="ion-more"></i>
                                        </button>
                                        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenuButton">
                                            <a class="dropdown-item" href="/admin/supply/update/${supply.id}">Изменить</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </#list>
                    </#if>
                </div>
            </div>
            <@pagination.template
            page = supplyPage
            url = "/admin/supply/all"
            ></@pagination.template>
        </div>
    </main>
</#assign>

<@template.adminPage
bodyBody = body
links=links
page = "Поставки"
></@template.adminPage>