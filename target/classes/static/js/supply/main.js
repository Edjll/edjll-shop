for (let edjllForm of edjllForms) {

    const table = edjllForm.node.getElementsByClassName('table')[0];
    const tableCreateRow = table.getElementsByClassName('table__body__create-row')[0];

    if (tableCreateRow) {
        tableCreateRow.onclick = () => {
            createTableRow(tableCreateRow);
        }
    }

    function createTableItem() {
        const tableItem = document.createElement('div');
        tableItem.classList.add('table__body__row__item');

        return tableItem;
    }

    function createDeleteItem() {
        const tableItem = document.createElement('div');
        tableItem.classList.add('table__body__row__item-remove');

        return tableItem;
    }

    function createTableRow(afterNode) {

        const deleteItem = createDeleteItem();

        const formGroup = new EdjllFormGroup(edjllForm, edjllForm, {name: 'products[]', deleteNode: deleteItem});
        const select = new EdjllSelect(edjllForm, formGroup, {name: 'product', label: 'Продукт', itemVisibleValue:["name"]}, {
            ajax: true,
            url: `${window.location.origin}/admin/product/get`,
            excludedValues: excludedValues.product
        }, {required: true});
        const input = new EdjllInput(edjllForm, formGroup, {
            name: 'count',
            label: 'Количество',
            type: 'number'
        }, {min: 0, max: 100, required: true});
        const selectDOM = createTableItem();
        selectDOM.appendChild(select.node);
        const inputDOM = createTableItem();
        inputDOM.appendChild(input.node);

        formGroup.node.classList.add('table__body__row');
        formGroup.node.append(deleteItem, selectDOM, inputDOM);

        formGroup.addItem(select);
        formGroup.addItem(input);

        edjllForm.items.push(formGroup);

        afterNode.before(formGroup.node);
    }
}