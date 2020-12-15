<#import "../../macro/security.ftlh" as security>
<#import "template.ftlh" as template>
<#import "../../macro/logout.ftlh" as logout>

<#macro pageControls page>
    <div class="admin-page-controls">
        <div class="admin-page-controls__inner">
            <div class="admin-page-controls__buttons">
                <div class="admin-page-controls__buttons__control-menu">
                    <div></div>
                    <div></div>
                    <div></div>
                </div>
            </div>
            <#if page != "">
                <div class="admin-page-controls__location">
                    <span class="admin-page-controls__location__text">${page}</span>
                </div>
            </#if>
            <div class="admin-page-controls__user">
                <div class="dropdown-toggle user-menu" id="user" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <i class="ion-ios-person-outline user-menu__icon"></i>
                </div>
                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="user">
                    <a class="dropdown-item" href="/user/profile">Мой профиль</a>
                    <a class="dropdown-item" href="/">Магазин</a>
                    <@logout.template></@logout.template>
                </div>
            </div>
        </div>
    </div>
</#macro>

<#macro panel page = "">
    <div class="admin-panel">
        <a href="/admin" class="admin-panel__logo">
            <span>edjll</span>
        </a>
        <div class="admin-panel__menu admin-menu">
            <#if security.isAdmin>
                <a href="/admin/statistics/all" class="admin-menu__item">
                    <div class="admin-menu__item__title">
                        <div class="admin-menu__item__title__img">
                            <img src="/static/image/icons/statistics.png" alt="statistics">
                        </div>
                        <div class="admin-menu__item__title__name">
                            <span>Статистика</span>
                        </div>
                    </div>
                </a>
            </#if>
            <div class="admin-menu__item">
                <div class="admin-menu__item__title">
                    <div class="admin-menu__item__title__img">
                        <img src="/static/image/icons/sales.png" alt="sales">
                    </div>
                    <div class="admin-menu__item__title__name">
                        <span>Продажи</span>
                    </div>
                    <div class="admin-menu__item__title__expend"></div>
                </div>
                <div class="admin-menu__item__list">
                    <ul class="admin-menu__item__list__inner">
                        <#if security.isAdmin>
                            <li class="admin-menu__item__list__item">
                                <a href="/admin/sale/all" class="admin-menu__item__list__item__link">
                                    <span>Все продажи</span>
                                </a>
                            </li>
                        </#if>
                        <li class="admin-menu__item__list__item">
                            <a href="/admin/refund/all" class="admin-menu__item__list__item__link">
                                <span>Все возвраты</span>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
            <#if security.isAdmin>
                <div class="admin-menu__item">
                    <div class="admin-menu__item__title">
                        <div class="admin-menu__item__title__img">
                            <img src="/static/image/icons/products.png" alt="products">
                        </div>
                        <div class="admin-menu__item__title__name">
                            <span>Товары</span>
                        </div>
                        <div class="admin-menu__item__title__expend"></div>
                    </div>
                    <div class="admin-menu__item__list">
                        <ul class="admin-menu__item__list__inner">
                            <li class="admin-menu__item__list__item">
                                <a href="/admin/product/all" class="admin-menu__item__list__item__link">
                                    <span>Все товары</span>
                                </a>
                            </li>
                            <li class="admin-menu__item__list__item">
                                <a href="/admin/product/create" class="admin-menu__item__list__item__link">
                                    <span>Добавить</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </#if>
            <#if security.isAdmin>
                <div class="admin-menu__item">
                    <div class="admin-menu__item__title">
                        <div class="admin-menu__item__title__img">
                            <img src="/static/image/icons/promotions.png" alt="promotions">
                        </div>
                        <div class="admin-menu__item__title__name">
                            <span>Акции</span>
                        </div>
                        <div class="admin-menu__item__title__expend"></div>
                    </div>
                    <div class="admin-menu__item__list">
                        <ul class="admin-menu__item__list__inner">
                            <li class="admin-menu__item__list__item">
                                <a href="/admin/promotion/all" class="admin-menu__item__list__item__link">
                                    <span>Все акции</span>
                                </a>
                            </li>
                            <li class="admin-menu__item__list__item">
                                <a href="/admin/promotion/create" class="admin-menu__item__list__item__link">
                                    <span>Добавить</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </#if>
            <#if security.isAdmin>
                <div class="admin-menu__item">
                    <div class="admin-menu__item__title">
                        <div class="admin-menu__item__title__img">
                            <img src="/static/image/icons/storages.png" alt="storages">
                        </div>
                        <div class="admin-menu__item__title__name">
                            <span>Склады</span>
                        </div>
                        <div class="admin-menu__item__title__expend"></div>
                    </div>
                    <div class="admin-menu__item__list">
                        <ul class="admin-menu__item__list__inner">
                            <li class="admin-menu__item__list__item">
                                <a href="/admin/storage/all" class="admin-menu__item__list__item__link">
                                    <span>Все склады</span>
                                </a>
                            </li>
                            <li class="admin-menu__item__list__item">
                                <a href="/admin/storage/create" class="admin-menu__item__list__item__link">
                                    <span>Добавить</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </#if>
            <#if security.isAdmin>
                <div class="admin-menu__item">
                    <div class="admin-menu__item__title">
                        <div class="admin-menu__item__title__img">
                            <img src="/static/image/icons/shipments.png" alt="shipments">
                        </div>
                        <div class="admin-menu__item__title__name">
                            <span>Поставки</span>
                        </div>
                        <div class="admin-menu__item__title__expend"></div>
                    </div>
                    <div class="admin-menu__item__list">
                        <ul class="admin-menu__item__list__inner">
                            <li class="admin-menu__item__list__item">
                                <a href="/admin/supply/all" class="admin-menu__item__list__item__link">
                                    <span>Все поставки</span>
                                </a>
                            </li>
                            <li class="admin-menu__item__list__item">
                                <a href="/admin/supply/create" class="admin-menu__item__list__item__link">
                                    <span>Добавить</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </#if>
            <#if security.isAdmin>
                <div class="admin-menu__item">
                    <div class="admin-menu__item__title">
                        <div class="admin-menu__item__title__img">
                            <img src="/static/image/icons/categories.png" alt="categories">
                        </div>
                        <div class="admin-menu__item__title__name">
                            <span>Категории</span>
                        </div>
                        <div class="admin-menu__item__title__expend"></div>
                    </div>
                    <div class="admin-menu__item__list">
                        <ul class="admin-menu__item__list__inner">
                            <li class="admin-menu__item__list__item">
                                <a href="/admin/category/all" class="admin-menu__item__list__item__link">
                                    <span>Все категории</span>
                                </a>
                            </li>
                            <li class="admin-menu__item__list__item">
                                <a href="/admin/category/create" class="admin-menu__item__list__item__link">
                                    <span>Добавить</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </#if>
            <#if security.isAdmin>
                <div class="admin-menu__item">
                    <div class="admin-menu__item__title">
                        <div class="admin-menu__item__title__img">
                            <img src="/static/image/icons/manufacturers.png" alt="manufacturers">
                        </div>
                        <div class="admin-menu__item__title__name">
                            <span>Производители</span>
                        </div>
                        <div class="admin-menu__item__title__expend"></div>
                    </div>
                    <div class="admin-menu__item__list">
                        <ul class="admin-menu__item__list__inner">
                            <li class="admin-menu__item__list__item">
                                <a href="/admin/manufacturer/all" class="admin-menu__item__list__item__link">
                                    <span>Все производители</span>
                                </a>
                            </li>
                            <li class="admin-menu__item__list__item">
                                <a href="/admin/manufacturer/create" class="admin-menu__item__list__item__link">
                                    <span>Добавить</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </#if>
            <#if security.isAdmin>
                <div class="admin-menu__item">
                    <div class="admin-menu__item__title">
                        <div class="admin-menu__item__title__img">
                            <img src="/static/image/icons/countries.png" alt="countries">
                        </div>
                        <div class="admin-menu__item__title__name">
                            <span>Страны</span>
                        </div>
                        <div class="admin-menu__item__title__expend"></div>
                    </div>
                    <div class="admin-menu__item__list">
                        <ul class="admin-menu__item__list__inner">
                            <li class="admin-menu__item__list__item">
                                <a href="/admin/country/all" class="admin-menu__item__list__item__link">
                                    <span>Все страны</span>
                                </a>
                            </li>
                            <li class="admin-menu__item__list__item">
                                <a href="/admin/country/create" class="admin-menu__item__list__item__link">
                                    <span>Добавить</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </#if>
            <#if security.isAdmin>
                <div class="admin-menu__item">
                    <div class="admin-menu__item__title">
                        <div class="admin-menu__item__title__img">
                            <img src="/static/image/icons/cities.png" alt="cities">
                        </div>
                        <div class="admin-menu__item__title__name">
                            <span>Города</span>
                        </div>
                        <div class="admin-menu__item__title__expend"></div>
                    </div>
                    <div class="admin-menu__item__list">
                        <ul class="admin-menu__item__list__inner">
                            <li class="admin-menu__item__list__item">
                                <a href="/admin/city/all" class="admin-menu__item__list__item__link">
                                    <span>Все города</span>
                                </a>
                            </li>
                            <li class="admin-menu__item__list__item">
                                <a href="/admin/city/create" class="admin-menu__item__list__item__link">
                                    <span>Добавить</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </#if>
            <a href="/admin/user/all" class="admin-menu__item">
                <div class="admin-menu__item__title">
                    <div class="admin-menu__item__title__img">
                        <img src="/static/image/icons/users.png" alt="users">
                    </div>
                    <div class="admin-menu__item__title__name">
                        <span>Пользователи</span>
                    </div>
                </div>
            </a>
            <a href="/admin/mail/create" class="admin-menu__item">
                <div class="admin-menu__item__title">
                    <div class="admin-menu__item__title__img">
                        <img src="/static/image/icons/mailing.png" alt="mailing">
                    </div>
                    <div class="admin-menu__item__title__name">
                        <span>Рассылка</span>
                    </div>
                </div>
            </a>
            <#if security.isAdmin>
                <div class="admin-menu__item">
                    <div class="admin-menu__item__title">
                        <div class="admin-menu__item__title__img">
                            <img src="/static/image/icons/employees.png" alt="employees">
                        </div>
                        <div class="admin-menu__item__title__name">
                            <span>Сотрудники</span>
                        </div>
                        <div class="admin-menu__item__title__expend"></div>
                    </div>
                    <div class="admin-menu__item__list">
                        <ul class="admin-menu__item__list__inner">
                            <li class="admin-menu__item__list__item">
                                <a href="/admin/employee/all" class="admin-menu__item__list__item__link">
                                    <span>Все сотрудники</span>
                                </a>
                            </li>
                            <li class="admin-menu__item__list__item">
                                <a href="/admin/employee/add" class="admin-menu__item__list__item__link">
                                    <span>Добавить</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </#if>
            <div class="admin-menu__item">
                <div class="admin-menu__item__title">
                    <div class="admin-menu__item__title__img">
                        <img src="/static/image/icons/feedback.png" alt="feedback">
                    </div>
                    <div class="admin-menu__item__title__name">
                        <span>Обратная связь</span>
                    </div>
                    <div class="admin-menu__item__title__expend"></div>
                </div>
                <div class="admin-menu__item__list">
                    <ul class="admin-menu__item__list__inner">
                        <li class="admin-menu__item__list__item">
                            <a href="/admin/review/all" class="admin-menu__item__list__item__link">
                                <span>Обзоры</span>
                            </a>
                        </li>
                        <li class="admin-menu__item__list__item">
                            <a href="/admin/question/all" class="admin-menu__item__list__item__link">
                                <span>Вопросы</span>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <@pageControls page = page></@pageControls>
    <script src="/static/js/admin.js"></script>
</#macro>
