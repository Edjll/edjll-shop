<#import "../macro/admin.ftlh" as admin>
<#import "../macro/template.ftlh" as template>
<#import "../../macro/edjllForm.ftlh" as edjllForm>
<#import "../../macro/edjllGraph.ftlh" as graph>

<#assign links>
    <link rel="stylesheet" href="/static/css/edjllGraph/edjllGraph.css">
    <link rel="stylesheet" href="/static/css/edjllForm/edjllForm.css">
    <link rel="stylesheet" href="/static/css/edjllObs/edjllObs.css">
</#assign>

<#assign body>
    <main>
        <form class="container edjll-form" name="updateProductCategory">
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "name", "label": "Название", "defaultValue":"${category.name}"}</span>
                    <span class="edjll-input__settings__validation">{"required":true}</span>
                </div>
            </div>
            <div class="edjll-textarea">
                <div class="edjll-textarea__settings">
                    <span class="edjll-textarea__settings__attributes">{"name": "description", "label": "Описание", "defaultValue": "${category.description}"}</span>
                    <span class="edjll-textarea__settings__validation">{"maxLength":300, "minLength":5}</span>
                </div>
            </div>
            <div class="edjll-graph">
                <div class="edjll-graph__settings">
                    <span class="edjll-graph__settings__attributes">{"name": "vertexes", "id":${category.id}}</span>
                    <span class="edjll-graph__settings__request">{"url": "http://localhost:8080/category/children"}</span>
                </div>
            </div>
            <div class="edjll-obs">
                <div class="edjll-obs__settings">
                    <span class="edjll-obs__settings__attributes">
                        {
                            "name":"attributes",
                            "patterns": {
                                "createItem":[
                                    {
                                        "type": "edjllSelect",
                                        "settings": {
                                            "attributes": {
                                                "name":"name",
                                                "label":"Характеристика",
                                                "inputValueFormat": {
                                                    "type":"json",
                                                    "pattern": ["name"]
                                                },
                                                "itemVisibleValue":["name"],
                                                "outputValueFormat": {
                                                    "type": "var",
                                                    "pattern": ["name"]
                                                }
                                            },
                                            "request": {
                                                "ajax":true,
                                                "url":"http://localhost:8080/admin/attribute/get/all",
                                                "tracking":"category",
                                                "excludedValues":"attribute",
                                                "urlAfterSelected":"http://localhost:8080/admin/attribute/get/info"
                                            },
                                            "validation": {
                                                "required": true
                                            }
                                        }
                                    }, {
                                        "type": "edjllInput",
                                        "settings": {
                                            "attributes": {
                                                "name":"description",
                                                "label":"Описание",
                                                "inputValueFormat": {
                                                    "type":"var",
                                                    "pattern": ["name"]
                                                },
                                                "outputValueFormat": {
                                                    "type": "var",
                                                    "pattern": ["name"]
                                                }
                                            },
                                            "validation": {
                                                "required": true
                                            }
                                        }
                                    }
                                ]
                            }
                        }
                    </span>
                </div>
                <div class="edjll-obs__head">
                    <div class="edjll-obs__head__item">Характеристика</div>
                    <div class="edjll-obs__head__item">Описание</div>
                </div>
                <#if category.attributes??>
                    <div class="edjll-obs__body">
                        <#list category.attributes?sort_by("name") as attribute>
                            <div class="edjll-form-group edjll-obs__body__row">
                                <#assign req = attributes?map(attr -> attr.name)?seq_contains(attribute.name)>
                                <div class="edjll-form-group__settings">
                                    <span class="edjll-form-group__settings__attributes">{"name": "wrapper"<#if !req>, "deleteNodeClassName":"edjll-form-group__delete"</#if>}</span>
                                </div>
                                <#if !req><div class="edjll-form-group__delete"></div></#if>
                                <div class="edjll-obs__body__row__item">
                                    <div class="edjll-select">
                                        <div class="edjll-select__settings">
                                            <span class="edjll-select__settings__attributes">{"name":"name", "inputValueFormat": { "type":"json", "pattern": ["name"] }, "itemVisibleValue": ["name"], "outputValueFormat": { "type": "var", "pattern": ["name"] }, "defaultValue":{"name":"${attribute.name}"}}</span>
                                            <span class="edjll-select__settings__request">{"ajax":true,"url":"http://localhost:8080/admin/attribute/get/all","tracking":"category","excludedValues":"attribute","urlAfterSelected":"http://localhost:8080/admin/attribute/get/info"}</span>
                                            <span class="edjll-select__settings__validation">{"required":true}</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="edjll-obs__body__row__item">
                                    <div class="edjll-input">
                                        <div class="edjll-input__settings">
                                            <span class="edjll-input__settings__attributes">{"name": "description", "defaultValue":"${attribute.description}", "inputValueFormat": { "type":"var", "pattern": ["name"] }, "outputValueFormat": { "type": "var", "pattern": ["name"] }}</span>
                                            <span class="edjll-input__settings__validation">{"required":true}</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </#list>
                    </div>
                </#if>
            </div>
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "id", "type": "hidden", "defaultValue": "${category.id}"}</span>
                </div>
            </div>
            <button type="submit" class="edjll-button">Сохранить</button>
        </form>
    </main>
</#assign>

<#assign scripts>
    <script src="/static/js/productCategory/update.js"></script>
    <@edjllForm.scripts></@edjllForm.scripts>
    <script src="/static/js/productCategory/main.js"></script>
</#assign>

<@template.adminPage
links = links
bodyBody = body
scripts = scripts
page = "Изменение категории продуктов"
></@template.adminPage>