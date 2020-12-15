<#import "../macro/admin.ftlh" as admin>
<#import "../macro/template.ftlh" as template>
<#import "../../macro/edjllForm.ftlh" as edjllForm>

<#assign links>
    <link rel="stylesheet" href="/static/css/edjllForm/edjllForm.css">
</#assign>

<#assign body>
    <main>
        <form class="container edjll-form" name="updateManufacturer">
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "name", "label": "Название", "defaultValue":"${manufacturer.name}"}</span>
                    <span class="edjll-input__settings__validation">{"required":true}</span>
                </div>
            </div>
            <div class="edjll-select">
                <div class="edjll-select__settings">
                    <span class="edjll-select__settings__attributes">{"name": "country","label": "Страна", "itemVisibleValue":["name"],"options": [<#list countries as country>{"id": ${country.id}, "name": "${country.name}"}<#if country?has_next>,</#if></#list>], "defaultValue":{"id":${manufacturer.country.id}, "name":"${manufacturer.country.name}"}}</span>
                    <span class="edjll-select__settings__validation">{"required":true}</span>
                </div>
            </div>
            <div class="edjll-textarea">
                <div class="edjll-textarea__settings">
                    <span class="edjll-textarea__settings__attributes">{"name": "description", "label": "Описание", "defaultValue":"${manufacturer.description}"}</span>
                    <span class="edjll-textarea__settings__validation">{"maxLength":300, "minLength":5}</span>
                </div>
            </div>
            <div class="edjll-file-input">
                <div class="edjll-file-input__settings">
                    <span class="edjll-file-input__settings__attributes">{"name": "logo", "label": "Логотип", "type": "file", "files": [{"id":${manufacturer.logo.id}, "src": "/image/manufacturerLogo/${manufacturer.logo.filename}"}]}</span>
                    <span class="edjll-file-input__settings__validation">{"imageTypes":["image.png", "image.jpeg"], "required": true}</span>
                </div>
            </div>
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "id", "type": "hidden", "defaultValue": "${manufacturer.id}"}</span>
                </div>
            </div>
            <button type="submit" class="edjll-button">Сохранить</button>
        </form>
    </main>
</#assign>

<#assign scripts>
    <script src="/static/js/manufacturer/update.js"></script>
    <@edjllForm.scripts></@edjllForm.scripts>
</#assign>

<@template.adminPage
bodyBody = body
links = links
scripts = scripts
page = "Изменение производеля"
>
</@template.adminPage>