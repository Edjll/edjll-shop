const edjllForms = [];

for (let form of forms) {
    const edjllForm = EdjllForm.create(form);
    edjllForms.push(edjllForm);
    edjllForm.scan();

    const requestParam = {
        body: typeof param !== "undefined" && param.body ? param.body : '',
        url: typeof param !== "undefined" && param.url ? param.url : window.location,
        redirect: typeof param !== "undefined" && param.redirect ? param.redirect : null,
        method: typeof param !== "undefined" && param.method ? param.method : 'post',
        csrf: {
            header: document.getElementsByTagName('meta').namedItem('_csrf_header').content,
            value: document.getElementsByTagName('meta').namedItem('_csrf').content
        },
        bodyParam: typeof param !== "undefined" && param.bodyParam ? param.bodyParam : [],
        responseHandlers: typeof param !== "undefined" && param.responseHandlers ? param.responseHandlers : []
    }

    form.onsubmit = () => false;

    form.addEventListener('submit', () => {
        if (edjllForm.validate()) {
            const body = new FormData();
            const edjllFormValue = edjllForm.getValue();

            if (requestParam.method === 'get') {
                const url = new URL(requestParam.url);
                url.searchParams.set(requestParam.body, JSON.stringify(edjllFormValue.object));
                window.location = url.href;
            }

            body.append(requestParam.body, new Blob([JSON.stringify(edjllFormValue.object)], {type: 'application/json'}));
            edjllFormValue.files.forEach(file => {
                file.files.forEach(f => {
                    let idFile = f.file;
                    if (!idFile) {
                        idFile = new File([''], '', {type : null});
                    }
                    body.append(requestParam.body + file.name.slice(0, 1).toUpperCase() + file.name.slice(1), idFile, f.id);
                });
            });

            if (requestParam.bodyParam && requestParam.bodyParam.length) {
                requestParam.bodyParam.forEach(bodyParam => {
                    body.append(bodyParam.name, new Blob([JSON.stringify(bodyParam.body)], {type: 'application/json'}));
                });
            }

            edjllForm.activateSpinner();

            const request = new XMLHttpRequest();
            request.open(requestParam.method, requestParam.url, true);
            request.setRequestHeader(requestParam.csrf.header, requestParam.csrf.value);
            request.onload = () => {
                if (request.status >= 200 && request.status <= 299) {
                    requestParam.responseHandlers.forEach(responseHandler => {
                        responseHandler(request.response);
                    });
                    if (requestParam.redirect) {
                        window.location = requestParam.redirect;
                    } else if (request.getResponseHeader('Referer')) {
                        window.location = request.getResponseHeader('Referer');
                    }
                } else if (request.status >= 400 && request.status <= 401) {
                    const unused = edjllForm.setErrors(request.response);
                    unused.forEach(error => {
                        new EdjllToast(EdjllToastEvent.ERROR, 'Ошибка', error[1], 300000);
                    });
                    edjllForm.deactivateSpinner();
                } else if (request.status === 404) {
                    new EdjllToast(EdjllToastEvent.ERROR, 'Ошибка', 'Страница не найдена', 300000);
                } else {
                    new EdjllToast(EdjllToastEvent.ERROR, 'Ошибка', 'На сервере произошла ошибка', 300000);
                }
            };
            if (requestParam.method === 'get') request.send(body);
            else request.send(body);
        }
    });
}