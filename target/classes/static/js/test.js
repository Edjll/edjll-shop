const request = new XMLHttpRequest();
let result;

request.open("post", "/admin/rest/test", true);
request.setRequestHeader(csrf.header, csrf.value);
request.setRequestHeader("Content-Type", "application/json");
request.onload = () => {
    result = request.response;
}

const body = {
    cities : [{
        id: 1,
        name: null,
        country : {
            id : 1,
            name : null
        }
    }]
}

request.send(JSON.stringify(body));