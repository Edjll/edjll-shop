const forms = document.getElementsByName('updateSale');

const param = {
    body: 'sale',
    url: '/admin/sale/update',
    redirect: '/admin/sale/all',
    method: 'post',
}