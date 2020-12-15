<#assign known = Session.SPRING_SECURITY_CONTEXT??>

<#if known>
    <#assign
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    isAdmin = user.getAuthorities()?seq_contains('ADMIN')
    isSupport = user.getAuthorities()?seq_contains('SUPPORT')
    isAuthorized = true
    >
<#else>
    <#assign
    name = ""
    isAdmin = false
    isSupport = false
    isAuthorized = false
    >
</#if>