const forms = document.getElementsByName('updateRefund');

const param = {
    body: 'refund',
    url: '/user/refund/update',
    redirect: `/user/profile/refunds`,
    method: 'post'
}