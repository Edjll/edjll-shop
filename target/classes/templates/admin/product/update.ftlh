<#import "../macro/admin.ftlh" as admin>
<#import "../macro/template.ftlh" as template>
<#import "../../macro/edjllForm.ftlh" as edjllForm>

<#assign links>
    <link rel="stylesheet" href="/static/css/edjllForm/edjllForm.css">
    <link rel="stylesheet" href="/static/css/attribute/table.css">
    <link rel="stylesheet" href="/static/css/edjllObs/edjllObs.css">
</#assign>

<#assign body>
    <main>
        <form class="container edjll-form" name="updateProduct">
            <div class="edjll-spinner"></div>
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "name", "label": "Название", "defaultValue":"${productData.name}"}</span>
                    <span class="edjll-input__settings__validation">{"minLength":5, "maxLength":40, "required":true}</span>
                </div>
            </div>
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "cost", "label": "Стоимость", "type":"number", "defaultValue":"${productData.cost?c}"}</span>
                    <span class="edjll-input__settings__validation">{"min":0, "max":1000000, "required":true}</span>
                </div>
            </div>
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "shelfLife", "label": "Срок годности", "type":"number", "defaultValue":"${productData.shelfLife?c}"}</span>
                    <span class="edjll-input__settings__validation">{"min":0, "required":true}</span>
                </div>
            </div>
            <div class="edjll-textarea">
                <div class="edjll-textarea__settings">
                    <span class="edjll-textarea__settings__attributes">{"name": "description", "label": "Описание", "defaultValue":"${productData.description}"}</span>
                    <span class="edjll-textarea__settings__validation">{"maxLength":300, "minLength":5}</span>
                </div>
            </div>
            <div class="edjll-select">
                <div class="edjll-select__settings">
                    <span class="edjll-select__settings__attributes">{"name": "country", "label": "Страна", "options": [<#list countries as country>{"id": ${country.id}, "name": "${country.name}"}<#if country?has_next>,</#if></#list>], "defaultValue":{"id":${productData.country}, "name":"${country.name}"}}</span>
                    <span class="edjll-select__settings__validation">{"required":true}</span>
                </div>
            </div>
            <div class="edjll-select">
                <div class="edjll-select__settings">
                    <span class="edjll-select__settings__attributes">{"name": "manufacturer", "label": "Производитель", "options": [<#list manufacturers as manufacturer>{"id": ${manufacturer.id}, "name": "${manufacturer.name}"}<#if manufacturer?has_next>,</#if></#list>], "defaultValue":{"id":${productData.manufacturer}, "name":"${manufacturer.name}"}}</span>
                    <span class="edjll-select__settings__validation">{"required":true}</span>
                </div>
            </div>
            <div class="edjll-select">
                <div class="edjll-select__settings">
                    <span class="edjll-select__settings__attributes">{"name": "category", "label": "Категория", "defaultValue":{"id":${productData.category}, "name":"${productCategory.name}"}}</span>
                    <span class="edjll-select__settings__request">{"ajax":true, "url":"http://localhost:8080/category/children/null"}</span>
                    <span class="edjll-select__settings__validation">{"required":true}</span>
                </div>
            </div>
            <div class="edjll-file-input">
                <div class="edjll-file-input__settings">
                    <span class="edjll-file-input__settings__attributes">{"name": "image", "label": "Изображения", "type": "file", "accept":"image/png, image/jpeg", "multiple":true, "files": [<#list images as image>{"id": ${image.id}, "src": "/image/productDataImage/${image.filename}"}<#if image?has_next>,</#if></#list>]}</span>
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
                <#if productData.attributes??>
                    <div class="edjll-obs__body">
                        <#list productData.attributes?sort_by("category") as attribute>
                            <div class="edjll-form-group edjll-obs__body__row">
                                <#assign req = attributes?map(attr -> attr.name)?seq_contains(attribute.name)>
                                <div class="edjll-form-group__settings">
                                    <span class="edjll-form-group__settings__attributes">{"name": "wrapper"<#if !req>, "deleteNodeClassName":"edjll-form-group__delete"</#if>}</span>
                                </div>
                                <#if !req><div class="edjll-form-group__delete"></div></#if>
                                <div class="edjll-obs__body__row__item">
                                    <div class="edjll-select">
                                        <div class="edjll-select__settings">
                                            <span class="edjll-select__settings__attributes">{"name":"category", "inputValueFormat": { "type":"var", "pattern": ["name"] }, "itemVisibleValue":["name"], "outputValueFormat": { "type": "var", "pattern": ["name"] }, "defaultValue":"${attribute.category}"}</span>
                                            <span class="edjll-select__settings__request">{"ajax":true, "url":"http://localhost:8080/admin/attributeCategories/get"}</span>
                                            <span class="edjll-select__settings__validation">{"required":true}</span>
                                        </div>
                                    </div>
                                </div>
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
                                <div class="edjll-obs__body__row__item">
                                    <div class="edjll-select">
                                        <div class="edjll-select__settings">
                                            <span class="edjll-select__settings__attributes">{"name":"value", "visibleValue":"value", "inputValueFormat": { "type":"json", "pattern": ["value"] }, "itemVisibleValue":["value"], "outputValueFormat": { "type": "var", "pattern": ["value"] }, "defaultValue":{"value":"${attribute.value}"}}</span>
                                            <span class="edjll-select__settings__request">{"ajax":true, "url":"http://localhost:8080/admin/value/get/all"}</span>
                                            <span class="edjll-select__settings__validation">{"required":true}</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="edjll-obs__body__row__item">
                                    <div class="edjll-select">
                                        <div class="edjll-select__settings">
                                            <span class="edjll-select__settings__attributes">{"name":"unit", "visibleValue":"unit", "inputValueFormat": { "type":"json", "pattern": ["unit"] }, "itemVisibleValue":["unit"], "outputValueFormat": { "type": "var", "pattern": ["unit"] }, "defaultValue":{"unit":"${attribute.unit}"}}</span>
                                            <span class="edjll-select__settings__request">{"ajax":true, "url":"http://localhost:8080/admin/value/get/all"}</span>
                                            <span class="edjll-select__settings__validation">{"required":true}</span>
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
                    <span class="edjll-input__settings__attributes">{"name": "id", "type": "hidden", "defaultValue": "${productData.id}"}</span>
                </div>
            </div>
            <button class="edjll-button">Сохранить</button>
        </form>
    </main>
</#assign>

<#assign scripts>
    <script src="/static/js/product/update.js"></script>
    <@edjllForm.scripts></@edjllForm.scripts>
    <script src="/static/js/product/main.js"></script>
    <script>
        <#list productData.attributes as attribute>excludedValues.attribute.push('${attribute.name}');</#list>
        <#if attributes??>
            const edjllObs = edjllForms[0].searchFormItemByName('attributes');
            const attributeNames = [<#list attributes as attribute>"${attribute.name}"<#if attribute?has_next>,</#if></#list>];
            edjllObs.items.forEach(item => {
                if (attributeNames.includes(item.searchFormItemByName('name').getValue())) {
                    requiredAttributes.push(item);
                }
            })
        </#if>
    </script>
</#assign>

<@template.adminPage
links = links
bodyBody = body
scripts = scripts
page = "Изменение продукта"
>
</@template.adminPage>