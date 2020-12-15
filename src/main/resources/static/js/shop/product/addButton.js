const addButton = document.getElementsByClassName('add-button')[0];

if (addButton) {
    const item = getItemFromLocalStorage(productId);
    if (item) {
        changeAddButton();
    }
}

function changeAddButton(item) {
    addButton.textContent = 'Изменить в корзине';
    if (edjllInput && item) {
        edjllInput.setValue(item.count);
    }
}

function getItemFromLocalStorage(id) {
    const cart = JSON.parse(localStorage.getItem('cart'));
    if (cart) {
        for (let cartItem of cart) {
            if (cartItem.id === id) {
                return cartItem;
            }
        }
    }
    return null;
}