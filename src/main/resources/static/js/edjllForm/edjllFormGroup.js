class EdjllFormGroup {
    constructor(
        edjllForm,
        parentFormItem,
        attributes,
        node
    ) {
        this.edjllForm = edjllForm;
        this.parentFormItem = parentFormItem;

        this.attributes = {
            name: attributes && attributes.name ? attributes.name : 'edjll-form-group',
            deleteNodeClassName: attributes && attributes.deleteNodeClassName ? attributes.deleteNodeClassName : null,
            deleteNode: attributes && attributes.deleteNode ? attributes.deleteNode : null,
            createDeleteNode: attributes && attributes.createDeleteNode !== undefined && attributes.createDeleteNode !== null ? attributes.createDeleteNode : false
        }

        this.items = [];

        this.node = node ? node : this.createNode();


        if (!this.attributes.deleteNode) {
            if (this.attributes.deleteNodeClassName) {
                this.attributes.deleteNode = this.node.getElementsByClassName(this.attributes.deleteNodeClassName)[0];
                if (this.attributes.deleteNode) {
                }
            } else if (this.attributes.createDeleteNode) {
                this.attributes.deleteNode = this.createDeleteNode();
                this.node.append(this.attributes.deleteNode);
            }
        }

        if (this.attributes.deleteNode) {
            this.attributes.deleteNode.addEventListener('click', () => {
                this.delete();
            });
        }
    }

    static create(node, edjllForm, parentFormItem) {
        let edjllFormGroup;
        if (!node) {
            edjllFormGroup = new EdjllFormGroup(edjllForm, parentFormItem);
        } else {
            const settings = node.getElementsByClassName('edjll-form-group__settings')[0];
            if (!settings) {
                edjllFormGroup = new EdjllFormGroup(edjllForm, parentFormItem, null, node);
            } else {
                let attributes = null;
                try {
                    attributes = JSON.parse(settings.getElementsByClassName('edjll-form-group__settings__attributes')[0].textContent);
                } catch (e) { }
                settings.remove();
                edjllFormGroup = new EdjllFormGroup(edjllForm, parentFormItem, attributes, node);
            }
        }
        edjllFormGroup.scan();
        return edjllFormGroup;
    }

    createNode() {
        const node = document.createElement('div');
        node.classList.add('edjll-form-group');

        return node;
    }

    scan() {
        this.items = this.items.concat(EdjllForm.scanEdjllFormGroup(this.node, this.edjllForm, this));
        this.items = this.items.concat(EdjllForm.scanEdjllSelect(this.node, this.edjllForm, this));
        this.items = this.items.concat(EdjllForm.scanEdjllCheckbox(this.node, this.edjllForm, this));
        this.items = this.items.concat(EdjllForm.scanEdjllFileInput(this.node, this.edjllForm, this));
        this.items = this.items.concat(EdjllForm.scanEdjllInput(this.node, this.edjllForm, this));
        this.items = this.items.concat(EdjllForm.scanEdjllTextarea(this.node, this.edjllForm, this));
        this.items = this.items.concat(EdjllForm.scanEdjllGraph(this.node, this.edjllForm, this));
    }

    searchFormItemByName(name, level = 0, depth = true) {
        if (level) {
            if (this.parentFormItem) {
                return this.parentFormItem.searchFormItemByName(name, level--, depth);
            }
        }
        return EdjllForm.searchFormItemByName(name, this.items, depth);
    }

    addItem(formItem) {
        this.items.push(formItem);
    }

    getValue() {
        return EdjllForm.getValue(this.items);
    }

    createDeleteNode() {
        const edjllFormGroupDelete = document.createElement('div');
        edjllFormGroupDelete.classList.add('edjll-form-group__delete');

        return edjllFormGroupDelete;
    }

    delete() {
        if (this.parentFormItem) {
            this.parentFormItem.items.splice(this.parentFormItem.items.indexOf(this), 1);
        }
        this.items.forEach(item => item.delete());
        this.node.remove();
    }

    validate() {
        let validation = true;
        this.items.forEach(item => {
            if (!item.validate()) validation = false;
        });
        return validation;
    }
}