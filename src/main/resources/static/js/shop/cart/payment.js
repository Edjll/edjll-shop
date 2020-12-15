const forms = document.getElementsByName('payment');

const param = {
    body: 'payment',
    url: '/shop/cart/payment',
    responseHandlers: [(data) => window.location = window.location.origin + data],
    method: 'post',
}