class EdjllForm {
    constructor(node = null, spinner = null) {
        this.node = node ? node : this.createNode();
        this.spinner = spinner;
        this.items = [];
    }

    static create(node) {
        const spinner = node.getElementsByClassName('edjll-spinner')[0];
        if (spinner) return new EdjllForm(node, spinner);
        return new EdjllForm(node);
    }

    createNode() {
        const node = document.createElement('div');
        node.classList.add('edjll-form');

        return node;
    }

    activateSpinner() {
        if (this.spinner) {
            if (!this.spinner.classList.contains('edjll-spinner-blurred')) {
                this.spinner.classList.add('edjll-spinner-blurred');
            }
            this.node.append(this.spinner);
        }
    }

    deactivateSpinner() {
        if (this.spinner) this.spinner.remove();
    }

    scan() {
        this.items = this.items.concat(EdjllForm.scan(this.node, this, this));
        this.deactivateSpinner();
    }

    static scan(node, edjllForm, parentFormItem) {
        let items = [];
        items = items.concat(this.scanEdjllObs(node, edjllForm, parentFormItem));
        items = items.concat(this.scanEdjllFormGroup(node, edjllForm, parentFormItem));
        items = items.concat(this.scanEdjllSelect(node, edjllForm, parentFormItem));
        items = items.concat(this.scanEdjllCheckbox(node, edjllForm, parentFormItem));
        items = items.concat(this.scanEdjllFileInput(node, edjllForm, parentFormItem));
        items = items.concat(this.scanEdjllInput(node, edjllForm, parentFormItem));
        items = items.concat(this.scanEdjllTextarea(node, edjllForm, parentFormItem));
        items = items.concat(this.scanEdjllGraph(node, edjllForm, parentFormItem));
        return items;
    }

    static scanEdjllFormGroup(node, edjllForm, parentFormItem = null) {
        return EdjllForm.scanEdjllFormItem(node, EdjllFormGroup, edjllForm, parentFormItem);
    }

    static scanEdjllObs(node, edjllForm, parentFormItem = null) {
        return EdjllForm.scanEdjllFormItem(node, EdjllObs, edjllForm, parentFormItem);
    }

    static scanEdjllSelect(node, edjllForm, parentFormItem = null) {
        return EdjllForm.scanEdjllFormItem(node, EdjllSelect, edjllForm, parentFormItem);
    }

    static scanEdjllCheckbox(node, edjllForm, parentFormItem = null) {
        return EdjllForm.scanEdjllFormItem(node, EdjllCheckbox, edjllForm, parentFormItem);
    }

    static scanEdjllInput(node, edjllForm, parentFormItem = null) {
        return EdjllForm.scanEdjllFormItem(node, EdjllInput, edjllForm, parentFormItem);
    }

    static scanEdjllFileInput(node, edjllForm, parentFormItem = null) {
        return EdjllForm.scanEdjllFormItem(node, EdjllFileInput, edjllForm, parentFormItem);
    }

    static scanEdjllTextarea(node, edjllForm, parentFormItem = null) {
        return EdjllForm.scanEdjllFormItem(node, EdjllTextarea, edjllForm, parentFormItem);
    }

    static scanEdjllGraph(node, edjllForm, parentFormItem = null) {
        return EdjllForm.scanEdjllFormItem(node, EdjllGraph, edjllForm, parentFormItem);
    }

