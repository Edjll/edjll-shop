const forms = document.getElementsByName('createProductCategory');

const excludedValues = {
    attribute: []
}

const param = {
    body: 'productCategory',
    url: '/admin/category/create',
    redirect: '/admin/category/all',
    method: 'post',
}