<#import "../macro/template.ftlh" as template>
<#import "../../macro/security.ftlh" as security>
<#import "../../macro/pagination.ftlh" as pagination>

<#assign title = "Профиль">

<#assign links>
    <link rel="stylesheet" href="/static/css/edjllForm/edjllPagination.css">
    <link rel="stylesheet" href="/static/css/table/table.css">
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
            <div class="user__body p-0">
                    <div class="table">
                        <div class="table__head">
                            <div class="table__head__item table__head__item--id">#</div>
                            <div class="table__head__item">Дата вопроса</div>
                            <div class="table__head__item">Дата ответа</div>
                            <div class="table__head__item">Вопрос</div>
                            <div class="table__head__item">Ответ</div>
                            <div class="table__head__item">Ссылка</div>
                        </div>
                        <div class="table__body">
                            <#if 0 < questionsPage.totalPages>
                                <#list questionsPage.getContent() as question>
                                    <div class="table__body__row">
                                        <div class="table__body__row__item table__body__row__item--id">
                                            ${question.id}
                                        </div>
                                        <div class="table__body__row__item table__body__row__item--truncate">
                                            ${question.questionDate}
                                        </div>
                                        <div class="table__body__row__item table__body__row__item--truncate">
                                            ${(question.answerDate??)?then(question.answerDate, "")}
                                        </div>
                                        <div class="table__body__row__item table__body__row__item--truncate">
                                            ${question.question}
                                        </div>
                                        <div class="table__body__row__item table__body__row__item--truncate">
                                            ${(question.answer??)?then(question.answer, "")}
                                        </div>
                                        <div class="table__body__row__item">
                                            <a class="edjll-button-style" href="/user/profile/support/question/${question.id}">Подробнее</a>
                                        </div>
                                    </div>
                                </#list>
                            <#else>
                                <div class="table__body__row justify-content-center">Пусто</div>
                            </#if>
                        </div>
                    </div>
                <@pagination.template
                    page = questionsPage
                    url = "/user/profile/support"
                ></@pagination.template>
            </div>
        </div>
    </main>
</#assign>

<@template.page
title = title
links = links
body = body
scripts = scripts
>
</@template.page>