<#import "../../macro/jquery.ftlh" as jquery>
<#import "admin.ftlh" as admin>
<#import "../../macro/toast.ftlh" as toast>

<#macro header body>
    <header>
        <a href="#" class="header-logo"></a>
    </header>
    ${body}
</#macro>

<#macro body body>
    ${body}
</#macro>

<#macro footer body>
    <footer>

    </footer>
    ${body}
</#macro>

<#macro adminPage
links = ""
headerBody = ""
bodyBody = ""
footerBody = ""
scripts = ""
    page = ""
>
    <!doctype html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <meta name="_csrf" content="${_csrf.token}">
        <meta name="_csrf_header" content="${_csrf.headerName}">
        <link rel="shortcut icon" href="/static/main/img/favicon.ico" type="image/x-icon" />
        <@jquery.css></@jquery.css>
        <link href="/static/main/css/ionicons.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="/static/css/admin/admin.css">
        <link rel="stylesheet" href="/static/css/user/menu.css">
        <@toast.links></@toast.links>
        ${links}
        <title>Shop</title>
    </head>
    <body>
    <@toast.scripts></@toast.scripts>
    <@admin.panel page = page></@admin.panel>
    <@body body = bodyBody></@body>
    <@jquery.js></@jquery.js>
    <script src="/static/js/admin/adminMenu.js"></script>
    <script src="/static/js/admin/main.js"></script>
    ${scripts}
    </body>
    </html>
</#macro>