    static scanEdjllFormItem(node, classType, edjllForm, parentFormItem = null) {
        let className, items = [];

        if (classType === EdjllFormGroup) className = EdjllFormType.EDJLL_FORM_GROUP;
        else if (classType === EdjllObs) className = EdjllFormType.EDJLL_OBS;
        else if (classType === EdjllSelect) className = EdjllFormType.EDJLL_SELECT;
        else if (classType === EdjllCheckbox) className = EdjllFormType.EDJLL_CHECKBOX;
        else if (classType === EdjllFileInput) className = EdjllFormType.EDJLL_FILE_INPUT;
        else if (classType === EdjllInput) className = EdjllFormType.EDJLL_INPUT;
        else if (classType === EdjllTextarea) className = EdjllFormType.EDJLL_TEXTAREA;
        else if (classType === EdjllGraph) className = EdjllFormType.EDJLL_GRAPH;

        const itemsDOM = node.getElementsByClassName(className);
        let startLength = itemsDOM.length;
        for (let i = 0; i < itemsDOM.length; i++) {
            if (

                itemsDOM[i].parentNode.closest(`.${EdjllFormType.EDJLL_FORM_GROUP}, .${EdjllFormType.EDJLL_OBS}`) === node
                || itemsDOM[i].parentNode.closest(`.${EdjllFormType.EDJLL_FORM_GROUP}, .${EdjllFormType.EDJLL_OBS}`) === null

            ) {
                const item = classType.create(itemsDOM[i], edjllForm, parentFormItem);
                items.push(item);
            }
            if (itemsDOM.length !== startLength) {
                i += itemsDOM.length - startLength;
                startLength = itemsDOM.length;
            }
        }
        return items;
    }

    getValue() {
        return EdjllForm.getValue(this.items);
    }

    static getValue(items) {
        const object = {};
        let files = [];
        items.forEach(item => {
            if (item instanceof EdjllFileInput) {
                files.push({name: item.attributes.name, files: item.getValue()});
            } else {
                const name = item.attributes.name.replace(/\[]/, '');
                if (object[name] !== undefined && !(object[name] instanceof Array)) {
                    const value = object[name];
                    object[name] = [];
                    object[name].push(value);
                }
                if (object[name] instanceof Array) {
                    if (item instanceof EdjllFormGroup) {
                        const value = item.getValue();
                        object[name].push(value.object);
                        files = files.concat(value.files);
                    } else {
                        object[name].push(item.getValue());
                    }
                } else {
                    if (item instanceof EdjllFormGroup) {
                        let value;
                        if (/\[]/.test(item.attributes.name)) {
                            value = item.getValue();
                            object[name] = [];
                            object[name].push(value.object);
                        } else {
                            value = item.getValue();
                            object[name] = value.object;
                        }
                        files = files.concat(value.files);
                    } else {
                        object[name] = item.getValue();
                    }
                }
            }
        });
        return {
            object: object,
            files: files
        };
    }

    setErrors(errors) {
        errors = errors.replace(/[{}]/g, '');
        errors = errors.split('",');
        errors = errors.map(error => {
            error += '"';
            let errorAfterSplit = error.split(':');
            errorAfterSplit = errorAfterSplit.map(errorAfterSplitItem => errorAfterSplitItem.replace(/"/g, ''));
            return errorAfterSplit;
        });
        const unused = [];
        errors.forEach(error => {
            const formItem = this.searchFormItem(error[0]);
            if (formItem) {
                formItem.createError(error[1]);
            } else  {
                unused.push(error);
            }
        });
        return unused;
    }

    searchFormItem(nameChain) {
        return EdjllForm.searchFormItem(nameChain, this);
    }

    static searchFormItem(nameChain, formItem) {
        let names = nameChain;
        if (typeof names === 'string') {
            names = nameChain.split('.');
            names.shift();
        }

        if (!names.length) return null;

        const dirtyName = names.shift();
        let index = 0;

        const name = dirtyName.match(/[^\[\]]*/g)[0];
        const indexMatch = dirtyName.match(/(?<=\[)\d*/g);

        if (indexMatch) index = indexMatch[0];

        const formItems = formItem.items.filter(item => item.attributes.name.replace(/\[]/, '') === name);
        if (formItems.length) {
            if (names.length) {
                return EdjllForm.searchFormItem(names, formItems[index]);
            }
            return formItems[index];
        }
        return null;
    }

    searchFormItemByName(name, level = 0, depth = true) {
        return EdjllForm.searchFormItemByName(name, this.items, depth);
    }

    static searchFormItemByName(name, items, depth = true) {
        const searchedItem = items.find(item => {
            if (item.attributes.name === name) return item;
            if (depth) {
                if (item instanceof EdjllFormGroup) {
                    return EdjllForm.searchFormItemByName(name, item.items);
                }
            }
        });
        return searchedItem !== undefined ? searchedItem : null;
    }

    validate() {
        let validation = true;
        this.items.forEach(item => {
            if (!item.validate()) validation = false;
        });
        return validation;
    }
}