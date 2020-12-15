<#import "../../macro/admin.ftlh" as admin>
<#import "../../macro/template.ftlh" as template>
<#import "../../../macro/edjllForm.ftlh" as edjllForm>

<#assign links>
    <link rel="stylesheet" href="/static/css/edjllForm/edjllForm.css">
    <link rel="stylesheet" href="/static/css/table/tableInEdjllForm.css">
</#assign>

<#assign body>
    <main>
        <form class="container edjll-form" name="updateReview">
            <div class="edjll-spinner"></div>
            <div class="edjll-input-style">
                <div class="edjll-input-style__label">
                    <span class="edjll-input-style__label__text">Пользователь</span>
                    <span class="edjll-input-style__label__input">${review.user.email}</span>
                </div>
            </div>
            <div class="edjll-input-style">
                <div class="edjll-input-style__label">
                    <span class="edjll-input-style__label__text">Продукт</span>
                    <span class="edjll-input-style__label__input"><a href="/shop/product/${review.productData.id}" target="_blank">${review.productData.name}</a></span>
                </div>
            </div>
            <div class="edjll-input-style">
                <div class="edjll-input-style__label">
                    <span class="edjll-input-style__label__text">Дата</span>
                    <span class="edjll-input-style__label__input">${review.date}</span>
                </div>
            </div>
            <div class="edjll-input-style">
                <div class="edjll-input-style__label">
                    <span class="edjll-input-style__label__text">Плюсы</span>
                    <span class="edjll-input-style__label__input">${review.advantages}</span>
                </div>
            </div>
            <div class="edjll-input-style">
                <div class="edjll-input-style__label">
                    <span class="edjll-input-style__label__text">Минусы</span>
                    <span class="edjll-input-style__label__input">${review.disadvantages}</span>
                </div>
            </div>
            <div class="edjll-input-style">
                <div class="edjll-input-style__label">
                    <span class="edjll-input-style__label__text">Комментарий</span>
                    <span class="edjll-input-style__label__input">${review.comment}</span>
                </div>
            </div>
            <div class="edjll-select">
                <div class="edjll-select__settings">
                    <span class="edjll-select__settings__attributes">{"name": "statusReview", "inputValueFormat":{"type":"json", "pattern":["id", "name"]}, "outputValueFormat":{"type":"var", "pattern":["id"]}, "defaultValue": {"id":${review.statusReview.ordinal()}, "name":"${review.statusReview.text}"}, "options":[<#list statuses as status>{"id":${status.ordinal()}, "name":"${status.text}"}<#if status?has_next>,</#if></#list>], "itemVisibleValue":["name"]}</span>
                    <span class="edjll-select__settings__request">{"pagination":false, "searchParam":false}</span>
                </div>
            </div>
            <div class="edjll-input">
                <div class="edjll-input__settings">
                    <span class="edjll-input__settings__attributes">{"name": "id", "type": "hidden", "defaultValue": "${review.id}"}</span>
                </div>
            </div>
            <script src="/static/js/manufacturer/form.js"></script>
            <button type="submit" class="edjll-button">Сохранить</button>
        </form>
    </main>
</#assign>

<#assign scripts>
    <script src="/static/js/support/review/update.js"></script>
    <@edjllForm.scripts></@edjllForm.scripts>
</#assign>

<@template.adminPage
bodyBody = body
links = links
scripts=scripts
page = "Утверждение обзора"
>
</@template.adminPage>