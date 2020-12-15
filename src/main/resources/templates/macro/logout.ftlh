<#macro template>
    <form action="/logout" method="post">
        <button class="dropdown-item">Выйти</button>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
    </form>
</#macro>