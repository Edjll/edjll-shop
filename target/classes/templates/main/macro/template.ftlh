<#import "header.ftlh" as header>
<#import "footer.ftlh" as footer>
<#import "../../macro/toast.ftlh" as toast>
<#import "../../macro/security.ftlh" as security>

<#macro page
    title = "edjll"
    links = ""
    body = ""
    scripts = ""
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

        <title>edjll</title>

        <!--== Favicon ==-->
        <link rel="shortcut icon" href="/static/main/img/favicon.ico" type="image/x-icon" />

        <!--== Google Fonts ==-->
        <link href="https://fonts.googleapis.com/css?family=Oswald:400,500,600,700%7CPoppins:400,400i,500,600&display=swap" rel="stylesheet">

        <!-- build:css /static/main/css/app.min.css -->
        <!--== Leaflet Min CSS ==-->
        <link href="/static/main/css/leaflet.min.css" rel="stylesheet" />
        <!--== Nice Select Min CSS ==-->
        <link href="/static/main/css/nice-select.min.css" rel="stylesheet" />
        <!--== Slick Slider Min CSS ==-->
        <link href="/static/main/css/slick.min.css" rel="stylesheet" />
        <!--== Magnific Popup Min CSS ==-->
        <link href="/static/main/css/magnific-popup.min.css" rel="stylesheet" />
        <!--== Slicknav Min CSS ==-->
        <link href="/static/main/css/slicknav.min.css" rel="stylesheet" />
        <!--== Animate Min CSS ==-->
        <link href="/static/main/css/animate.min.css" rel="stylesheet" />
        <!--== Ionicons Min CSS ==-->
        <link href="/static/main/css/ionicons.min.css" rel="stylesheet" />
        <!--== Font-Awesome Min CSS ==-->
        <link href="/static/main/css/font-awesome.min.css" rel="stylesheet" />
        <!--== Bootstrap Min CSS ==-->
        <link href="/static/main/css/bootstrap.min.css" rel="stylesheet" />

        <!--== Main Style CSS ==-->
        <link href="/static/main/css/style.css" rel="stylesheet" />
        <!--== Helper Min CSS ==-->
        <link href="/static/main/css/helper.min.css" rel="stylesheet" />
        <!-- endbuild -->
        <link rel="stylesheet" href="/static/css/edjllForm/edjllForm.css">
        <link rel="stylesheet" href="/static/css/bootstrap/dropdown.css">
        <link rel="stylesheet" href="/static/css/user/menu.css">

        <@toast.links></@toast.links>
        ${links}

    </head>
    <body>
        <@header.template></@header.template>
        ${body}
        <@footer.template></@footer.template>

        <!--=======================Javascript============================-->
        <!-- build:js /static/main/js/app.min.js -->
        <!--=== Modernizr Min Js ===-->
        <script src="/static/main/js/modernizr-3.6.0.min.js"></script>
        <!--=== jQuery Min Js ===-->
        <script src="/static/main/js/jquery-3.3.1.min.js"></script>
        <!--=== jQuery Migration Min Js ===-->
        <script src="/static/main/js/jquery-migrate-1.2.1.min.js"></script>
        <!--=== Popper Min Js ===-->
        <script src="/static/main/js/popper.min.js"></script>
        <!--=== Bootstrap Min Js ===-->
        <script src="/static/main/js/bootstrap.min.js"></script>
        <!--=== Slicknav Min Js ===-->
        <script src="/static/main/js/jquery.slicknav.min.js"></script>
        <!--=== Magnific Popup Min Js ===-->
        <script src="/static/main/js/jquery.magnific-popup.min.js"></script>
        <!--=== Slick Slider Min Js ===-->
        <script src="/static/main/js/slick.min.js"></script>
        <!--=== Nice Select Min Js ===-->
        <script src="/static/main/js/jquery.nice-select.min.js"></script>
        <!--=== Leaflet Min Js ===-->
        <script src="/static/main/js/leaflet.min.js"></script>
        <!--=== Countdown Js ===-->
        <script src="/static/main/js/countdown.js"></script>

        <!--=== Active Js ===-->
        <script src="/static/main/js/active.js"></script>
        <!-- endbuild -->

        <script>const authorize = <#if security.isAuthorized>true<#else>false</#if></script>
        <@toast.scripts></@toast.scripts>
        <script src="/static/js/shop/cart/count.js"></script>
        ${scripts}
    </body>
    </html>
</#macro>