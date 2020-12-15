<#import "../../macro/security.ftlh" as security>

<#macro card
    prod,
    baskets = []
>
    <div class="col-md-6 col-lg-4 col-xl-3">
        <div class="product-item product-item-${prod.id}">
            <div class="product-item__thumb">
                <a href="/shop/product/${prod.id}">
                    <#if prod.image??>
                        <img class="thumb-primary" src="/image/productDataImage/${prod.image}" alt="Product" />
                    </#if>
                </a>
                <div class="ratting">
                    <#if 0 < prod.rating>
                        <#list 1..prod.rating?floor as r>
                            <span><i class="ion-android-star"></i></span>
                        </#list>
                    </#if>
                    <#if prod.rating?floor != prod.rating?round>
                        <span><i class="ion-android-star-half"></i></span>
                    </#if>
                    <#if prod.rating?round < 5>
                        <#list prod.rating?round + 1..5 as r>
                            <span><i class="ion-android-star-outline"></i></span>
                        </#list>
                    </#if>
                </div>
            </div>

            <div class="product-item__content">
                <div class="product-item__info">
                    <h4 class="title"><a href="/shop/product/${prod.id}">${prod.name}</a></h4>
                    <span class="price"><strong>Цена:</strong><#if prod.maxCount?? && 0 < prod.maxCount> ${prod.discountPrice}₽<#if prod.discount?? && 0 < prod.discount><span class="line-through pl-1">${prod.price}₽</span></#if><#else> Нет в наличии</#if></span>
                </div>

                <div class="product-item__action">
                    <#if security.isAuthorized && baskets?? && baskets?map(basket -> basket.productData.id)?seq_contains(prod.id)>
                        <a href="/shop/cart" class="btn-add-to-cart"><i class="ion-android-checkmark-circle"></i></a>
                    <#else>
                        <#if prod.maxCount?? && 0 < prod.maxCount>
                            <div class="btn-add-to-cart" onclick="<#if security.isAuthorized>addToDatabaseCart(${prod.id})<#else>addToLocalStorageCart(${prod.id})</#if>">
                                <i class="ion-bag"></i>
                            </div>
                        </#if>
                    </#if>
                    <a class="btn-add-to-cart" href="/shop/product/${prod.id}"><i class="ion-link"></i></a>
                </div>

                <div class="product-item__desc">
                    <p><#noautoesc>${prod.description}</#noautoesc></p>
                </div>
            </div>

            <#if prod.discount?? && 0 < prod.discount>
                <div class="product-item__sale">
                    <span class="sale-txt">${prod.discount}%</span>
                </div>
            </#if>
        </div>
    </div>
</#macro>