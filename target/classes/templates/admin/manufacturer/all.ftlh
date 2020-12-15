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
                    <div class="table__head__item">Страна</div>
                    <div class="table__head__item">Описание</div>
                    <div class="table__head__item">Логотип</div>
                    <div class="table__head__item">Действия</div>
                </div>
                <div class="table__body">
                    <#if manufacturersPage??>
                        <#list manufacturersPage.getContent() as manufacturer>
                            <div class="table__body__row">
                                <div class="table__body__row__item table__body__row__item--id">
                                    ${manufacturer.id}
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate">
                                    ${manufacturer.name}
                                </div>
                                <div class="table__body__row__item">
                                    <#if manufacturer.country??>${manufacturer.country.name}</#if>
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate">
                                    ${manufacturer.description}
                                </div>
                                <div class="table__body__row__item">
                                    <#if manufacturer.logo??>
                                        <a href="/image/manufacturerLogo/${manufacturer.logo.filename}" target="_blank">
                                            <img src="/image/manufacturerLogo/${manufacturer.logo.filename}" alt="${manufacturer.logo.filename}">
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
                                            <a class="dropdown-item" href="/admin/manufacturer/update/${manufacturer.id}">Изменить</a>
                                            <form class="dropdown-item" name="deleteManufacturer">
                                                <div class="edjll-input">
                                                    <div class="edjll-input__settings">
                                                        <span class="edjll-input__settings__attributes">{"name": "id", "type": "hidden", "defaultValue": "${manufacturer.id}"}</span>
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
                page = manufacturersPage
                url = "/admin/manufacturer/all"
            ></@pagination.template>
        </div>
    </main>
</#assign>

<#assign scripts>
    <script src="/static/js/manufacturer/delete.js"></script>
    <@edjllForm.scripts></@edjllForm.scripts>
</#assign>

<@template.adminPage
bodyBody = body
links=links
page = "Производители"
scripts = scripts
></@template.adminPage>