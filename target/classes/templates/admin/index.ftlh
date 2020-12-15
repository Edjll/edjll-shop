<#import "macro/admin.ftlh" as admin>
<#import "macro/template.ftlh" as template>
<#import "../macro/security.ftlh" as security>

<#assign links>
    <link rel="stylesheet" href="/static/css/admin/index.css">
</#assign>

<#assign body>
    <main>
        <#if security.isAdmin>
            <div class="card">
                <div class="card__header">
                    <img class="card__header__icon" src="/static/image/icons/statistics.png" alt="statistics">
                    <span class="card__header__text">Статистика</span>
                </div>
                <div class="card__body">
                    <a href="/admin/statistics/all" class="card__body__item">Посмотреть</a>
                </div>
            </div>
        </#if>
        <div class="card">
            <div class="card__header">
                <img class="card__header__icon" src="/static/image/icons/sales.png" alt="sales">
                <span class="card__header__text">Продажи</span>
            </div>
            <div class="card__body">
                <#if security.isAdmin><a href="/admin/sale/all" class="card__body__item">Все продажи</a></#if>
                <a href="/admin/refund/all" class="card__body__item">Все возвраты</a>
            </div>
        </div>
        <#if security.isAdmin>
            <div class="card">
                <div class="card__header">
                    <img class="card__header__icon" src="/static/image/icons/products.png" alt="products">
                    <span class="card__header__text">Товары</span>
                </div>
                <div class="card__body">
                    <a href="/admin/product/all" class="card__body__item">Все товары</a>
                    <a href="/admin/product/create" class="card__body__item">Добавить</a>
                </div>
            </div>
        </#if>
        <#if security.isAdmin>
            <div class="card">
                <div class="card__header">
                    <img class="card__header__icon" src="/static/image/icons/promotions.png" alt="promotions">
                    <span class="card__header__text">Акции</span>
                </div>
                <div class="card__body">
                    <a href="/admin/promotion/all" class="card__body__item">Все акции</a>
                    <a href="/admin/promotion/create" class="card__body__item">Добавить</a>
                </div>
            </div>
        </#if>
        <#if security.isAdmin>
            <div class="card">
                <div class="card__header">
                    <img class="card__header__icon" src="/static/image/icons/storages.png" alt="storages">
                    <span class="card__header__text">Склады</span>
                </div>
                <div class="card__body">
                    <a href="/admin/storage/all" class="card__body__item">Все склады</a>
                    <a href="/admin/storage/create" class="card__body__item">Добавить</a>
                </div>
            </div>
        </#if>
        <#if security.isAdmin>
            <div class="card">
                <div class="card__header">
                    <img class="card__header__icon" src="/static/image/icons/shipments.png" alt="shipments">
                    <span class="card__header__text">Поставки</span>
                </div>
                <div class="card__body">
                    <a href="/admin/supply/all" class="card__body__item">Все поставки</a>
                    <a href="/admin/supply/create" class="card__body__item">Добавить</a>
                </div>
            </div>
        </#if>
        <#if security.isAdmin>
            <div class="card">
                <div class="card__header">
                    <img class="card__header__icon" src="/static/image/icons/categories.png" alt="categories">
                    <span class="card__header__text">Категории</span>
                </div>
                <div class="card__body">
                    <a href="/admin/category/all" class="card__body__item">Все категории</a>
                    <a href="/admin/category/create" class="card__body__item">Добавить</a>
                </div>
            </div>
        </#if>
        <#if security.isAdmin>
            <div class="card">
                <div class="card__header">
                    <img class="card__header__icon" src="/static/image/icons/manufacturers.png" alt="manufacturers">
                    <span class="card__header__text">Производители</span>
                </div>
                <div class="card__body">
                    <a href="/admin/manufacturer/all" class="card__body__item">Все производители</a>
                    <a href="/admin/manufacturer/create" class="card__body__item">Добавить</a>
                </div>
            </div>
        </#if>
        <#if security.isAdmin>
            <div class="card">
                <div class="card__header">
                    <img class="card__header__icon" src="/static/image/icons/countries.png" alt="countries">
                    <span class="card__header__text">Страны</span>
                </div>
                <div class="card__body">
                    <a href="/admin/country/all" class="card__body__item">Все страны</a>
                    <a href="/admin/country/create" class="card__body__item">Добавить</a>
                </div>
            </div>
        </#if>
        <#if security.isAdmin>
            <div class="card">
                <div class="card__header">
                    <img class="card__header__icon" src="/static/image/icons/cities.png" alt="cities">
                    <span class="card__header__text">Города</span>
                </div>
                <div class="card__body">
                    <a href="/admin/city/all" class="card__body__item">Все города</a>
                    <a href="/admin/city/create" class="card__body__item">Добавить</a>
                </div>
            </div>
        </#if>
        <div class="card">
            <div class="card__header">
                <img class="card__header__icon" src="/static/image/icons/users.png" alt="users">
                <span class="card__header__text">Пользователи</span>
            </div>
            <div class="card__body">
                <a href="/admin/user/all" class="card__body__item">Все пользователи</a>
            </div>
        </div>
        <div class="card">
            <div class="card__header">
                <img class="card__header__icon" src="/static/image/icons/mailing.png" alt="mailing">
                <span class="card__header__text">Рассылка</span>
            </div>
            <div class="card__body">
                <a href="/admin/mail/create" class="card__body__item">Создать письмо</a>
            </div>
        </div>
        <#if security.isAdmin>
            <div class="card">
                <div class="card__header">
                    <img class="card__header__icon" src="/static/image/icons/employees.png" alt="employees">
                    <span class="card__header__text">Сотрудники</span>
                </div>
                <div class="card__body">
                    <a href="/admin/employee/all" class="card__body__item">Все сотрудники</a>
                    <a href="/admin/employee/add" class="card__body__item">Добавить</a>
                </div>
            </div>
        </#if>
        <div class="card">
            <div class="card__header">
                <img class="card__header__icon" src="/static/image/icons/feedback.png" alt="feedback">
                <span class="card__header__text">Обратная связь</span>
            </div>
            <div class="card__body">
                <a href="/admin/question/all" class="card__body__item">Вопросы</a>
                <a href="/admin/review/all" class="card__body__item">Обзоры</a>
            </div>
        </div>
    </main>
</#assign>


<@template.adminPage
    bodyBody = body
    links = links
>
</@template.adminPage>