<#import "../macro/admin.ftlh" as admin>
<#import "../macro/template.ftlh" as template>
<#import "../../macro/pagination.ftlh" as pagination>
<#import "../../macro/edjllForm.ftlh" as edjllForm>

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
                    <div class="table__head__item">Дата</div>
                    <div class="table__head__item">Пользователь</div>
                    <div class="table__head__item">Адрес</div>
                    <div class="table__head__item">Действия</div>
                </div>
                <div class="table__body">
                    <#if salePage??>
                        <#list salePage.getContent() as sale>
                            <div class="table__body__row">
                                <div class="table__body__row__item table__body__row__item--id">
                                    ${sale.id}
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate">
                                    ${sale.date}
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate">
                                    ${sale.user}
                                </div>
                                <div class="table__body__row__item">
                                    ${sale.address}
                                </div>
                                <div class="table__body__row__item">
                                    <div class="dropdown">
                                        <button class="btn btn-sm btn-custom" type="button" id="dropdownMenuButton"
                                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                            <i class="ion-more"></i>
                                        </button>
                                        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenuButton">
                                            <a class="dropdown-item" href="/admin/sale/update/${sale.id}">Изменить</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </#list>
                    </#if>
                </div>
            </div>
            <@pagination.template
            page = salePage
            url = "/admin/sale/all"
            ></@pagination.template>
        </div>
    </main>
</#assign>

<@template.adminPage
bodyBody = body
links=links
page = "Продажи"
scripts = scripts
></@template.adminPage>