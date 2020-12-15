class EdjllCheckbox {
    constructor(
        edjllForm,
        parentFormItem,
        attributes,
        node
    ) {
        this.edjllForm = edjllForm;
        this.parentFormItem = parentFormItem;

        this.attributes = {
            name: attributes && attributes.name ? attributes.name : 'edjll-checkbox',
            label: attributes && attributes.label ? attributes.label :  'edjll-checkbox',
            defaultValue: attributes && attributes.defaultValue ? attributes.defaultValue : false
        }

        this.node = node ? node : this.createNode();
        this.input = this.createInput();
        this.label = this.createLabel();
        this.node.append(this.label);
    }

    static create(node, edjllForm, parentFormItem) {
        if (!node) return new EdjllCheckbox(edjllForm, parentFormItem);
        const settings = node.getElementsByClassName('edjll-checkbox__settings')[0];
        if (!settings) return new EdjllCheckbox(edjllForm, parentFormItem, null, node);
        else {
            let attributes = null;
            try {
                attributes = JSON.parse(settings.getElementsByClassName('edjll-checkbox__settings__attributes')[0].textContent);
            } catch (e) { }
            settings.remove();
            return new EdjllCheckbox(
                edjllForm,
                parentFormItem,
                attributes,
                node
            );
        }
    }

    createNode() {
        const node = document.createElement('div');
        node.classList.add('edjll-checkbox');

        return node;
    }

    createLabel() {
        const label = document.createElement('label');
        label.classList.add('edjll-checkbox__label');

        const text = document.createElement('span');
        text.classList.add('edjll-checkbox__label__text');
        text.textContent = this.attributes.label;

        label.append(this.input, text);

        return label;
    }

    createInput() {
        const input = document.createElement('input');

        input.classList.add('edjll-checkbox__label__input');
        input.name = this.attributes.name;
        input.type = 'checkbox';
        input.checked = this.attributes.defaultValue;

        return input;
    }

    setValue(value) {
        this.input.value = value;
    }

    getValue() {
        return this.input.checked;
    }

    validate() {
        return true;
    }
}