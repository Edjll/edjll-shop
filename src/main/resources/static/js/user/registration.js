const forms = document.getElementsByName('registration');

const cart = JSON.parse(localStorage.getItem('cart'));

const param = {
    body: 'user',
    url: '/user/registration',
    method: 'post',
    bodyParam: [{name: 'cart', body: cart}],
    responseHandlers: [() => localStorage.setItem('cart', '[]')]
}