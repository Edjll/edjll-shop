const tableBody = document.getElementsByClassName('products')[0];
const cartPrice = document.getElementsByClassName('cart-price')[0];

function getCartProductsFromDatabase(url) {
    const request = new XMLHttpRequest();
    request.open('get', url, true);
    request.onload = () => {
        const cart = JSON.parse(request.response);
        cart.forEach(product => {
            createCartProduct(product, tableBody);
        });
        if (cart.length === 0 || (cartPrice && Number(cartPrice.textContent.replace(/₽/, '')) === 0)) {
            removePayButton();
        }
    }
    request.send();
}

function removePayButton() {
    const btn = document.getElementsByClassName('proceed-checkout-btn')[0];
    if (btn) {
        btn.remove();
    }
}

function getCartProductsFromLocalStorage() {
    const cart = JSON.parse(localStorage.getItem('cart'));
    if (cart.length) {
        const url = new URL(window.location.origin + '/basket/get/product');
        url.searchParams.set('products', JSON.stringify(cart));

        getCartProductsFromDatabase(url.href);
    } else {
        removePayButton();
    }
}

function createCartProduct(cartProduct, parent) {
    const edjllFormGroupNode = document.createElement('tr');
    const edjllFormGroup = EdjllFormGroup.create(edjllFormGroupNode, null, null);
    edjllFormGroup.node.classList.add('edjll-form-group');
    edjllFormGroup.attributes.name = 'products[]';

    const idEdjllInput = new EdjllInput(null, edjllFormGroup, {name: 'id', type: 'hidden', defaultValue: cartProduct.id});
    edjllFormGroup.addItem(idEdjllInput);

    const productList = document.createElement('td');
    productList.classList.add('product-list');

    const cartProductItem = document.createElement('div');
    cartProductItem.classList.add('cart-product-item', 'd-flex', 'align-items-center');

    const removeItem = document.createElement('div');
    removeItem.classList.add('remove-icon');

    const button = document.createElement('div');

    const icon = document.createElement('i');
    icon.classList.add('fa', 'fa-trash-o');

    button.append(icon);
    removeItem.append(button);

    const link = document.createElement('a');
    link.href = '/shop/product/' + cartProduct.id;
    link.classList.add('product-thumb');

    const img = document.createElement('img');
    img.src = '/static/image/productDataImage/' + cartProduct.image;

    link.append(img);

    const title = document.createElement('a');
    title.href = '/shop/product/' + cartProduct.id;
    title.classList.add('product-name');
    title.textContent = cartProduct.name;

    cartProductItem.append(removeItem, link, title);
    productList.append(cartProductItem);

    const priceTd = document.createElement('td');

    if (cartProduct.discount != null) {
        const price = document.createElement('span');
        price.classList.add('line-through', 'pr-1');
        price.textContent = cartProduct.price + '₽';
        priceTd.append(price, cartProduct.discountPrice + '₽');
    } else {
        priceTd.append(cartProduct.price + '₽');
    }

    const countTd = document.createElement('td');

    const fullPriceTd = document.createElement('td');

    const fullPrice = document.createElement('span');
    fullPrice.classList.add('full-price');
    if (cartProduct.maxCount > 0) {
        fullPrice.textContent = cartProduct.discountPrice * cartProduct.count + '₽';
    } else {
        fullPrice.textContent = 0 + '₽';
    }

    cartPrice.textContent = Number(cartPrice.textContent.replace(/₽/, '')) + Number(fullPrice.textContent.replace(/₽/, '')) + '₽';

    fullPriceTd.append(fullPrice);

    if (cartProduct.maxCount > 0) {
        const countEdjllInput = new EdjllInput(null, edjllFormGroup, {name: 'count', type: 'number', defaultValue: cartProduct.count <= cartProduct.maxCount ? cartProduct.count : cartProduct.maxCount, controllers: true}, {min: 1, max: cartProduct.maxCount});
        countEdjllInput.node.classList.add('m-0');
        edjllFormGroup.addItem(countEdjllInput);

        countTd.append(countEdjllInput.node);

        countEdjllInput.input.onchange = () => {
            const oldPrice = Number(fullPrice.textContent.replace(/₽/, ''));
            fullPrice.textContent = countEdjllInput.input.valueAsNumber * cartProduct.discountPrice + '₽';
            cartPrice.textContent = Number(cartPrice.textContent.replace(/₽/, '')) + Number(fullPrice.textContent.replace(/₽/, '')) - oldPrice + '₽';

            if (authorize) {
                updateCartInDataBase({id: cartProduct.id, count: countEdjllInput.input.valueAsNumber});
            } else {
                updateCartInLocalStorage({id: cartProduct.id, count: countEdjllInput.input.valueAsNumber});
            }
        }
    } else {
        const empty = document.createElement('span');
        empty.textContent = 'Нет в наличии';
        countTd.append(empty);
    }

    button.onclick = () => {

        const products = document.getElementsByClassName('product-list');
        if (products.length === 1) {
            removePayButton();
        }

        cartPrice.textContent = Number(cartPrice.textContent.replace(/₽/, '')) - Number(fullPrice.textContent.replace(/₽/, '')) + '₽';
        edjllFormGroup.delete();

        if (authorize) {
            deleteCartInDataBase({id: cartProduct.id});
        } else {
            deleteCartInLocalStorage({id: cartProduct.id});
        }
    }

    edjllFormGroup.node.append(productList, priceTd, countTd, fullPriceTd);

    parent.append(edjllFormGroup.node);
}

if (authorize) {
    getCartProductsFromDatabase('/basket/get/product');
} else {
    getCartProductsFromLocalStorage();
}