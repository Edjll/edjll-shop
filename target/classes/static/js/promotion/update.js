const forms = document.getElementsByName('updatePromotion');

const excludedValues = {
    product: []
}

const param = {
    body: 'promotion',
    url: '/admin/promotion/update',
    redirect: '/admin/promotion/all',
    method: 'post',
}