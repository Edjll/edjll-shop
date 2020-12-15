<#import "../macro/admin.ftlh" as admin>
<#import "../macro/template.ftlh" as template>
<#import "../../macro/edjllForm.ftlh" as edjllForm>

<#assign links>
    <link rel="stylesheet" href="/static/css/edjllForm/edjllForm.css">
    <link rel="stylesheet" href="/static/css/table/tableInEdjllForm.css">
</#assign>

<#assign body>
    <main>
        <form class="container edjll-form" name="updateSale">
            <div class="edjll-spinner"></div>
            <div class="edjll-input-style">
                <div class="edjll-input-style__label">
                    <span class="edjll-input-style__label__text">Пользователь</span>
                    <span class="edjll-input-style__label__input">${sale.user.email}</span>
                </div>
            </div>
            <div class="edjll-input-style">
                <div class="edjll-input-style__label">
                    <span class="edjll-input-style__label__text">Дата</span>
                    <span class="edjll-input-style__label__input">${sale.date}</span>
                </div>
            </div>
            <div class="edjll-input-style">
                <div class="edjll-input-style__label">
                    <span class="edjll-input-style__label__text">Адрес</span>
                    <span class="edjll-input-style__label__input"><#assign address = sale.saleProducts?first.delivery.address><#if address??>${address}</#if></span>
                </div>
            </div>
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "id", "type": "hidden", "defaultValue": "${sale.id}"}</span>
                </div>
            </div>
            <div class="table-wrapper">
                <div class="table">
                    <div class="table__head">
                        <div class="table__head__item table__head__item--id">#</div>
                        <div class="table__head__item">Товар</div>
                        <div class="table__head__item">Склад</div>
                        <div class="table__head__item">Статус</div>
                    </div>
                    <div class="table__body">
                        <#if sale.saleProducts??>
                            <#list sale.saleProducts as sp>
                                <div class="table__body__row edjll-form-group">
                                    <div class="edjll-form-group__settings">
                                        <span class="edjll-form-group__settings__attributes">{"name": "deliveries[]", "deleteNodeClassName":"table__body__row__item-remove"}</span>
                                    </div>
                                    <div class="table__body__row__item table__body__row__item--id">
                                        <div class="edjll-input-style">
                                            <div class="edjll-input-style__label">
                                                <span class="edjll-input-style__label__input">${sp.product.id}</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="table__body__row__item">
                                        <div class="edjll-input-style">
                                            <div class="edjll-input-style__label">
                                                <span class="edjll-input-style__label__input">${sp.product.productData.name}</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="table__body__row__item">
                                        <div class="edjll-input-style">
                                            <div class="edjll-input-style__label">
                                                <span class="edjll-input-style__label__input">${sp.product.supply.storage.name}</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="table__body__row__item">
                                        <div class="edjll-select">
                                            <div class="edjll-select__settings">
                                                <span class="edjll-select__settings__attributes">{"name": "status", "inputValueFormat":{"type":"json", "pattern":["id", "name"]}, "outputValueFormat":{"type":"var", "pattern":["id"]}, "defaultValue": {"id":${sp.delivery.statusDelivery.ordinal()}, "name":"${sp.delivery.statusDelivery.text}"}, "options":[<#list statuses as status>{"id":${status.ordinal()}, "name":"${status.text}"}<#if status?has_next>,</#if></#list>], "itemVisibleValue":["name"]}</span>
                                                <span class="edjll-select__settings__request">{"pagination":false, "searchParam":false}</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="edjll-input">
                                        <div class="edjll-input__settings">
                                            <span class="edjll-input__settings__attributes">{"name": "id", "type": "hidden", "defaultValue": "${sp.delivery.id}"}</span>
                                        </div>
                                    </div>
                                </div>
                            </#list>
                        </#if>
                    </div>
                </div>
            </div>
            <script src="/static/js/manufacturer/form.js"></script>
            <button type="submit" class="edjll-button">Сохранить</button>
        </form>
    </main>
</#assign>

<#assign scripts>
    <script src="/static/js/sale/update.js"></script>
    <@edjllForm.scripts></@edjllForm.scripts>
</#assign>

<@template.adminPage
bodyBody = body
links = links
scripts=scripts
page = "Изменение покупки"
>
</@template.adminPage>