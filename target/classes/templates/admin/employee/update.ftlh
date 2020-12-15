<#import "../macro/admin.ftlh" as admin>
<#import "../macro/template.ftlh" as template>
<#import "../../macro/edjllForm.ftlh" as edjllForm>

<#assign links>
    <link rel="stylesheet" href="/static/css/edjllForm/edjllForm.css">
    <link rel="stylesheet" href="/static/css/table/tableInEdjllForm.css">
</#assign>

<#assign body>
    <main>
        <form class="container edjll-form" name="updateEmployee">
            <div class="edjll-spinner"></div>
            <div class="edjll-input-style">
                <div class="edjll-input-style__label">
                    <span class="edjll-input-style__label__text">Пользователь</span>
                    <span class="edjll-input-style__label__input">${employee.getUser().getEmail()}</span>
                </div>
            </div>
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "passport", "label": "Паспорт", "defaultValue":"${employee.passport}"}</span>
                    <span class="edjll-input__settings__validation">{"minLength":10, "maxLength":11, "required":true}</span>
                </div>
            </div>
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "employmentContract", "label": "Договор", "defaultValue":"${employee.employmentContract}"}</span>
                    <span class="edjll-input__settings__validation">{"required":true}</span>
                </div>
            </div>
            <div class="edjll-select">
                <div class="edjll-select__settings">
                    <span class="edjll-select__settings__attributes">{"name": "role", "itemVisibleValue":["name"], "label": "Роль", "options": [<#list roles as role>{"id": ${role.ordinal() + 1}, "name": "${role.name()}"}<#if role?has_next>,</#if></#list>]<#if !employee.dismissed>, "defaultValue":{"id":${employee.getUser().getRoles()?last.ordinal() + 1}, "name":"${employee.getUser().getRoles()?last.name()}"}</#if>}</span>
                    <span class="edjll-select__settings__validation">{"required":true}</span>
                </div>
            </div>
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "id", "type": "hidden", "defaultValue": "${employee.id}"}</span>
                </div>
            </div>
            <button type="submit" class="edjll-button">Сохранить</button>
        </form>
    </main>
</#assign>

<#assign scripts>
    <script src="/static/js/employee/update.js"></script>
    <@edjllForm.scripts></@edjllForm.scripts>
</#assign>

<@template.adminPage
bodyBody = body
links = links
scripts=scripts
page = "Изменение сотрудника"
>
</@template.adminPage>