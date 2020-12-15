function deleteCartInLocalStorage(product) {
    const cart = JSON.parse(localStorage.getItem('cart'));
    cart.splice(cart.findIndex(item => item.id === product.id), 1);
    localStorage.setItem('cart', JSON.stringify(cart));
    new EdjllToast(EdjllToastEvent.SUCCESS, 'Корзина', 'Продукт успешно удалён из корзины');
    getCartCountFromLocalStorage().then(data => cartTotal.textContent = data);
}

function deleteCartInDataBase(product) {
    const request = new XMLHttpRequest();
    request.open('post', '/basket/delete/product', true);
    request.setRequestHeader(
        document.getElementsByTagName('meta').namedItem('_csrf_header').content,
        document.getElementsByTagName('meta').namedItem('_csrf').content
    );
    request.onload = () => {
        if (request.response !== '') {
            new EdjllToast(EdjllToastEvent.SUCCESS, 'Корзина', request.response);
            getCartCountFromDataBase().then(data => cartTotal.textContent = data);
        }
    };
    const formData = new FormData();
    formData.append('product', new Blob([JSON.stringify({id: product.id})], {type: 'application/json'}));
    request.send(formData);
}