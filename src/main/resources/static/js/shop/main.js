const forms = document.getElementsByName('shop');

const param = {
    body: 'search',
    url: 'http://localhost:8080/shop',
    method: 'get',
}

const ev = new CustomEvent("submit", { });