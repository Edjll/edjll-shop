<#import "../../macro/template.ftlh" as template>
<#import "../../../macro/security.ftlh" as security>
<#import "../../../macro/edjllForm.ftlh" as edjllForm>

<#assign links>
    <link rel="stylesheet" href="/static/css/edjllForm/edjllForm.css">
    <link rel="stylesheet" href="/static/css/user/profile.css">
</#assign>

<#assign body>
    <main>
        <div class="container user my-5">
            <ul id="myTab" class="nav nav-tabs user__header">
                <li class="nav-item"><a href="/user/profile" class="nav-link">Информация</a></li>
                <li class="nav-item"><a href="/user/profile/sales" class="nav-link">Покупки</a></li>
                <li class="nav-item"><a href="/user/profile/reviews" class="nav-link">Отзывы</a></li>
                <li class="nav-item"><a href="/user/profile/support" class="nav-link">Поддержка</a></li>
                <li class="nav-item"><a href="/user/profile/refunds" class="nav-link active">Возвраты</a></li>
            </ul>
            <div class="user__body">
                <#if refund??>
                    <form name="updateRefund">
                        <div class="edjll-input-style">
                            <div class="edjll-input-style__label">
                                <span class="edjll-input-style__label__text">Продажа</span>
                                <span class="edjll-input-style__label__input">${refund.saleProduct.id}</span>
                            </div>
                        </div>
                        <div class="edjll-input-style">
                            <div class="edjll-input-style__label">
                                <span class="edjll-input-style__label__text">Статус</span>
                                <span class="edjll-input-style__label__input">${refund.statusRefund.text}</span>
                            </div>
                        </div>
                        <div class="edjll-input-style">
                            <div class="edjll-input-style__label">
                                <span class="edjll-input-style__label__text">Дата</span>
                                <span class="edjll-input-style__label__input">${refund.date}</span>
                            </div>
                        </div>
                        <#if refund.statusRefund.ordinal() != 1>
                            <div class="edjll-textarea">
                                <div class="edjll-textarea__settings">
                                    <span class="edjll-textarea__settings__attributes">{"name": "reason", "label": "Причина", "defaultValue":"${refund.reason}"}</span>
                                    <span class="edjll-textarea__settings__validation">{"maxLength":300, "minLength":5}</span>
                                </div>
                            </div>
                            <div class="edjll-select">
                                <div class="edjll-select__settings">
                                    <span class="edjll-select__settings__attributes">{"name": "type", "inputValueFormat":{"type":"json", "pattern":["id", "name"]}, "outputValueFormat":{"type":"var", "pattern":["id"]}, "defaultValue": {"id":${refund.typeRefund.ordinal()}, "name":"${refund.typeRefund.text}"}, "options":[<#list statuses as status>{"id":${status.ordinal()}, "name":"${status.text}"}<#if status?has_next>,</#if></#list>], "itemVisibleValue":["name"]}</span>
                                    <span class="edjll-select__settings__request">{"pagination":false, "searchParam":false}</span>
                                </div>
                            </div>
                            <div class="edjll-input">
                                <div class="edjll-input__settings">
                                    <span class="edjll-input__settings__attributes">{"name": "id", "type": "hidden", "defaultValue": "${refund.id}"}</span>
                                </div>
                            </div>
                        <#else>
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
                        </#if>
                        <div class="edjll-input-style">
                            <div class="edjll-input-style__label">
                                <span class="edjll-input-style__label__text">Комментарий</span>
                                <span class="edjll-input-style__label__input"><#if refund.comment??>${refund.comment}</#if></span>
                            </div>
                        </div>
                        <#if refund.refundSaleProduct??>
                            <div class="edjll-input-style">
                                <div class="edjll-input-style__label">
                                    <span class="edjll-input-style__label__text">Новый продукт</span>
                                    <span class="edjll-input-style__label__input">#${refund.refundSaleProduct.id}</span>
                                </div>
                            </div>
                        </#if>
                        <#if refund.statusRefund.ordinal() != 1><button class="edjll-button">Сохранить</button></#if>
                    </form>
                </#if>
            </div>
        </div>
    </main>
</#assign>

<#assign scripts>
    <script src="/static/js/refund/userUpdate.js"></script>
    <@edjllForm.scripts></@edjllForm.scripts>
</#assign>

<@template.page
links = links
body = body
scripts = scripts
>
</@template.page>