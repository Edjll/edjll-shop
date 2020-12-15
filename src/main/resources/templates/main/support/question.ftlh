<#import "../macro/template.ftlh" as template>
<#import "../../macro/edjllForm.ftlh" as edjllForm>
<#import "../../macro/security.ftlh" as security>

<#assign title = "Вопрос поддержке">

<#assign links>
    <link rel="stylesheet" href="/static/css/edjllForm/edjllForm.css">
    <link rel="stylesheet" href="/static/css/main/registration/form.css">
</#assign>

<#assign body>
    <main>
        <form action="/user/registration" method="post" class="container edjll-form" name="createQuestion">
            <div class="edjll-spinner"></div>
            <div class="edjll-form__title">
                <h1>Поддержка</h1>
            </div>
            <#if !security.isAuthorized>
                <div class="edjll-input">
                    <div class="edjll-input__settings">
                        <span class="edjll-input__settings__attributes">{"name": "email", "label": "Email"}</span>
                        <span class="edjll-input__settings__validation">{"required":true}</span>
                    </div>
                </div>
            </#if>
            <div class="edjll-textarea">
                <div class="edjll-textarea__settings">
                    <span class="edjll-textarea__settings__attributes">{"name": "question", "label": "Вопрос"}</span>
                    <span class="edjll-textarea__settings__validation">{"required":true}</span>
                </div>
            </div>
            <button type="submit" class="edjll-button">Отправить</button>
        </form>
    </main>
</#assign>

<#assign scripts>
    <script src="/static/js/support/question/create.js"></script>
    <@edjllForm.scripts></@edjllForm.scripts>
</#assign>

<@template.page
title = title
links = links
body = body
scripts = scripts
>
</@template.page>