const forms = document.getElementsByName('updateProductCategory');

const excludedValues = {
    attribute: []
}

const param = {
    body: 'productCategory',
    url: '/admin/category/update',
    redirect: '/admin/category/all',
    method: 'post',
}