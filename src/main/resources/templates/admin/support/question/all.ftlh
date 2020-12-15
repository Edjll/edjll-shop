<#import "../../macro/admin.ftlh" as admin>
<#import "../../macro/template.ftlh" as template>
<#import "../../../macro/pagination.ftlh" as pagination>

<#assign links>
    <link rel="stylesheet" href="/static/css/edjllForm/edjllPagination.css">
    <link rel="stylesheet" href="/static/css/table/table.css">
</#assign>

<#assign body>
    <main>
        <div class="container table-wrapper">
            <div class="table">
                <div class="table__head">
                    <div class="table__head__item table__head__item--id">#</div>
                    <div class="table__head__item">Пользователь</div>
                    <div class="table__head__item">Вопрос</div>
                    <div class="table__head__item">Статус</div>
                    <div class="table__head__item">Действия</div>
                </div>
                <div class="table__body">
                    <#if questionsPage??>
                        <#list questionsPage.getContent() as question>
                            <div class="table__body__row">
                                <div class="table__body__row__item table__body__row__item--id">
                                    ${question.id}
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate">
                                    <#if question.email??>
                                        ${question.email}
                                    <#else>
                                        ${question.user.email}
                                    </#if>
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate">
                                    ${question.question}
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate">
                                    <#if question.answer??>
                                        <span class="status-success">Отвечено</span>
                                    <#else>
                                        <span class="status-error">Не отвечено</span>
                                    </#if>
                                </div>
                                <div class="table__body__row__item">
                                    <div class="dropdown">
                                        <button class="btn btn-sm btn-custom" type="button" id="dropdownMenuButton"
                                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                            <i class="ion-more"></i>
                                        </button>
                                        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenuButton">
                                            <a class="dropdown-item" href="/admin/question/update/${question.id}">Изменить</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </#list>
                    </#if>
                </div>
            </div>
            <@pagination.template
            page = questionsPage
            url = "/admin/question/all"
            ></@pagination.template>
        </div>
    </main>
</#assign>

<@template.adminPage
bodyBody = body
links=links
page = "Вопросы"
></@template.adminPage>