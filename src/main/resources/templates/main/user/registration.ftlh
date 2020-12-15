<#import "../macro/template.ftlh" as template>
<#import "../../macro/edjllForm.ftlh" as edjllForm>

<#assign title = "Регистрация">

<#assign links>
    <link rel="stylesheet" href="/static/css/edjllForm/edjllForm.css">
    <link rel="stylesheet" href="/static/css/main/registration/form.css">
</#assign>

<#assign body>
    <main>
        <form action="/user/registration" method="post" class="container edjll-form" name="registration">
            <div class="edjll-spinner"></div>
            <div class="edjll-form__title">
                <h1>Регистрация</h1>
            </div>
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "firstName", "label": "Имя"}</span>
                    <span class="edjll-input__settings__validation">{"required":true, "regex":{"pattern":"\\D", "message": "Имя должно содержать хотя бы одну букву"}}</span>
                </div>
            </div>
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "lastName", "label": "Фамилия"}</span>
                    <span class="edjll-input__settings__validation">{"required":true, "regex":{"pattern":"\\D", "message": "Фамилия должна содержать хотя бы одну букву"}}</span>
                </div>
            </div>
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "patronymic", "label": "Отчество"}</span>
                </div>
            </div>
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "email", "label": "Email"}</span>
                    <span class="edjll-input__settings__validation">{"required":true, "regex":{"pattern":".+(?<=@).+\\..+", "message": "email должен удовлетворять формату: *@*.*"}}</span>
                </div>
            </div>
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "phone", "label": "Телефон", "type":"number"}</span>
                    <span class="edjll-input__settings__validation">{"required":true, "max":99999999999}</span>
                </div>
            </div>
            <div class="edjll-select">
                <div class="edjll-select__settings">
                    <span class="edjll-select__settings__attributes">{"name": "city","label": "Город","itemVisibleValue":["name"],"options": [<#list cities as city>{"id": ${city.id}, "name": "${city.name}"}<#if city?has_next>,</#if></#list>]}</span>
                    <span class="edjll-select__settings__validation">{"required":true}</span>
                </div>
            </div>
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "password", "label": "Пароль", "type":"password"}</span>
                    <span class="edjll-input__settings__validation">{"required":true}</span>
                </div>
            </div>
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "repeatPassword", "label": "Повторите пароль", "type":"password"}</span>
                    <span class="edjll-input__settings__validation">{"required":true, "identity":"password"}</span>
                </div>
            </div>
            <button type="submit" class="edjll-button">Зарегистрироваться</button>
        </form>
    </main>
</#assign>

<#assign scripts>
    <script src="/static/js/user/registration.js"></script>
    <@edjllForm.scripts></@edjllForm.scripts>
</#assign>

<@template.page
    title = title
    links = links
    body = body
    scripts = scripts
>
</@template.page>