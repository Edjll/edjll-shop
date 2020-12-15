<#import "../macro/template.ftlh" as template>
<#import "../../macro/edjllForm.ftlh" as edjllForm>

<#assign title = "Регистрация">

<#assign links>
    <link rel="stylesheet" href="/static/css/edjllForm/edjllForm.css">
    <link rel="stylesheet" href="/static/css/main/registration/form.css">
</#assign>

<#assign body>
    <main>
        <form action="/user/registration" method="post" class="container edjll-form" name="login">
            <div class="edjll-spinner"></div>
            <div class="edjll-form__title">
                <h1>Вход</h1>
            </div>
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "email", "label": "Email"}</span>
                    <span class="edjll-input__settings__validation">{"required":true, "regex":{"pattern":".+(?<=@).+\\..+", "message": "email должен удовлетворять формату: *@*.*"}}</span>
                </div>
            </div>
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "password", "label": "Пароль", "type":"password"}</span>
                    <span class="edjll-input__settings__validation">{"required":true}</span>
                </div>
            </div>
            <button type="submit" class="edjll-button">Войти</button>
        </form>
    </main>
</#assign>

<#assign scripts>
    <script src="/static/js/user/login.js"></script>
    <@edjllForm.scripts></@edjllForm.scripts>
</#assign>

<@template.page
title = title
links = links
body = body
scripts = scripts
>
</@template.page>