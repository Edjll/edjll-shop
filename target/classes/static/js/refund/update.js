const forms = document.getElementsByName('updateRefund');

const param = {
    body: 'refund',
    url: '/admin/refund/update',
    redirect: '/admin/refund/all',
    method: 'post'
}