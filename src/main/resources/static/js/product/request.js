const form = document.getElementsByTagName('form')[1];

function sendRequest(body, url) {
    const request = new XMLHttpRequest();
    request.open('POST', url, true)
    request.setRequestHeader('Content-Type', 'application/json');
    request.setRequestHeader(form['_csrf_header'].value, form['_csrf'].value);
    request.onload = () => {
        console.log(request.response);
    };
    request.send(JSON.stringify(body));
}

function createRequestBody() {
    const body = {
        id: form['id'] === undefined ? null : form['id'].value,
        name: form['name'].value,
        cost: form['cost'].value,
        shelfLife: form['shelfLife'].value,
        description: form['description'].value,
        country: form['country'].value,
        manufacturer: form['manufacturer'].value,
        category: form['category'].value,
        attributes: parseWinform()
    };
    console.log(body);
    return body;
}

form.onsubmit = () => {
    const body = createRequestBody();
    sendRequest(body, '/admin/products/rest/create');
    return false;
}