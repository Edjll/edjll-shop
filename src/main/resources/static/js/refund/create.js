const forms = document.getElementsByName('createRefund');

const param = {
    body: 'refund',
    url: '/user/refund/create',
    redirect: '/user/profile/refunds',
    method: 'post',
}