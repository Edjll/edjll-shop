<#import "macro/edjllForm.ftlh" as edjllForm>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <@edjllForm.links></@edjllForm.links>
    <title>Document</title>
</head>
<body>
<form method="post" action="/login" class="edjll-form">
    <div class="edjll-input"></div>
    <div class="edjll-checkbox"></div>
</form>
<script>
    const form = document.getElementsByClassName('edjll-form')[0];
</script>
<@edjllForm.scripts></@edjllForm.scripts>
</body>
</html>