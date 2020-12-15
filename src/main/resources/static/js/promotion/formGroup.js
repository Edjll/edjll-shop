class FormGroup {
    constructor(name, parentNode, itemWrapper = null) {
        this.name = name;
        this.content = [];
        this.parentNode = parentNode;
        this.itemWrapper = itemWrapper;
    }

    createSelect(name, url, csrf, values) {
        let selectParentNode = this.parentNode;
        if (this.itemWrapper) {
            selectParentNode = this.itemWrapper();
            this.parentNode.append(selectParentNode);
        }
        const select = new Select(name, selectParentNode, url, csrf, values);
        this.content.push(select);
    }

    createInput(name, type = 'text') {
        const input = document.createElement('input');
        input.type = type;
        input.name = name;


        let inputParentNode = this.parentNode;
        if (this.itemWrapper) {
            inputParentNode = this.itemWrapper();
            this.parentNode.append(inputParentNode);
        }
        inputParentNode.append(input);


        this.content.push(input);
    }

    getContent() {
        const contentJSON = {}
        this.content.forEach(content => {
            contentJSON[content.name] = content.value;
        })
        return contentJSON;
    }
}