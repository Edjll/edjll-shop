<#--
<ul class="edjll-pagination"><li class="edjll-pagination__item edjll-pagination__item-disabled"><a href="#page=0" class="edjll-pagination__item__link">◁</a></li><li class="edjll-pagination__item edjll-pagination__item-active"><a href="#page=0" class="edjll-pagination__item__link">1</a></li><li class="edjll-pagination__item"><a href="#page=1" class="edjll-pagination__item__link">2</a></li><li class="edjll-pagination__item"><a href="#page=1" class="edjll-pagination__item__link">▷</a></li></ul>-->

<#macro template page url>
    <nav aria-label="...">
        <ul class="edjll-pagination">
            <li class="edjll-pagination__item <#if page.pageable.getPageNumber() == 0>edjll-pagination__item-disabled</#if>">
                <form action="${url}" method="get">
                    <input type="hidden" name="page" value="${page.pageable.getPageNumber() - 1}">
                    <button class="edjll-pagination__item__link" <#if page.pageable.getPageNumber() == 0>disabled</#if>>Предыдущая</button>
                </form>
            </li>
            <#assign lastPage = page.totalPages - 1>
            <#assign visiblePages = 7>
            <#assign currentPage = page.pageable.getPageNumber()>
            <#assign deltaLast = lastPage - currentPage>
            <#if lastPage <= visiblePages>
                <#list 0..lastPage as index>
                    <#if index < 0><#break></#if>
                    <li class="edjll-pagination__item <#if currentPage == index>edjll-pagination__item-active</#if>">
                        <form action="${url}" method="get">
                            <input type="hidden" name="page" value="${index}">
                            <button class="edjll-pagination__item__link" <#if currentPage == index>disabled</#if>>${index + 1}</button>
                        </form>
                    </li>
                </#list>
            <#else>
                <li class="edjll-pagination__item <#if currentPage == 0>edjll-pagination__item-active</#if>">
                    <form action="${url}" method="get">
                        <input type="hidden" name="page" value="0">
                        <button class="edjll-pagination__item__link" <#if currentPage == 0>disabled</#if>>1</button>
                    </form>
                </li>
                <#if currentPage < (visiblePages / 2)?floor>
                    <#list 1..currentPage + visiblePages - currentPage - 2 as index>
                        <li class="edjll-pagination__item <#if currentPage == index>edjll-pagination__item-active</#if>">
                            <form action="${url}" method="get">
                                <input type="hidden" name="page" value="${index}">
                                <button class="edjll-pagination__item__link" <#if currentPage == index>disabled</#if>>${index + 1}</button>
                            </form>
                        </li>
                    </#list>
                <#elseif deltaLast < (visiblePages / 2)?floor>
                    <#list currentPage - visiblePages + deltaLast + 2..lastPage - 1 as index>
                        <li class="edjll-pagination__item <#if currentPage == index>edjll-pagination__item-active</#if>">
                            <form action="${url}" method="get">
                                <input type="hidden" name="page" value="${index}">
                                <button class="edjll-pagination__item__link" <#if currentPage == index>disabled</#if>>${index + 1}</button>
                            </form>
                        </li>
                    </#list>
                <#else>
                    <#list currentPage - (visiblePages / 2)?floor + 1..currentPage + (visiblePages / 2)?floor - 1 as index>
                        <#if index < 1><#continue></#if>
                        <#if lastPage - 1 <= index><#break></#if>
                        <li class="edjll-pagination__item <#if currentPage == index>edjll-pagination__item-active</#if>">
                            <form action="${url}" method="get">
                                <input type="hidden" name="page" value="${index}">
                                <button class="edjll-pagination__item__link" <#if currentPage == index>disabled</#if>>${index + 1}</button>
                            </form>
                        </li>
                    </#list>
                </#if>
                <#if lastPage != 0>
                    <li class="edjll-pagination__item <#if currentPage == lastPage>edjll-pagination__item-active</#if>">
                        <form action="${url}" method="get">
                            <input type="hidden" name="page" value="${lastPage}">
                            <button class="edjll-pagination__item__link" <#if currentPage == lastPage>disabled</#if>>${lastPage + 1}</button>
                        </form>
                    </li>
                </#if>
            </#if>
            <li class="edjll-pagination__item <#if page.totalPages <= page.pageable.getPageNumber() + 1>edjll-pagination__item-disabled</#if>">
                <form action="${url}" method="get">
                    <input type="hidden" name="page" value="${page.pageable.getPageNumber() + 1}">
                    <button class="edjll-pagination__item__link" <#if page.totalPages <= page.pageable.getPageNumber() + 1>disabled</#if>>Следующая</button>
                </form>
            </li>
        </ul>
    </nav>
</#macro>