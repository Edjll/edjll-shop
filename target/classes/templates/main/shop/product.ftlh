<#import "../macro/template.ftlh" as template>
<#import "../macro/slider.ftlh" as slider>
<#import "../macro/product.ftlh" as product>
<#import "../../macro/edjllForm.ftlh" as edjllForm>
<#import "../../macro/security.ftlh" as security>

<#assign links>
    <link rel="stylesheet" href="/static/css/table/table.css">
</#assign>

<#assign body>
    <!--== Start Page Header Area ==-->
    <div class="page-header-wrap bg-img" data-bg="/static/main/img/slider/slider-2.jpg">
        <div class="container">
            <div class="row">
                <div class="col-12 text-center">
                    <div class="page-header-content">
                        <div class="page-header-content-inner">
                            <h1>${productData.name}</h1>

                            <ul class="breadcrumb">
                                <li><a href="/">Главная</a></li>
                                <li><a href="/shop">Товары</a></li>
                                <li class="current"><a href="/shop/product/${productData.id}">${productData.name}</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--== End Page Header Area ==-->

    <!--== Start Page Content Wrapper ==-->
    <div class="page-content-wrapper sp-y">
        <div class="product-details-page-content">
            <div class="container container-wide">
                <div class="row">
                    <div class="col-12">
                        <div class="row">
                            <!-- Start Product Thumbnail Area -->
                            <div class="col-md-5">
                                <div class="thumb-area product-thumb-area">
                                    <div class="details-thumbnail product-details-thumbnail">
                                        <div class="product-thumbnail-slider" id="thumb-gallery">
                                            <#if productData.images??>
                                                <#list productData.images as image>
                                                    <figure class="pro-thumb-item"
                                                            data-mfp-src="/image/productDataImage/${image.filename}">
                                                        <img src="/image/productDataImage/${image.filename}"
                                                             alt="Product Details"/>
                                                    </figure>
                                                </#list>
                                            </#if>
                                        </div>

                                        <a href="#thumb-gallery" class="btn-large-view btn-gallery-popup">Увеличить<i
                                                    class="fa fa-search-plus"></i></a>
                                    </div>

                                    <div class="details-thumbnail-nav product-details-thumbnail-nav">
                                        <#if productData.images??>
                                            <#list productData.images as image>
                                                <figure class="pro-thumb-item"
                                                        data-mfp-src="/image/productDataImage/${image.filename}">
                                                    <img src="/image/productDataImage/${image.filename}"
                                                         alt="Product Details"/>
                                                </figure>
                                            </#list>
                                        </#if>
                                    </div>
                                </div>
                            </div>
                            <!-- End Product Thumbnail Area -->

                            <!-- Start Product Info Area -->
                            <div class="col-md-7">
                                <div class="product-details-info-content-wrap">
                                    <div class="prod-details-info-content">
                                        <h2>${productData.name}</h2>
                                        <h5 class="price">
                                            <strong>Цена:</strong>
                                            <span class="price-amount">
                                                <#if maxCount?? && 0 < maxCount>
                                                    <#if 0 < discount>
                                                        ${(100 - discount) * productData.cost / 100}₽
                                                        <span class="line-through pl-1">${productData.cost}₽</span>
                                                    <#else>
                                                        ${productData.cost}₽
                                                    </#if>
                                                <#else>
                                                    Нет в наличии
                                                </#if>
                                            </span>
                                        </h5>
                                        <p><#noautoesc>${productData.description}</#noautoesc></p>
                                        <p><strong>Производитель: </strong>${productData.manufacturer.name}</p>
                                        <p><strong>Произведено: </strong>${productData.country.name}</p>
                                        <p><strong>Срок годности: </strong>${productData.shelfLife}</p>
                                        <#if maxCount?? && 0 < maxCount>
                                            <div class="product-action">
                                                <div class="action-top d-sm-flex">
                                                    <div class="edjll-input">
                                                        <div class="edjll-input__settings">
                                                            <span class="edjll-input__settings__attributes">{"name": "count", "type": "number", "controllers":true, "defaultValue":<#if basket??>${basket.count}<#else>1</#if>}</span>
                                                            <span class="edjll-input__settings__validation">{"required":true, "min":1, "max":${maxCount}}</span>
                                                        </div>
                                                    </div>
                                                    <button class="btn btn-bordered add-button" onclick="<#if security.isAuthorized>addToDatabaseCart(${productData.id})<#else>addToLocalStorageCart(${productData.id})</#if>">
                                                        <#if basket??>
                                                            Изменить в корзине
                                                        <#else>
                                                            Добавить в корзину
                                                        </#if>
                                                    </button>
                                                </div>
                                            </div>
                                        <#else>
                                            <p><strong>Нет в наличии</strong></p>
                                        </#if>
                                    </div>
                                </div>
                            </div>
                            <!-- End Product Info Area -->
                        </div>

                        <div class="row">
                            <div class="col-12">
                                <div class="product-description-review">
                                    <!-- Product Description Tab Menu -->
                                    <ul class="nav nav-tabs desc-review-tab-menu" id="desc-review-tab" role="tablist">
                                        <li>
                                            <a class="active" id="desc-tab" data-toggle="tab" href="#descriptionContent"
                                               role="tab">Характеристики</a>
                                        </li>
                                        <li>
                                            <a id="profile-tab" data-toggle="tab" href="#reviewContent">Отзывы</a>
                                        </li>
                                    </ul>

                                    <!-- Product Description Tab Content -->
                                    <div class="tab-content" id="myTabContent">
                                        <div class="tab-pane fade show active" id="descriptionContent">
                                            <div class="description-content">
                                                <div class="table">
                                                    <#list attributeCategories as attributeCategory>
                                                        <div class="table__head">
                                                            <div class="table__head__item table__head__item">${attributeCategory.name}</div>
                                                        </div>
                                                        <div class="table__body">
                                                            <#list productData.attributes as attributeValue>
                                                                <#if attributeValue.attribute.category.id == attributeCategory.id>
                                                                    <div class="table__body__row">
                                                                        <div class="table__body__row__item table__body__row__item--id" title="${attributeValue.attribute.description}">
                                                                            ${attributeValue.attribute.name}
                                                                        </div>
                                                                        <div class="table__body__row__item">
                                                                            ${attributeValue.value.value} ${attributeValue.value.unit}
                                                                        </div>
                                                                    </div>
                                                                </#if>
                                                            </#list>
                                                        </div>
                                                    </#list>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="tab-pane fade" id="reviewContent">
                                            <div class="product-rating-wrap">
                                                <#if productData.reviews??>
                                                    <#list productData.reviews?sort_by("date") as review>
                                                        <#if review.statusReview.ordinal() == 1>
                                                            <div class="display-ratings">
                                                                <div class="rating-item">
                                                                    <div class="rating-author-txt w-100">
                                                                        <div class="rating-star">
                                                                            <#list 1..review.rating as r>
                                                                                <i class="ion-android-star"></i>
                                                                            </#list>
                                                                            <#if review.rating < 5>
                                                                                <#list review.rating + 1..5 as r>
                                                                                    <i class="ion-android-star-outline"></i>
                                                                                </#list>
                                                                            </#if>
                                                                        </div>

                                                                        <div class="rating-meta">
                                                                            <h3>${review.user.firstName + " " + review.user.lastName}</h3>
                                                                            <span class="time">${review.date}</span>
                                                                        </div>

                                                                        <p><strong>Плюсы:</strong> ${review.advantages}</p>
                                                                        <p><strong>Минусы:</strong> ${review.disadvantages}</p>
                                                                        <p><strong>Комментарий:</strong> ${review.comment}</p>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </#if>
                                                    </#list>
                                                </#if>

                                                <#if security.isAuthorized>
                                                    <#if review??>
                                                        <div class="rating-form-wrapper">
                                                            <h3>Измените свой отзыв</h3>
                                                            <form name="updateReview">
                                                                <div class="rating-form">
                                                                    <h5>Ваш рейтинг:</h5>
                                                                    <div class="rating-star review-rating fix mb-20">
                                                                        <#list 1..5 as star>
                                                                            <i class="fa fa-star<#if star == 6 - review.rating> current</#if>"></i>
                                                                        </#list>
                                                                    </div>
                                                                    <div class="edjll-input">
                                                                        <div class="edjll-input__settings">
                                                                            <span class="edjll-input__settings__attributes">{"name": "rating", "type": "hidden", "defaultValue": "${review.rating}"}</span>
                                                                        </div>
                                                                    </div>
                                                                    <div class="edjll-textarea">
                                                                        <div class="edjll-textarea__settings">
                                                                            <span class="edjll-textarea__settings__attributes">{"name": "advantages", "label": "Плюсы", "defaultValue":"${review.advantages}"}</span>
                                                                            <span class="edjll-textarea__settings__validation">{"maxLength":300, "minLength":5}</span>
                                                                        </div>
                                                                    </div>
                                                                    <div class="edjll-textarea">
                                                                        <div class="edjll-textarea__settings">
                                                                            <span class="edjll-textarea__settings__attributes">{"name": "disadvantages", "label": "Минусы", "defaultValue":"${review.disadvantages}"}</span>
                                                                            <span class="edjll-textarea__settings__validation">{"maxLength":300, "minLength":5}</span>
                                                                        </div>
                                                                    </div>
                                                                    <div class="edjll-textarea">
                                                                        <div class="edjll-textarea__settings">
                                                                            <span class="edjll-textarea__settings__attributes">{"name": "comment", "label": "Комментарий", "defaultValue":"${review.comment}"}</span>
                                                                            <span class="edjll-textarea__settings__validation">{"maxLength":300, "minLength":5}</span>
                                                                        </div>
                                                                    </div>
                                                                    <div class="edjll-input">
                                                                        <div class="edjll-input__settings">
                                                                            <span class="edjll-input__settings__attributes">{"name": "id", "type": "hidden", "defaultValue": "${review.id}"}</span>
                                                                        </div>
                                                                    </div>
                                                                    <button class="edjll-button">Сохранить</button>
                                                                </div>
                                                            </form>
                                                        </div>
                                                    <#else>
                                                        <div class="rating-form-wrapper">
                                                            <h3>Добавьте свой отзыв</h3>
                                                            <form action="#" method="post" name="addReview">
                                                                <div class="rating-form">
                                                                    <h5>Ваш рейтинг:</h5>
                                                                    <div class="rating-star review-rating fix mb-20">
                                                                        <#list 1..5 as star>
                                                                            <i class="fa fa-star"></i>
                                                                        </#list>
                                                                    </div>
                                                                    <div class="edjll-textarea">
                                                                        <div class="edjll-textarea__settings">
                                                                            <span class="edjll-textarea__settings__attributes">{"name": "advantages", "label": "Плюсы"}</span>
                                                                            <span class="edjll-textarea__settings__validation">{"maxLength":300, "minLength":5}</span>
                                                                        </div>
                                                                    </div>
                                                                    <div class="edjll-textarea">
                                                                        <div class="edjll-textarea__settings">
                                                                            <span class="edjll-textarea__settings__attributes">{"name": "disadvantages", "label": "Минусы"}</span>
                                                                            <span class="edjll-textarea__settings__validation">{"maxLength":300, "minLength":5}</span>
                                                                        </div>
                                                                    </div>
                                                                    <div class="edjll-textarea">
                                                                        <div class="edjll-textarea__settings">
                                                                            <span class="edjll-textarea__settings__attributes">{"name": "comment", "label": "Комментарий"}</span>
                                                                            <span class="edjll-textarea__settings__validation">{"maxLength":300, "minLength":5}</span>
                                                                        </div>
                                                                    </div>
                                                                    <div class="edjll-input">
                                                                        <div class="edjll-input__settings">
                                                                            <span class="edjll-input__settings__attributes">{"name": "productData", "type": "hidden", "defaultValue": "${productData.id}"}</span>
                                                                        </div>
                                                                    </div>
                                                                    <button class="edjll-button">Сохранить</button>
                                                                </div>
                                                            </form>
                                                        </div>
                                                    </#if>
                                                </#if>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--== End Page Content Wrapper ==-->
</#assign>

<#assign scripts>
    <#if review??>
        <script src="/static/js/shop/product/reviewUpdate.js"></script>
    <#else>
        <script src="/static/js/shop/product/main.js"></script>
    </#if>
    <@edjllForm.scripts></@edjllForm.scripts>
    <script src="/static/js/shop/product/countInput.js"></script>
    <script>const productId = ${productData.id};</script>
    <script src="/static/js/shop/product/addButton.js"></script>
    <script src="/static/js/shop/product/addToCart.js"></script>
    <script src="/static/js/shop/product/review.js"></script>
</#assign>

<@template.page
    links = links
body = body
scripts = scripts
>
</@template.page>