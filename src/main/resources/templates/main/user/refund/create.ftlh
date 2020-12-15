<#import "../../macro/template.ftlh" as template>
<#import "../../../macro/security.ftlh" as security>
<#import "../../../macro/edjllForm.ftlh" as edjllForm>

<#assign title = "Профиль">

<#assign links>
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
                <form name="createRefund">
                    <div class="edjll-spinner"></div>
                    <div class="edjll-input-style">
                        <div class="edjll-input-style__label">
                            <span class="edjll-input-style__label__text">Продукт</span>
                            <span class="edjll-input-style__label__input"><a href="/shop/product/${saleProduct.product.productData.id}">${saleProduct.product.productData.name}</a></span>
                        </div>
                    </div>
                    <div class="edjll-textarea">
                        <div class="edjll-textarea__settings">
                            <span class="edjll-textarea__settings__attributes">{"name": "reason", "label": "Причина"}</span>
                            <span class="edjll-textarea__settings__validation">{"maxLength":300, "minLength":5}</span>
                        </div>
                    </div>
                    <div class="edjll-select">
                        <div class="edjll-select__settings">
                            <span class="edjll-select__settings__attributes">{"name": "type", "inputValueFormat":{"type":"json", "pattern":["id", "name"]}, "outputValueFormat":{"type":"var", "pattern":["id"]}, "defaultValue": {"id":${statuses?first.ordinal()}, "name":"${statuses?first.text}"}, "options":[<#list statuses as status>{"id":${status.ordinal()}, "name":"${status.text}"}<#if status?has_next>,</#if></#list>], "itemVisibleValue":["name"]}</span>
                            <span class="edjll-select__settings__request">{"pagination":false, "searchParam":false}</span>
                        </div>
                    </div>
                    <div class="edjll-input">
                        <div class="edjll-input__settings">
                            <span class="edjll-input__settings__attributes">{"name": "saleProduct", "type": "hidden", "defaultValue": "${saleProduct.id}"}</span>
                        </div>
                    </div>
                    <button type="submit" class="edjll-button">Запросить</button>
                </form>
            </div>
        </div>
    </main>
</#assign>

<#assign scripts>
    <script src="/static/js/refund/create.js"></script>
    <@edjllForm.scripts></@edjllForm.scripts>
</#assign>

<@template.page
title = title
links = links
body = body
scripts = scripts
>
</@template.page>