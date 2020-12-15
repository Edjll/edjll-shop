class Input {
    constructor(name, type) {
        this.name = name;
        this.type = type;
        this.value = null;
        this.body = {
            object: this.createBody()
        };
    }

    createBody() {
        const object = document.createElement('input');
        object.type = this.type;
        object.name = this.name;

        return object;
    }

}