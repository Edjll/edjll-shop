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
                    <div class="table__head__item">Название</div>
                    <div class="table__head__item">Описание</div>
                    <div class="table__head__item">Дата начала</div>
                    <div class="table__head__item">Дата окончания</div>
                    <div class="table__head__item">Баннер</div>
                    <div class="table__head__item">Действия</div>
                </div>
                <div class="table__body">
                    <#if promotionsPage??>
                        <#list promotionsPage.getContent() as promotion>
                            <div class="table__body__row">
                                <div class="table__body__row__item table__body__row__item--id">
                                    <a href="/promotions/${promotion.id}/products">${promotion.id}</a>
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate">
                                    ${promotion.name}
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate">
                                    <#noautoesc>${promotion.description}</#noautoesc>
                                </div>
                                <div class="table__body__row__item">
                                    ${promotion.dateStart}
                                </div>
                                <div class="table__body__row__item">
                                    ${promotion.dateEnd}
                                </div>
                                <div class="table__body__row__item">
                                    <#if promotion.banner??>
                                        <a href="/image/promotionBanner/${promotion.banner.filename}" target="_blank">
                                            <img src="/image/promotionBanner/${promotion.banner.filename}" alt="${promotion.banner.filename}">
                                        </a>
                                    </#if>
                                </div>
                                <div class="table__body__row__item">
                                    <div class="dropdown">
                                        <button class="btn btn-sm btn-custom" type="button" id="dropdownMenuButton"
                                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                            <i class="ion-more"></i>
                                        </button>
                                        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenuButton">
                                            <a class="dropdown-item" href="/admin/promotion/update/${promotion.id}">Изменить</a>
                                            <form class="dropdown-item" name="deletePromotion">
                                                <div class="edjll-input">
                                                    <div class="edjll-input__settings">
                                                        <span class="edjll-input__settings__attributes">{"name": "id", "type": "hidden", "defaultValue": "${promotion.id}"}</span>
                                                    </div>
                                                </div>
                                                <button>Удалить</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </#list>
                    </#if>
                </div>
            </div>
            <@pagination.template
            page = promotionsPage
            url = "/admin/promotion/all"
            ></@pagination.template>
        </div>
    </main>
</#assign>

<#assign scripts>
    <script src="/static/js/promotion/delete.js"></script>
    <@edjllForm.scripts></@edjllForm.scripts>
</#assign>

<@template.adminPage
bodyBody = body
links=links
page = "Акции"
scripts = scripts
></@template.adminPage>