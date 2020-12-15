<#import "../macro/admin.ftlh" as admin>
<#import "../macro/template.ftlh" as template>
<#import "../../macro/edjllForm.ftlh" as edjllForm>

<#assign links>
    <link rel="stylesheet" href="/static/css/edjllForm/edjllForm.css">
    <link rel="stylesheet" href="/static/css/table/tableInEdjllForm.css">
</#assign>

<#assign body>
    <main>
        <form class="container edjll-form" name="createSupply">
            <div class="edjll-spinner"></div>
            <div class="edjll-select">
                <div class="edjll-select__settings">
                    <span class="edjll-select__settings__attributes">{"name": "storage","label": "Склад", "itemVisibleValue":["name"],"options": [<#list storages as storage>{"id": ${storage.id}, "name": "${storage.name}"}<#if storage?has_next>,</#if></#list>]}</span>
                    <span class="edjll-select__settings__validation">{"required":true}</span>
                </div>
            </div>
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "date", "label": "Дата", "type": "datetime-local"}</span>
                    <span class="edjll-input__settings__validation">{"required":true}</span>
                </div>
            </div>
            <div class="table-wrapper">
                <div class="table">
                    <div class="table__head">
                        <div class="table__head__item">Продукт</div>
                        <div class="table__head__item">Количество</div>
                    </div>
                    <div class="table__body">
                        <div class="table__body__row edjll-form-group">
                            <div class="edjll-form-group__settings">
                                <span class="edjll-form-group__settings__attributes">{"name": "products[]", "deleteNodeClassName":"table__body__row__item-remove"}</span>
                            </div>
                            <div class="table__body__row__item-remove">

                            </div>
                            <div class="table__body__row__item">
                                <div class="edjll-select">
                                    <div class="edjll-select__settings">
                                        <span class="edjll-select__settings__attributes">{"name": "product", "itemVisibleValue":["name"], "label": "Продукт"}</span>
                                        <span class="edjll-select__settings__request">{"ajax":true, "url":"http://localhost:8080/admin/product/get", "excludedValues":"product"}</span>
                                        <span class="edjll-select__settings__validation">{"required":true}</span>
                                    </div>
                                </div>
                            </div>
                            <div class="table__body__row__item">
                                <div class="edjll-input">
                                    <div class="edjll-input__settings">
                                        <span class="edjll-input__settings__attributes">{"name": "count", "label": "Количество", "type": "number"}</span>
                                        <span class="edjll-input__settings__validation">{"min":0, "max":10000, "required":true}</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="table__body__create-row">
                            <div class="table__body__create-row__item">
                                <div class="table__body__create-row__item__input"></div>
                                <div class="table__body__create-row__item__text">Продукт</div>
                            </div>
                            <div class="table__body__create-row__item">
                                <div class="table__body__create-row__item__text">Количество</div>
                                <div class="table__body__create-row__item__input"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <button type="submit" class="edjll-button">Сохранить</button>
        </form>
    </main>
</#assign>

<#assign scripts>
    <script src="/static/js/supply/create.js"></script>
    <@edjllForm.scripts></@edjllForm.scripts>
    <script src="/static/js/supply/main.js"></script>
</#assign>

<@template.adminPage
bodyBody = body
links = links
scripts=scripts
page = "Добавление поставки"
>
</@template.adminPage>