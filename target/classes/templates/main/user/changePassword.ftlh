<#import "../macro/template.ftlh" as template>
<#import "../../macro/edjllForm.ftlh" as edjllForm>
<#import "../../macro/security.ftlh" as security>

<#assign title = "Профиль">

<#assign links>
    <link rel="stylesheet" href="/static/css/user/profile.css">
</#assign>

<#assign body>
    <main>
        <div class="container user my-5">
            <ul id="myTab" class="nav nav-tabs user__header">
                <li class="nav-item"><a href="/user/profile" class="nav-link active">Информация</a></li>
                <li class="nav-item"><a href="/user/profile/sales" class="nav-link">Покупки</a></li>
                <li class="nav-item"><a href="/user/profile/reviews" class="nav-link">Отзывы</a></li>
                <li class="nav-item"><a href="/user/profile/support" class="nav-link">Поддержка</a></li>
                <li class="nav-item"><a href="/user/profile/refunds" class="nav-link">Возвраты</a></li>
            </ul>
            <div class="table-wrapper user__body">
                <#if security.user??>
                    <form name="changePassword">
                        <div class="edjll-input">
                            <div class="edjll-input__settings">
                                <span class="edjll-input__settings__attributes">{"name": "password", "label":"Старый пароль", "type":"password"}</span>
                            </div>
                        </div>
                        <div class="edjll-input">
                            <div class="edjll-input__settings">
                                <span class="edjll-input__settings__attributes">{"name": "newPassword", "label":"Новый пароль", "type":"password"}</span>
                            </div>
                        </div>
                        <div class="edjll-input">
                            <div class="edjll-input__settings">
                                <span class="edjll-input__settings__attributes">{"name": "id", "type":"hidden", "defaultValue":"${security.user.id}"}</span>
                            </div>
                        </div>
                        <button class="edjll-button">Сохранить</button>
                    </form>
                </#if>
            </div>
        </div>
    </main>
</#assign>

<#assign scripts>
    <script src="/static/js/user/changePassword.js"></script>
    <@edjllForm.scripts></@edjllForm.scripts>
</#assign>

<@template.page
title = title
links = links
body = body
scripts = scripts
>
</@template.page>