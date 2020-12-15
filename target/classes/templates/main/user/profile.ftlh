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
            <div class="user__body">
                <#if security.user??>
                    <form name="updateUser">
                        <div class="edjll-input">
                            <div class="edjll-input__settings">
                                <span class="edjll-input__settings__attributes">{"name": "email", "label": "Email", "defaultValue":"${security.user.email}"}</span>
                                <span class="edjll-input__settings__validation">{"required":true, "regex":{"pattern":".+(?<=@).+\\..+", "message": "email должен удовлетворять формату: *@*.*"}}</span>
                            </div>
                        </div>
                        <div class="edjll-input">
                            <div class="edjll-input__settings">
                                <span class="edjll-input__settings__attributes">{"name": "firstName", "label": "Имя", "defaultValue":"${security.user.firstName}"}</span>
                                <span class="edjll-input__settings__validation">{"required":true, "regex":{"pattern":"\\D", "message": "Имя должно содержать хотя бы одну букву"}}</span>
                            </div>
                        </div>
                        <div class="edjll-input">
                            <div class="edjll-input__settings">
                                <span class="edjll-input__settings__attributes">{"name": "lastName", "label": "Фамилия", "defaultValue":"${security.user.lastName}"}</span>
                                <span class="edjll-input__settings__validation">{"required":true, "regex":{"pattern":"\\D", "message": "Фамилия должна содержать хотя бы одну букву"}}</span>
                            </div>
                        </div>
                        <div class="edjll-input">
                            <div class="edjll-input__settings">
                                <span class="edjll-input__settings__attributes">{"name": "patronymic", "label": "Отчество", "defaultValue":"${security.user.patronymic}"}</span>
                                <span class="edjll-input__settings__validation">{"required":true}</span>
                            </div>
                        </div>
                        <div class="edjll-select">
                            <div class="edjll-select__settings">
                                <span class="edjll-select__settings__attributes">{"name": "city","label": "Город","options": [<#list cities as city>{"id": ${city.id}, "name": "${city.name}"}<#if city?has_next>,</#if></#list>], "defaultValue":{"id":${security.user.city.id}, "name":"${security.user.city.name}"}}</span>
                                <span class="edjll-select__settings__validation">{"required":true}</span>
                            </div>
                        </div>
                        <div class="edjll-input">
                            <div class="edjll-input__settings">
                                <span class="edjll-input__settings__attributes">{"name": "phone", "label": "Телефон", "defaultValue":"${security.user.phone}", "type":"number"}</span>
                                <span class="edjll-input__settings__validation">{"required":true, "max":99999999999}</span>
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
                                <span class="edjll-input__settings__attributes">{"name": "id", "type":"hidden", "defaultValue": ${security.user.id}}</span>
                            </div>
                        </div>
                        <div class="row m-0 justify-content-between">
                            <button class="edjll-button">Сохранить</button>
                            <a href="/user/profile/password" class="edjll-button">Сменить пароль</a>
                        </div>
                    </form>
                </#if>
            </div>
        </div>
    </main>
</#assign>

<#assign scripts>
    <script src="/static/js/user/update.js"></script>
    <@edjllForm.scripts></@edjllForm.scripts>
</#assign>

<@template.page
title = title
links = links
body = body
scripts = scripts
>
</@template.page>