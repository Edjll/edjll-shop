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
                    <div class="table__head__item">Продажа</div>
                    <div class="table__head__item">Причина</div>
                    <div class="table__head__item">Статус</div>
                    <div class="table__head__item">Комментарий</div>
                    <div class="table__head__item">Действия</div>
                </div>
                <div class="table__body">
                    <#if refundPage??>
                        <#list refundPage.getContent() as refund>
                            <div class="table__body__row">
                                <div class="table__body__row__item table__body__row__item--id">
                                    ${refund.id}
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate">
                                    ${refund.date}
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate">
                                    ${refund.saleProduct.sale.user.email}
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate">
                                    ${refund.saleProduct.id}
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate">
                                    ${refund.reason}
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
                                    <#if refund.comment??>${refund.comment}</#if>
                                </div>
                                <div class="table__body__row__item">
                                    <div class="dropdown">
                                        <button class="btn btn-sm btn-custom" type="button" id="dropdownMenuButton"
                                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                            <i class="ion-more"></i>
                                        </button>
                                        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenuButton">
                                            <a class="dropdown-item" href="/admin/refund/update/${refund.id}">Изменить</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </#list>
                    </#if>
                </div>
            </div>
            <@pagination.template
                page = refundPage
                url = "/admin/refund/all"
            ></@pagination.template>
        </div>
    </main>
</#assign>

<@template.adminPage
bodyBody = body
links=links
page = "Возвраты"
scripts = scripts
></@template.adminPage>