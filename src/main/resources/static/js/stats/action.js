const edjllForm = edjllForms[0];

if (edjllForm) {
    const dateStartInput = edjllForm.searchFormItemByName('dateStart');
    const dateEndInput = edjllForm.searchFormItemByName('dateEnd');

    edjllForm.node.dispatchEvent(new CustomEvent("submit", { }));

    dateStartInput.attributes.onChangeValue = () => {
        if (dateStartInput.getValue() !== '' && dateEndInput.getValue() !== '') {
            edjllForm.node.dispatchEvent(new CustomEvent("submit", { }));
        }
    }

    dateEndInput.attributes.onChangeValue = () => {
        if (dateStartInput.getValue() !== '' && dateEndInput.getValue() !== '') {
            edjllForm.node.dispatchEvent(new CustomEvent("submit", { }));
        }
    }
}