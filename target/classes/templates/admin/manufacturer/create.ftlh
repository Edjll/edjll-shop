<#import "../macro/admin.ftlh" as admin>
<#import "../macro/template.ftlh" as template>
<#import "../../macro/edjllForm.ftlh" as edjllForm>

<#assign links>
    <link rel="stylesheet" href="/static/css/edjllForm/edjllForm.css">
</#assign>

<#assign body>
    <main>
        <form class="container edjll-form" name="createManufacturer">
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "name", "label": "Название"}</span>
                    <span class="edjll-input__settings__validation">{"required":true}</span>
                </div>
            </div>
            <div class="edjll-select">
                <div class="edjll-select__settings">
                    <span class="edjll-select__settings__attributes">{"name": "country","label": "Страна", "itemVisibleValue":["name"],"options": [<#list countries as country>{"id": ${country.id}, "name": "${country.name}"}<#if country?has_next>,</#if></#list>]}</span>
                    <span class="edjll-select__settings__validation">{"required":true}</span>
                </div>
            </div>
            <div class="edjll-textarea">
                <div class="edjll-textarea__settings">
                    <span class="edjll-textarea__settings__attributes">{"name": "description", "label": "Описание"}</span>
                    <span class="edjll-textarea__settings__validation">{"maxLength":300, "minLength":5}</span>
                </div>
            </div>
            <div class="edjll-file-input">
                <div class="edjll-file-input__settings">
                    <span class="edjll-file-input__settings__attributes">{"name": "logo", "label": "Логотип", "type": "file"}</span>
                    <span class="edjll-file-input__settings__validation">{"imageTypes":["image.png", "image.jpeg"], "required": true}</span>
                </div>
            </div>
            <button type="submit" class="edjll-button">Сохранить</button>
        </form>
    </main>
</#assign>

<#assign scripts>
    <script src="/static/js/manufacturer/create.js"></script>
    <@edjllForm.scripts></@edjllForm.scripts>
</#assign>

<@template.adminPage
bodyBody = body
links = links
scripts = scripts
page = "Создание производителя"
>
</@template.adminPage>