class EdjllObs extends EdjllFormGroup {
    constructor(
        edjllForm,
        parentFormItem,
        attributes,
        node
    ) {
        super(
            edjllForm,
            parentFormItem,
            attributes,
            node
        );

        if (node) this.scan();

        this.attributes.buttonAddItemText = attributes && attributes.buttonAddItemText ? attributes.buttonAddItemText : 'Добавить элемент';

        this.attributes.patterns = {
            createItem: attributes && attributes.patterns && attributes.patterns.createItem ? attributes.patterns.createItem : null,
            createWrapper: attributes && attributes.patterns && attributes.patterns.createWrapper ? attributes.patterns.createWrapper : {
                settings: {
                    attributes: {
                        name: 'wrapper',
                        createDeleteNode: true
                    }
                }
            }
        }

        this.buttonAddItem = this.createButtonAddItem();
        this.body = this.node.getElementsByClassName('edjll-obs__body')[0];
        if (this.body == null) {
            this.body = this.createBody();
            this.body.resizeObserver = new ResizeObserver(
                entries => {
                    for (let entry of entries) {
                        if (entry.contentBoxSize && entry.contentBoxSize.inlineSize ? entry.contentBoxSize.inlineSize : entry.contentRect.width > this.node.offsetWidth) {
                            if (!this.node.classList.contains('edjll-obs--grid-width')) {
                                this.node.classList.add('edjll-obs--grid-width');
                            }
                        } else {
                            this.node.classList.remove('edjll-obs--grid-width');
                        }
                    }
                }
            );
            this.body.resizeObserver.observe(this.body);
        }
        this.body.append(this.buttonAddItem);
        this.node.append(this.body);

    }

    static create(node, edjllForm, parentFormItem) {
        let edjllObs;
        if (!node) {
            edjllObs = new EdjllObs(edjllForm, parentFormItem);
        } else {
            const settings = node.getElementsByClassName('edjll-obs__settings')[0];
            if (!settings) {
                edjllObs = new EdjllObs(edjllForm, parentFormItem, null, node);
            } else {
                let attributes = null;
                try {
                    attributes = JSON.parse(settings.getElementsByClassName('edjll-obs__settings__attributes')[0].textContent);
                } catch (e) { }
                settings.remove();
                edjllObs = new EdjllObs(edjllForm, parentFormItem, attributes, node);
            }
        }
        return edjllObs;
    }

    createNode() {
        const node = document.createElement('div');
        node.classList.add('edjll-obs');

        return node;
    }

    createBody() {
        const body = document.createElement('div');
        body.classList.add('edjll-obs__body');

        return body;
    }

    createButtonAddItem() {
        const button = document.createElement('div');
        button.classList.add('edjll-obs__add-row');

        for (let i = 0; i <  this.attributes.patterns.createItem.length; i++) {
            const pc = this.attributes.patterns.createItem[i];

            const wrapper = document.createElement('div');
            wrapper.classList.add('edjll-obs__add-row__item');

            const item = document.createElement('div');
            item.classList.add('edjll-input-style');

            const itemText = document.createElement('span');
            itemText.classList.add('edjll-input-style__label__text');

            try {
                itemText.textContent = pc.settings.attributes.label;
            } catch (ex) { }

            item.append(itemText);
            wrapper.append(item);
            button.append(wrapper);
        }

        button.onclick = () => {
            const createdItem = this.createPatternItem();
            this.addItem(createdItem);
            this.body.lastElementChild.before(createdItem.node);
        }

        return button;
    }

    createPatternItem() {
        const edjllFormGroup = new EdjllFormGroup(this.edjllForm, this, this.attributes.patterns.createWrapper.settings.attributes);
        edjllFormGroup.node.classList.add('edjll-obs__body__row');

        for (let i = 0; i <  this.attributes.patterns.createItem.length; i++) {
            const pc = this.attributes.patterns.createItem[i];
            let formItem = null;
            const wrapper = document.createElement('div');
            wrapper.classList.add('edjll-obs__body__row__item');

            try {
                pc.settings.attributes.label = null;
                switch (pc.type) {
                    case 'edjllSelect':
                        formItem = new EdjllSelect(this.edjllForm, edjllFormGroup, pc.settings.attributes, pc.settings.request, pc.settings.validation);
                        break;
                    case 'edjllInput':
                        formItem = new EdjllInput(this.edjllForm, edjllFormGroup, pc.settings.attributes, pc.settings.validation);
                        break;
                    case 'edjllFileInput':
                        formItem = new EdjllFileInput(this.edjllForm, edjllFormGroup, pc.settings.attributes, pc.settings.validation);
                        break;
                    case 'edjllTextarea':
                        formItem = new EdjllTextarea(this.edjllForm, edjllFormGroup, pc.settings.attributes, pc.settings.validation);
                        break;
                }

                if (formItem) {
                    wrapper.append(formItem.node);
                    edjllFormGroup.node.append(wrapper);
                    edjllFormGroup.addItem(formItem);
                }
            } catch (e) { }
        }

        return edjllFormGroup;
    }

    getValue() {
        const values = [];
        this.items.forEach(item => {
            values.push(item.getValue().object);
        });
        return {
            object: values,
            files: []
        };
    }
}