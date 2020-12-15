const forms = document.getElementsByName('createProduct');

const excludedValues = {
    attribute: []
}

const param = {
    body: 'product',
    url: '/admin/product/create',
    redirect: '/admin/product/all',
    method: 'post',
}