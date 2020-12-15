<#import "../macro/admin.ftlh" as admin>
<#import "../macro/template.ftlh" as template>
<#import "../../macro/edjllForm.ftlh" as edjllForm>

<#assign links>
    <link rel="stylesheet" href="/static/css/edjllForm/edjllForm.css">
    <link rel="stylesheet" href="/static/css/table/tableInEdjllForm.css">
</#assign>

<#assign body>
    <main>
        <form class="container edjll-form" name="updateSupply">
            <div class="edjll-spinner"></div>
            <div class="edjll-select">
                <div class="edjll-select__settings">
                    <span class="edjll-select__settings__attributes">{"name": "storage","label": "Склад", "itemVisibleValue":["name"],"options": [<#list storages as storage>{"id": ${storage.id}, "name": "${storage.name}"}<#if storage?has_next>,</#if></#list>], "defaultValue":{"id":"${supply.storage.id}", "name":"${supply.storage.name}"}}</span>
                    <span class="edjll-select__settings__validation">{"required":true}</span>
                </div>
            </div>
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "date", "label": "Дата", "type": "datetime-local", "defaultValue":"${supply.date?iso_m_nz("Europe/Samara")}"}</span>
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
                        <#list supplyProductData as spd>
                            <div class="table__body__row">
                                <div class="table__body__row__item">
                                    <div class="edjll-input-style">
                                        <div class="edjll-input-style__label">
                                            <span class="edjll-input-style__label__text">Продукт</span>
                                            <span class="edjll-input-style__label__input">${spd.productName}</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="table__body__row__item">
                                    <div class="edjll-input-style">
                                        <div class="edjll-input-style__label">
                                            <span class="edjll-input-style__label__text">Количество</span>
                                            <span class="edjll-input-style__label__input">${spd.count}</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </#list>
                    </div>
                </div>
            </div>
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "id", "type": "hidden", "defaultValue": "${supply.id}"}</span>
                </div>
            </div>
            <button type="submit" class="edjll-button">Сохранить</button>
        </form>
    </main>
</#assign>

<#assign scripts>
    <script src="/static/js/supply/update.js"></script>
    <@edjllForm.scripts></@edjllForm.scripts>
</#assign>

<@template.adminPage
bodyBody = body
links = links
scripts=scripts
page = "Обновление поставки"
>
</@template.adminPage>