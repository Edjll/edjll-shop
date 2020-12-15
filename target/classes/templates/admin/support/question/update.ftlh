<#import "../../macro/admin.ftlh" as admin>
<#import "../../macro/template.ftlh" as template>
<#import "../../../macro/edjllForm.ftlh" as edjllForm>

<#assign links>
    <link rel="stylesheet" href="/static/css/edjllForm/edjllForm.css">
    <link rel="stylesheet" href="/static/css/table/tableInEdjllForm.css">
</#assign>

<#assign body>
    <main>
        <form class="container edjll-form" name="updateQuestion">
            <div class="edjll-spinner"></div>
            <div class="edjll-input-style">
                <div class="edjll-input-style__label">
                    <span class="edjll-input-style__label__text">Пользователь</span>
                    <span class="edjll-input-style__label__input">${(question.email??)?then(question.email, question.user.email)}</span>
                </div>
            </div>
            <div class="edjll-input-style">
                <div class="edjll-input-style__label">
                    <span class="edjll-input-style__label__text">Вопрос</span>
                    <span class="edjll-input-style__label__input">${question.question}</span>
                </div>
            </div>
            <div class="edjll-textarea">
                <div class="edjll-textarea__settings">
                    <span class="edjll-textarea__settings__attributes">{"name": "answer", "label": "Ответ"<#if question.answer??>, "defaultValue": "${question.answer}"</#if>}</span>
                    <span class="edjll-textarea__settings__validation">{"minLength":5, "maxLength":40, "required":true}</span>
                </div>
            </div>
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "id", "type": "hidden", "defaultValue": "${question.id}"}</span>
                </div>
            </div>
            <script src="/static/js/manufacturer/form.js"></script>
            <button type="submit" class="edjll-button">Сохранить</button>
        </form>
    </main>
</#assign>

<#assign scripts>
    <script src="/static/js/support/question/update.js"></script>
    <@edjllForm.scripts></@edjllForm.scripts>
</#assign>

<@template.adminPage
bodyBody = body
links = links
scripts=scripts
page = "Ответ на вопрос"
>
</@template.adminPage>