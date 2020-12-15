<#import "../macro/template.ftlh" as template>
<#import "../../macro/security.ftlh" as security>

<#assign title>
    <#if question??>${question.id}</#if>
</#assign>

<#assign links>
    <link rel="stylesheet" href="/static/css/edjllForm/edjllForm.css">
    <link rel="stylesheet" href="/static/css/user/profile.css">
</#assign>

<#assign body>
    <main>
        <div class="container user my-5">
            <ul id="myTab" class="nav nav-tabs user__header">
                <li class="nav-item"><a href="/user/profile" class="nav-link">Информация</a></li>
                <li class="nav-item"><a href="/user/profile/sales" class="nav-link">Покупки</a></li>
                <li class="nav-item"><a href="/user/profile/reviews" class="nav-link">Отзывы</a></li>
                <li class="nav-item"><a href="/user/profile/support" class="nav-link active">Поддержка</a></li>
                <li class="nav-item"><a href="/user/profile/refunds" class="nav-link">Возвраты</a></li>
            </ul>
            <div class="user__body">
                <#if question??>
                    <div class="edjll-input-style">
                        <div class="edjll-input-style__label">
                            <span class="edjll-input-style__label__text">Вопрос</span>
                            <span class="edjll-input-style__label__input">${question.question}</span>
                        </div>
                    </div>
                    <div class="edjll-input-style">
                        <div class="edjll-input-style__label">
                            <span class="edjll-input-style__label__text">Дата вопроса</span>
                            <span class="edjll-input-style__label__input">${question.questionDate}</span>
                        </div>
                    </div>
                    <div class="edjll-input-style">
                        <div class="edjll-input-style__label">
                            <span class="edjll-input-style__label__text">Ответ</span>
                            <span class="edjll-input-style__label__input">${(question.answer??)?then(question.answer, "")}</span>
                        </div>
                    </div>
                    <div class="edjll-input-style">
                        <div class="edjll-input-style__label">
                            <span class="edjll-input-style__label__text">Дата ответа</span>
                            <span class="edjll-input-style__label__input"><#if question.answerDate??>${question.answerDate}</#if></span>
                        </div>
                    </div>
                    <div class="edjll-input-style">
                        <div class="edjll-input-style__label">
                            <span class="edjll-input-style__label__text">Сотрудник</span>
                            <span class="edjll-input-style__label__input">${(question.employee??)?then(question.employee.user.firstName, "")}</span>
                        </div>
                    </div>
                </#if>
            </div>
        </div>
    </main>
</#assign>

<@template.page
title = title
links = links
body = body
>
</@template.page>