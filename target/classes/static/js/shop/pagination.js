const pagination = document.getElementsByClassName('pag')[0];

if (pagination) {
    const edjllForm = edjllForms[0];
    const pageInput = new EdjllInput(edjllForm, edjllForm, {type: 'hidden', name: 'page', defaultValue:`${page}`});
    edjllForm.items.push(pageInput);
    const edjllPagination = new EdjllPagination(
        (page) => new Promise((resolve, reject) => {
            pageInput.setValue(page);

            edjllForm.node.dispatchEvent(ev);

            resolve(null);
        }),
        (totalPages / pageSize) > 0 ? page : 0,
        Math.ceil(totalPages / pageSize) > 0 ? Math.ceil(totalPages / pageSize) : 1,
        pageSize,
        7
    );

    pagination.append(edjllPagination.node);
}