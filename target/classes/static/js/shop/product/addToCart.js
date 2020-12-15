if (!localStorage.getItem('cart')) {
    localStorage.setItem('cart', JSON.stringify([]));
}

function addToLocalStorageCart(id) {
    const cart = JSON.parse(localStorage.getItem('cart'));
    let newItem = true;
    for (let cartItem of cart) {
        if (cartItem.id === id) {
            let countValue = 1;
            if (typeof count !== 'undefined' && count && count.value > 0) countValue = count.value;
            if (countValue === cartItem.count) {
                return;
            }
            newItem = false;
            cartItem.count = countValue;
            new EdjllToast(EdjllToastEvent.SUCCESS, 'Корзина', 'Продукт успешно изменён в корзине');
            break;
        }
    }
    if (newItem) {
        cart.push({
            id: id,
            count: typeof count !== 'undefined' && count && count.value > 0 ? count.value : 1
        });
        changeProduct(id);
        new EdjllToast(EdjllToastEvent.SUCCESS, 'Корзина', 'Продукт успешно добавлен в корзину');
    }
    localStorage.setItem('cart', JSON.stringify(cart));
    if (newItem && typeof addButton !== 'undefined' && addButton) changeAddButton();
    getCartCountFromLocalStorage().then(data => cartTotal.textContent = data);
}

function addToDatabaseCart(id) {
    const request = new XMLHttpRequest();
    request.open('post', '/basket/add/product', true);
    request.setRequestHeader(
        document.getElementsByTagName('meta').namedItem('_csrf_header').content,
        document.getElementsByTagName('meta').namedItem('_csrf').content
    );
    request.onload = () => {
        if (request.response !== '') {
            new EdjllToast(EdjllToastEvent.SUCCESS, 'Корзина', request.response);
            if (typeof addButton !== 'undefined' && addButton) changeAddButton();
            changeProduct(id);
            getCartCountFromDataBase().then(data => cartTotal.textContent = data);
        }
    };
    const formData = new FormData();
    let countValue = 1;
    if (typeof count !== 'undefined' && count && count.value > 0) countValue = count.value;
    formData.append('product', new Blob([JSON.stringify({id: id, count: countValue})], {type: 'application/json'}));
    request.send(formData);
}

function changeProduct(id) {
    const wrapper = document.getElementsByClassName(`product-item-${id}`)[0];
    if (wrapper) {
        const btn = wrapper.getElementsByClassName('btn-add-to-cart')[0];
        if (btn) {
            btn.onclick = () => { };
            const check = document.createElement('a');
            check.classList.add('btn-add-to-cart');
            check.href = '/shop/cart';

            const icon = document.createElement('i');
            icon.classList.add('ion-android-checkmark-circle');

            check.append(icon);

            btn.before(check);
            btn.remove();
        }
    }
}

if (!authorize) {
    const cart = JSON.parse(localStorage.getItem('cart'));
    cart.forEach(item => changeProduct(item.id));
}