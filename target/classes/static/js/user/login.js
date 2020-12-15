const forms = document.getElementsByName('login');

const cart = JSON.parse(localStorage.getItem('cart'));

const param = {
    body: 'user',
    url: '/login',
    method: 'post',
    bodyParam: [{name: 'cart', body: cart}],
    responseHandlers: [() => localStorage.setItem('cart', '[]')]
}