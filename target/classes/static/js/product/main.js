const requiredAttributes = [];

edjllForms[0].searchFormItemByName('category', 0, false).attributes.onChangeValue = (value) => {
    const edjllObs = edjllForms[0].searchFormItemByName('attributes');
    const request = new XMLHttpRequest();
    const url = new URL(`${window.location.origin}/admin/attribute/get/productCategory`);
    url.searchParams.set('category', value);
    request.open('get', url.href, true);
    request.onload = () => {
        requiredAttributes.forEach(item => item.delete());
        const response = JSON.parse(request.response);
        edjllObs.attributes.patterns.createWrapper.settings.attributes.createDeleteNode = false;
        response.forEach(item => {
            const formGroup = edjllObs.createPatternItem();
            formGroup.searchFormItemByName('category').setValue(item.category);
            formGroup.searchFormItemByName('name').setValue(item.name);
            formGroup.searchFormItemByName('description').setValue(item.description);
            edjllObs.addItem(formGroup);
            edjllObs.body.lastElementChild.before(formGroup.node);
            requiredAttributes.push(formGroup);
        });
        edjllObs.attributes.patterns.createWrapper.settings.attributes.createDeleteNode = true;
    }
    request.send();
}