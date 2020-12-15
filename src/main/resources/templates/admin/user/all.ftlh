<#import "../macro/admin.ftlh" as admin>
<#import "../macro/template.ftlh" as template>
<#import "../../macro/pagination.ftlh" as pagination>

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
                    <div class="table__head__item">Email</div>
                    <div class="table__head__item">Имя</div>
                    <div class="table__head__item">Фамилия</div>
                    <div class="table__head__item">Отчество</div>
                    <div class="table__head__item">Город</div>
                    <div class="table__head__item">Телефон</div>
                </div>
                <div class="table__body">
                    <#if usersPage??>
                        <#list usersPage.getContent() as user>
                            <div class="table__body__row">
                                <div class="table__body__row__item table__body__row__item--id">
                                    ${user.id}
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate" title="${user.email}">
                                    ${user.email}
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate" title="${user.firstName}">
                                    ${user.firstName}
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate" title="${user.lastName}">
                                    ${user.lastName}
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate" title="${user.patronymic}">
                                    ${user.patronymic}
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate" title="${user.city.name}">
                                    ${user.city.name}
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate" title="${user.phone}">
                                    ${user.phone}
                                </div>
                            </div>
                        </#list>
                    </#if>
                </div>
            </div>
            <@pagination.template
            page = usersPage
            url = "/admin/user/all"
            ></@pagination.template>
        </div>
    </main>
</#assign>

<@template.adminPage
bodyBody = body
links=links
page = "Пользователи"
></@template.adminPage>