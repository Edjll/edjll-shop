const forms = document.getElementsByName('createPromotion');

const excludedValues = {
    product: []
}

const param = {
    body: 'promotion',
    url: '/admin/promotion/create',
    redirect: '/admin/promotion/all',
    method: 'post',
}