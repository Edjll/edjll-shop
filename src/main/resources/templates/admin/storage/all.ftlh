<#import "../macro/admin.ftlh" as admin>
<#import "../macro/template.ftlh" as template>
<#import "../../macro/pagination.ftlh" as pagination>
<#import "../../macro/edjllForm.ftlh" as edjllForm>

<#assign links>
    <link rel="stylesheet" href="/static/css/edjllForm/edjllForm.css">
    <link rel="stylesheet" href="/static/css/table/table.css">
</#assign>

<#assign body>
    <main>
        <div class="container table-wrapper">
            <div class="table">
                <div class="table__head">
                    <div class="table__head__item table__head__item--id">#</div>
                    <div class="table__head__item">Название</div>
                    <div class="table__head__item">Город</div>
                    <div class="table__head__item">Описание</div>
                    <div class="table__head__item">Действия</div>
                </div>
                <div class="table__body">
                    <#if storagesPage??>
                        <#list storagesPage.getContent() as storage>
                            <div class="table__body__row">
                                <div class="table__body__row__item table__body__row__item--id">
                                    ${storage.id}
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate">
                                    ${storage.name}
                                </div>
                                <div class="table__body__row__item">
                                    <#if storage.city??>${storage.city.name}</#if>
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate">
                                    ${storage.description}
                                </div>
                                <div class="table__body__row__item">
                                    <div class="dropdown">
                                        <button class="btn btn-sm btn-custom" type="button" id="dropdownMenuButton"
                                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                            <i class="ion-more"></i>
                                        </button>
                                        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenuButton">
                                            <a class="dropdown-item" href="/admin/storage/update/${storage.id}">Изменить</a>
                                            <form class="dropdown-item" name="deleteStorage">
                                                <div class="edjll-input">
                                                    <div class="edjll-input__settings">
                                                        <span class="edjll-input__settings__attributes">{"name": "id", "type": "hidden", "defaultValue": "${storage.id}"}</span>
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
                page = storagesPage
                url = "/admin/storage/all"
            ></@pagination.template>
        </div>
    </main>
</#assign>

<#assign scripts>
    <script src="/static/js/storage/delete.js"></script>
    <@edjllForm.scripts></@edjllForm.scripts>
</#assign>

<@template.adminPage
bodyBody = body
links=links
page = "Склады"
scripts = scripts
></@template.adminPage>