function getCartCountFromLocalStorage() {
    return new Promise((resolve, reject) => {
        const cart = JSON.parse(localStorage.getItem('cart'));
        resolve(cart.length);
    });
}

function getCartCountFromDataBase() {
    return new Promise((resolve, reject) => {
        const request = new XMLHttpRequest();
        request.open('get', '/basket/get/product/count', true);
        request.onload = () => {
            resolve(request.response);
        }
        request.send();
    });
}

const cartTotal = document.getElementsByClassName('cart-total')[0];

if (cartTotal) {
    if (authorize) {
        getCartCountFromDataBase().then(data => cartTotal.textContent = data);
    } else {
        getCartCountFromLocalStorage().then(data => cartTotal.textContent = data);
    }
}