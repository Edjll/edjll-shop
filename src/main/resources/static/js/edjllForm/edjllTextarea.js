class EdjllTextarea {
    constructor(
        edjllForm,
        parentFormItem,
        attributes,
        validation,
        node
    ) {
        this.edjllForm = edjllForm;
        this.parentFormItem = parentFormItem;

        this.attributes = {
            name: attributes && attributes.name ? attributes.name : 'edjll-textarea',
            label: attributes && attributes.label ? attributes.label : 'edjll-textarea',
            defaultValue: attributes && attributes.defaultValue ? attributes.defaultValue : '',
        }
        this.validation = new EdjllValidation(edjllForm, validation);

        this.node = node ? node : this.createNode();
        this.label = this.createLabel();
        this.textarea = this.createTextarea();

        this.label.append(this.textarea);
        this.node.append(this.label);
    }

    static create(node, edjllForm, parentFormItem) {
        if (!node) return new EdjllTextarea(edjllForm, parentFormItem);
        const settings = node.getElementsByClassName('edjll-textarea__settings')[0];
        if (!settings) return new EdjllTextarea(edjllForm, parentFormItem, null, null, node);
        else {
            let attributes = null, validation = null;
            try {
                attributes = JSON.parse(settings.getElementsByClassName('edjll-textarea__settings__attributes')[0].textContent);
            } catch (e) { }
            try {
                validation = JSON.parse(settings.getElementsByClassName('edjll-textarea__settings__validation')[0].textContent);
            } catch (e) { }
            settings.remove();
            return new EdjllTextarea(
                edjllForm,
                parentFormItem,
                attributes,
                validation,
                node
            );
        }
    }

    createNode() {
        const node = document.createElement('div');
        node.classList.add('edjll-textarea');

        return node;
    }

    createLabel() {
        const label = document.createElement('label');
        label.classList.add('edjll-textarea__label');

        const text = document.createElement('span');
        text.classList.add('edjll-textarea__label__text');
        text.textContent = this.attributes.label;

        label.append(text);

        return label;
    }

    createTextarea() {
        const textarea = document.createElement('textarea');
        textarea.classList.add('edjll-textarea__label__textarea');
        textarea.name = this.attributes.name;
        textarea.textContent = this.attributes.defaultValue;
        textarea.oninput = this.textareaChange.bind(this);

        return textarea;
    }

    textareaChange() {
        this.removeError();
    }

    setValue(value) {
        this.textarea.value = value;
    }

    getValue() {
        return this.textarea.value;
    }

    createError(message) {
        if (this.validation.error) {
            this.validation.error.text.textContent = message;
        } else {
            this.validation.error = EdjllValidation.createError(message);
            this.node.append(this.validation.error.node);
        }
    }

    removeError() {
        if (this.validation.error) {
            this.validation.error.node.remove();
            this.validation.error = null;
        }
    }

    validate() {
        const message = this.validation.validate(this.textarea.value);
        if (message) {
            this.createError(message);
            return false;
        } else {
            this.removeError();
        }
        return true;
    }
}