<#import "../macro/admin.ftlh" as admin>
<#import "../macro/template.ftlh" as template>
<#import "../../macro/edjllForm.ftlh" as edjllForm>

<#assign links>
    <link rel="stylesheet" href="/static/css/edjllForm/edjllForm.css">
</#assign>

<#assign body>
    <main>
        <form class="container edjll-form" name="createMail">
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "title", "label": "Заголовок"}</span>
                    <span class="edjll-input__settings__validation">{"required":true}</span>
                </div>
            </div>
            <div class="edjll-textarea">
                <div class="edjll-textarea__settings">
                    <span class="edjll-textarea__settings__attributes">{"name": "body", "label": "Сообщение"}</span>
                    <span class="edjll-textarea__settings__validation">{"maxLength":300, "minLength":5}</span>
                </div>
            </div>
            <button type="submit" class="edjll-button">Отправить</button>
        </form>
    </main>
</#assign>

<#assign scripts>
    <script src="/static/js/mail/create.js"></script>
    <@edjllForm.scripts></@edjllForm.scripts>
</#assign>

<@template.adminPage
bodyBody = body
links = links
scripts = scripts
page = "Создание письма"
>
</@template.adminPage>