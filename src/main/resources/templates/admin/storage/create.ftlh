<#import "../macro/admin.ftlh" as admin>
<#import "../macro/template.ftlh" as template>
<#import "../../macro/edjllForm.ftlh" as edjllForm>

<#assign links>
    <link rel="stylesheet" href="/static/css/edjllForm/edjllForm.css">
</#assign>

<#assign body>
    <main>
        <form class="container edjll-form" name="createStorage">
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "name", "label": "Название"}</span>
                    <span class="edjll-input__settings__validation">{"required":true}</span>
                </div>
            </div>
            <div class="edjll-select">
                <div class="edjll-select__settings">
                    <span class="edjll-select__settings__attributes">{"name": "city","label": "Город", "itemVisibleValue":["name"],"options": [<#list cities as city>{"id": ${city.id}, "name": "${city.name}"}<#if city?has_next>,</#if></#list>]}</span>
                    <span class="edjll-select__settings__validation">{"required":true}</span>
                </div>
            </div>
            <div class="edjll-textarea">
                <div class="edjll-textarea__settings">
                    <span class="edjll-textarea__settings__attributes">{"name": "description", "label": "Описание"}</span>
                    <span class="edjll-textarea__settings__validation">{"maxLength":300, "minLength":5}</span>
                </div>
            </div>
            <button type="submit" class="edjll-button">Сохранить</button>
        </form>
    </main>
</#assign>

<#assign scripts>
    <script src="/static/js/storage/create.js"></script>
    <@edjllForm.scripts></@edjllForm.scripts>
</#assign>

<@template.adminPage
bodyBody = body
links = links
scripts = scripts
page = "Создание склада"
>
</@template.adminPage>