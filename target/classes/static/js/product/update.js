const forms = document.getElementsByName('updateProduct');

const excludedValues = {
    attribute: []
}

const param = {
    body: 'product',
    url: '/admin/product/update',
    redirect: '/admin/product/all',
    method: 'post',
}