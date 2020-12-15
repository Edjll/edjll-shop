const forms = document.getElementsByName('deletePromotion');

const param = {
    body: 'promotion',
    url: '/admin/promotion/delete',
    redirect: '/admin/promotion/all',
    method: 'post',
}