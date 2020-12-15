<#import "../macro/admin.ftlh" as admin>
<#import "../macro/template.ftlh" as template>
<#import "../../macro/edjllForm.ftlh" as edjllForm>

<#assign links>
    <link rel="stylesheet" href="/static/css/edjllForm/edjllForm.css">
    <link rel="stylesheet" href="/static/css/table/tableInEdjllForm.css">
</#assign>

<#assign body>
    <main>
        <form class="container edjll-form" name="createPromotion">
            <div class="edjll-spinner"></div>
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "name", "label": "Название"}</span>
                    <span class="edjll-input__settings__validation">{"minLength":5, "maxLength":40, "required":true}</span>
                </div>
            </div>
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "dateStart", "label": "Дата старта", "type": "datetime-local"}</span>
                    <span class="edjll-input__settings__validation">{"required":true}</span>
                </div>
            </div>
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "dateEnd", "label": "Дата окончания", "type": "datetime-local"}</span>
                    <span class="edjll-input__settings__validation">{"required":true, "laterThan": "dateStart"}</span>
                </div>
            </div>
            <div class="edjll-textarea">
                <div class="edjll-textarea__settings">
                    <span class="edjll-textarea__settings__attributes">{"name": "description", "label": "Описание"}</span>
                    <span class="edjll-textarea__settings__validation">{"maxLength":300, "minLength":5}</span>
                </div>
            </div>
            <div class="edjll-file-input">
                <div class="edjll-file-input__settings">
                    <span class="edjll-file-input__settings__attributes">{"name": "banner", "label": "Баннер", "type": "file", "accept":"image/png, image/jpeg"}</span>
                    <span class="edjll-file-input__settings__validation">{"required": true}</span>
                </div>
            </div>
            <div class="table-wrapper">
                <div class="table">
                    <div class="table__head">
                        <div class="table__head__item">Продукт</div>
                        <div class="table__head__item">Скидка</div>
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
                                        <span class="edjll-select__settings__attributes">{"name": "product", "label": "Продукт", "itemVisibleValue":["name"]}</span>
                                        <span class="edjll-select__settings__request">{"ajax":true, "url":"http://localhost:8080/admin/product/get/without/promotion", "excludedValues":"product"}</span>
                                        <span class="edjll-select__settings__validation">{"required":true}</span>
                                    </div>
                                </div>
                            </div>
                            <div class="table__body__row__item">
                                <div class="edjll-input">
                                    <div class="edjll-input__settings">
                                        <span class="edjll-input__settings__attributes">{"name": "discount", "label": "%", "type": "number"}</span>
                                        <span class="edjll-input__settings__validation">{"min":0, "max":100, "required":true}</span>
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
                                <div class="table__body__create-row__item__text">%</div>
                                <div class="table__body__create-row__item__input"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <script src="/static/js/manufacturer/form.js"></script>
            <button type="submit" class="edjll-button">Сохранить</button>
        </form>
    </main>
</#assign>

<#assign scripts>
    <script src="/static/js/promotion/create.js"></script>
    <@edjllForm.scripts></@edjllForm.scripts>
    <script src="/static/js/promotion/main.js"></script>
</#assign>

<@template.adminPage
bodyBody = body
links = links
scripts=scripts
    page = "Создание акции"
>
</@template.adminPage>