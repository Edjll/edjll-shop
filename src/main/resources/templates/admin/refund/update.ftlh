<#import "../macro/admin.ftlh" as admin>
<#import "../macro/template.ftlh" as template>
<#import "../../macro/edjllForm.ftlh" as edjllForm>

<#assign links>
    <link rel="stylesheet" href="/static/css/edjllForm/edjllForm.css">
    <link rel="stylesheet" href="/static/css/table/tableInEdjllForm.css">
</#assign>

<#assign body>
    <main>
        <form class="container edjll-form" name="updateRefund">
            <div class="edjll-spinner"></div>
            <div class="edjll-input-style">
                <div class="edjll-input-style__label">
                    <span class="edjll-input-style__label__text">Пользователь</span>
                    <span class="edjll-input-style__label__input">${refund.saleProduct.sale.user.email}</span>
                </div>
            </div>
            <div class="edjll-input-style">
                <div class="edjll-input-style__label">
                    <span class="edjll-input-style__label__text">Дата</span>
                    <span class="edjll-input-style__label__input">${refund.date}</span>
                </div>
            </div>
            <div class="edjll-input-style">
                <div class="edjll-input-style__label">
                    <span class="edjll-input-style__label__text">Продажа</span>
                    <span class="edjll-input-style__label__input">${refund.saleProduct.id}</span>
                </div>
            </div>
            <div class="edjll-input-style">
                <div class="edjll-input-style__label">
                    <span class="edjll-input-style__label__text">Причина</span>
                    <span class="edjll-input-style__label__input">${refund.reason}</span>
                </div>
            </div>
            <div class="edjll-input-style">
                <div class="edjll-input-style__label">
                    <span class="edjll-input-style__label__text">Тип возврата</span>
                    <span class="edjll-input-style__label__input">${refund.typeRefund.text}</span>
                </div>
            </div>
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "id", "type": "hidden", "defaultValue": "${refund.id}"}</span>
                </div>
            </div>
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "comment", "label": "Комментарий", "type": "text"<#if refund.comment??>, "defaultValue": "${refund.comment}"</#if>}</span>
                </div>
            </div>
            <#if refund.statusRefund.ordinal() != 1>
                <div class="edjll-select">
                    <div class="edjll-select__settings">
                        <span class="edjll-select__settings__attributes">{"name": "status", "inputValueFormat":{"type":"json", "pattern":["id", "name"]}, "outputValueFormat":{"type":"var", "pattern":["id"]}, "defaultValue": {"id":${refund.statusRefund.ordinal()}, "name":"${refund.statusRefund.text}"}, "options":[<#list statuses as status>{"id":${status.ordinal()}, "name":"${status.text}"}<#if status?has_next>,</#if></#list>], "itemVisibleValue":["name"]}</span>
                        <span class="edjll-select__settings__request">{"pagination":false, "searchParam":false}</span>
                    </div>
                </div>
            <#else>
                <div class="edjll-input-style">
                    <div class="edjll-input-style__label">
                        <span class="edjll-input-style__label__text">Статус</span>
                        <span class="edjll-input-style__label__input">${refund.statusRefund.text}</span>
                    </div>
                </div>
                <div class="edjll-input">
                    <div class="edjll-input__settings">
                        <span class="edjll-input__settings__attributes">{"name": "status", "type": "hidden", "defaultValue": "${refund.statusRefund.ordinal()}"}</span>
                    </div>
                </div>
            </#if>
            <script src="/static/js/manufacturer/form.js"></script>
            <button type="submit" class="edjll-button">Сохранить</button>
        </form>
    </main>
</#assign>

<#assign scripts>
    <script src="/static/js/refund/update.js"></script>
    <@edjllForm.scripts></@edjllForm.scripts>
</#assign>

<@template.adminPage
bodyBody = body
links = links
scripts=scripts
page = "Изменение возврата"
>
</@template.adminPage>