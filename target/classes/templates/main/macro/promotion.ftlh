<#import "../../macro/security.ftlh" as security>

<#macro card
prom
>
    <div class="col-md-6 col-lg-4 col-xl-3">
        <div class="product-item">
            <div class="product-item__thumb">
                <a href="/promotions/${prom.id}/products">
                    <#if prom.banner??>
                        <img class="thumb-primary" src="/image/promotionBanner/${prom.banner.filename}" alt="Promotion" />
                    </#if>
                </a>
            </div>

            <div class="product-item__content pt-3">
                <div class="product-item__info">
                    <h4 class="title"><a href="/promotions/${prom.id}/products">${prom.name}</a></h4>
                    <span class="price">${prom.dateStart}<br>${prom.dateEnd}</span>
                </div>

                <div class="product-item__action">
                    <a class="btn-add-to-cart" href="/promotions/${prom.id}/products"><i class="ion-link"></i></a>
                </div>

                <div class="product-item__desc">
                    <p><#noautoesc>${prom.description}</#noautoesc></p>
                </div>
            </div>
        </div>
    </div>
</#macro>