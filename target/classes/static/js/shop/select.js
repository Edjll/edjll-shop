const edjllForm = edjllForms[0];
const select = edjllForm.searchFormItemByName('sort');
if (select) {
    select.attributes.onChangeValue = () => {
        edjllForm.node.dispatchEvent(ev);
    }
}