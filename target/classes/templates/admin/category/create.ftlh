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
        <form class="container edjll-form" name="createProductCategory">
            <div class="edjll-spinner"></div>
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "name", "label": "Название"}</span>
                    <span class="edjll-input__settings__validation">{"required":true}</span>
                </div>
            </div>
            <div class="edjll-textarea">
                <div class="edjll-textarea__settings">
                    <span class="edjll-textarea__settings__attributes">{"name": "description", "label": "Описание"}</span>
                    <span class="edjll-textarea__settings__validation">{"maxLength":300, "minLength":5}</span>
                </div>
            </div>
            <div class="edjll-graph">
                <div class="edjll-graph__settings">
                    <span class="edjll-graph__settings__attributes">{"name": "vertexes"}</span>
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
            </div>
            <button type="submit" class="edjll-button">Сохранить</button>
        </form>
    </main>
</#assign>

<#assign scripts>
    <script src="/static/js/productCategory/create.js"></script>
    <@edjllForm.scripts></@edjllForm.scripts>
    <script src="/static/js/productCategory/main.js"></script>
</#assign>

<@template.adminPage
links = links
bodyBody = body
scripts = scripts
page = "Создание категории продуктов"
></@template.adminPage>