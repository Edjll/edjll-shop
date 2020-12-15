const forms = document.getElementsByName('deleteCategory');

const param = {
    body: 'productCategory',
    url: '/admin/category/delete',
    redirect: '/admin/category/all',
    method: 'post',
}