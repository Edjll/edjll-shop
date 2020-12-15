<#import "../macro/admin.ftlh" as admin>
<#import "../macro/template.ftlh" as template>
<#import "../../macro/edjllForm.ftlh" as edjllForm>

<#assign links>
    <link rel="stylesheet" href="/static/css/edjllForm/edjllForm.css">
    <link rel="stylesheet" href="/static/css/edjllObs/edjllObs.css">
    <link rel="stylesheet" href="/static/css/table/tableInEdjllForm.css">
</#assign>

<#assign body>
    <main>
        <form class="container edjll-form" name="createProduct">
            <div class="edjll-spinner"></div>
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "name", "label": "Название"}</span>
                    <span class="edjll-input__settings__validation">{"minLength":5, "maxLength":40, "required":true}</span>
                </div>
            </div>
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "cost", "label": "Стоимость", "type":"number"}</span>
                    <span class="edjll-input__settings__validation">{"min":0, "max":1000000, "required":true}</span>
                </div>
            </div>
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "shelfLife", "label": "Срок годности", "type":"number"}</span>
                    <span class="edjll-input__settings__validation">{"min":0, "required":true}</span>
                </div>
            </div>
            <div class="edjll-textarea">
                <div class="edjll-textarea__settings">
                    <span class="edjll-textarea__settings__attributes">{"name": "description", "label": "Описание"}</span>
                    <span class="edjll-textarea__settings__validation">{"maxLength":300, "minLength":5}</span>
                </div>
            </div>
            <div class="edjll-select">
                <div class="edjll-select__settings">
                    <span class="edjll-select__settings__attributes">{"name": "country", "label": "Страна", "itemVisibleValue":["name"], "options": [<#list countries as country>{"id": ${country.id}, "name": "${country.name}"}<#if country?has_next>,</#if></#list>]}</span>
                    <span class="edjll-select__settings__validation">{"required":true}</span>
                </div>
            </div>
            <div class="edjll-select">
                <div class="edjll-select__settings">
                    <span class="edjll-select__settings__attributes">{"name": "manufacturer", "itemVisibleValue":["name"], "label": "Производитель", "options": [<#list manufacturers as manufacturer>{"id": ${manufacturer.id}, "name": "${manufacturer.name}"}<#if manufacturer?has_next>,</#if></#list>]}</span>
                    <span class="edjll-select__settings__validation">{"required":true}</span>
                </div>
            </div>
            <div class="edjll-select">
                <div class="edjll-select__settings">
                    <span class="edjll-select__settings__attributes">{"name": "category", "label": "Категория", "itemVisibleValue":["name"]}</span>
                    <span class="edjll-select__settings__request">{"ajax":true, "url":"http://localhost:8080/category/children/null"}</span>
                    <span class="edjll-select__settings__validation">{"required":true}</span>
                </div>
            </div>
            <div class="edjll-file-input">
                <div class="edjll-file-input__settings">
                    <span class="edjll-file-input__settings__attributes">{"name": "image", "label": "Изображения", "type": "file", "accept":"image/png, image/jpeg", "multiple":true}</span>
                    <span class="edjll-file-input__settings__validation">{"required": true}</span>
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
                                                "name":"category",
                                                "label":"Категория",
                                                "inputValueFormat": {
                                                    "type":"var",
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
                                                "url":"http://localhost:8080/admin/attributeCategories/get"
                                            },
                                            "validation": {
                                                "required": true
                                            }
                                        }
                                    }, {
                                        "type": "edjllSelect",
                                        "settings": {
                                            "attributes": {
                                                "name":"name",
                                                "label":"Характеристика",
                                                "inputValueFormat": {
                                                    "type":"json",
                                                    "pattern": ["name"]
                                                },
                                                "itemVisibleValue": ["name"],
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
                                    }, {
                                        "type": "edjllSelect",
                                        "settings": {
                                            "attributes": {
                                                "name":"value",
                                                "label":"Значение",
                                                "visibleValue":"value",
                                                "inputValueFormat": {
                                                    "type":"json",
                                                    "pattern": ["value"]
                                                },
                                                "itemVisibleValue":["value"],
                                                "outputValueFormat": {
                                                    "type": "var",
                                                    "pattern": ["value"]
                                                }
                                            },
                                            "request": {
                                                "ajax":true,
                                                "url":"http://localhost:8080/admin/value/get/all"
                                            },
                                            "validation": {
                                                "required": true
                                            }
                                        }
                                    }, {
                                        "type":"edjllSelect",
                                        "settings": {
                                            "attributes": {
                                                "name":"unit",
                                                "label":"Единицы измерения",
                                                "visibleValue":"unit",
                                                "inputValueFormat": {
                                                    "type":"json",
                                                    "pattern": ["unit"]
                                                },
                                                "itemVisibleValue":["unit"],
                                                "outputValueFormat": {
                                                    "type": "var",
                                                    "pattern": ["unit"]
                                                }
                                            },
                                            "request": {
                                                "ajax":true,
                                                "url":"http://localhost:8080/admin/value/get/all"
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
                    <div class="edjll-obs__head__item">Категория</div>
                    <div class="edjll-obs__head__item">Характеристика</div>
                    <div class="edjll-obs__head__item">Описание</div>
                    <div class="edjll-obs__head__item">Значение</div>
                    <div class="edjll-obs__head__item">Единицы измерения</div>
                </div>
            </div>
            <button class="edjll-button">Сохранить</button>
        </form>
    </main>
</#assign>

<#assign scripts>
    <script src="/static/js/product/create.js"></script>
    <@edjllForm.scripts></@edjllForm.scripts>
    <script src="/static/js/product/main.js"></script>
</#assign>

<@template.adminPage
links = links
bodyBody = body
scripts = scripts
page = "Создание продукта"
>
</@template.adminPage>