<#import "../macro/admin.ftlh" as admin>
<#import "../macro/template.ftlh" as template>
<#import "../../macro/pagination.ftlh" as pagination>
<#import "../../macro/edjllForm.ftlh" as edjllForm>
<#import "../../macro/security.ftlh" as security>

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
                    <div class="table__head__item">Паспорт</div>
                    <div class="table__head__item">Договор</div>
                    <div class="table__head__item">Стутус</div>
                    <div class="table__head__item">Действия</div>
                </div>
                <div class="table__body">
                    <#if employeesPage??>
                        <#list employeesPage.getContent() as employee>
                            <div class="table__body__row">
                                <div class="table__body__row__item table__body__row__item--id">
                                    ${employee.id}
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate" title="${employee.user.email}">
                                    ${employee.user.email}
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate" title="${employee.user.firstName}">
                                    ${employee.user.firstName}
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate" title="${employee.user.lastName}">
                                    ${employee.user.lastName}
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate" title="${employee.user.patronymic}">
                                    ${employee.user.patronymic}
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate" title="${employee.user.city.name}">
                                    ${employee.user.city.name}
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate" title="${employee.user.phone}">
                                    ${employee.user.phone}
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate" title="${employee.passport}">
                                    ${employee.passport}
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate" title="${employee.employmentContract}">
                                    ${employee.employmentContract}
                                </div>
                                <div class="table__body__row__item table__body__row__item--truncate" title="${employee.dismissed?then("Уволен", "Работает")}" class>
                                    <#if employee.dismissed>
                                        <span class="status-error">Уволен</span>
                                    <#else>
                                        <span class="status-success">Работает</span>
                                    </#if>
                                </div>
                                <div class="table__body__row__item">
                                    <#if security.user.id != employee.user.id>
                                        <div class="dropdown">
                                            <button class="btn btn-sm btn-custom" type="button" id="dropdownMenuButton"
                                                    data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                <i class="ion-more"></i>
                                            </button>
                                            <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenuButton">
                                                <a class="dropdown-item" href="/admin/employee/update/${employee.id}">Изменить</a>
                                                <#if !employee.dismissed>
                                                    <form class="dropdown-item" name="dismissEmployee">
                                                        <div class="edjll-input">
                                                            <div class="edjll-input__settings">
                                                                <span class="edjll-input__settings__attributes">{"name": "id", "type": "hidden", "defaultValue": "${employee.id}"}</span>
                                                            </div>
                                                        </div>
                                                        <button>Уволить</button>
                                                    </form>
                                                </#if>
                                            </div>
                                        </div>
                                    </#if>
                                </div>
                            </div>
                        </#list>
                    </#if>
                </div>
            </div>
            <@pagination.template
            page = employeesPage
            url = "/admin/employee/all"
            ></@pagination.template>
        </div>
    </main>
</#assign>

<#assign scripts>
    <script src="/static/js/employee/delete.js"></script>
    <@edjllForm.scripts></@edjllForm.scripts>
</#assign>

<@template.adminPage
bodyBody = body
links=links
page = "Сотрудники"
scripts = scripts
></@template.adminPage>