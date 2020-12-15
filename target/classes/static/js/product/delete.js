const forms = document.getElementsByName('deleteProduct');

const param = {
    body: 'product',
    url: '/admin/product/delete',
    redirect: '/admin/product/all',
    method: 'post',
}