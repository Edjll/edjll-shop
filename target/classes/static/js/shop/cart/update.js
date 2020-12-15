function updateCartInLocalStorage(product) {
    const cart = JSON.parse(localStorage.getItem('cart'));
    cart.find(item => item.id === product.id).count = product.count;
    localStorage.setItem('cart', JSON.stringify(cart));
}

function updateCartInDataBase(product) {
    const request = new XMLHttpRequest();
    request.open('post', '/basket/add/product', true);
    request.setRequestHeader(
        document.getElementsByTagName('meta').namedItem('_csrf_header').content,
        document.getElementsByTagName('meta').namedItem('_csrf').content
    );
    request.onload = () => { };
    const formData = new FormData();
    formData.append('product', new Blob([JSON.stringify({id: product.id, count: product.count})], {type: 'application/json'}));
    request.send(formData);
}