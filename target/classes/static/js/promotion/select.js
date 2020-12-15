class Select {
    constructor(name, parentNode, url, csrf, values) {
        this.body = this.createBody(parentNode);
        this.body.data = {
            url: url,
            page: 0,
            countLinks: 2,
            csrf: csrf
        };
        this.values = values;
        this.name = name;
        this.value = null;
    }

    createBody(parentNode) {
        const object = document.createElement('div');
        object.classList.add('select');

        const content = document.createElement('div');
        content.classList.add('select__content');

        const contentSpinner = document.createElement('div');
        contentSpinner.classList.add('select__content__spinner');

        const contentItems = document.createElement('div');
        contentItems.classList.add('select__content__items');

        const pagination = document.createElement('ul');
        pagination.classList.add('pagination');

        const previousLink = this.createPageLink(-2);
        const nextLink = this.createPageLink(-1);
        pagination.append(previousLink.object, nextLink.object);

        content.append(contentSpinner, contentItems, pagination);

        const input = document.createElement('div');
        input.classList.add('select__input');

        const inputProduct = document.createElement('input');
        inputProduct.type = 'hidden';
        inputProduct.name = 'product';

        const inputSearch = document.createElement('input');
        inputSearch.type = 'text';
        inputSearch.name = 'searchParam';
        inputSearch.oninput = () => this.inputChange();

        const button = document.createElement('div');
        button.classList.add('select__button');
        button.onclick = () => this.buttonClick();

        input.append(inputProduct, inputSearch, button);

        object.append(content, input);

        parentNode.append(object);

        return {
            object: object,
            content: {
                object: content,
                spinner: {
                    object: contentSpinner
                },
                items: {
                    object: contentItems,
                    values: []
                },
                pagination: {
                    object: pagination,
                    items: [],
                    previousLink: previousLink,
                    nextLink: nextLink
                }
            },
            input: {
                object: input,
                product: inputProduct,
                search: inputSearch
            },
            button: button
        };
    }

    buttonClick() {
        if (this.changeContentVisibility()) {
            this.changePage()
                .then(
                    data => this.createPageLinks()
                );
        }
    }

    inputChange() {
        if (this.value) {
            this.values.splice(this.values.indexOf(this.value), 1);
            this.value = null;
        }
        if (this.changeContentVisibility(true)) {
            this.changePage()
                .then(
                    data => {
                        this.clearPageLinks();
                        this.createPageLinks();
                    }
                );
        }
    }

    changeContentVisibility(requiredVisible = false) {
        if (requiredVisible){
            if (!this.body.object.classList.contains('select-active')) {
                this.openContent();
                return true;
            }
        } else {
            if (this.body.object.classList.contains('select-active')) {
                this.closeContent();
                this.clearPageLinks();
                return false;
            } else {
                this.openContent();
                return true;
            }
        }
        return true;
    }

    closeContent() {
        this.body.object.classList.remove('select-active');
    }

    openContent() {
        if (!this.body.object.classList.contains('select-active')) {
            this.body.object.classList.add('select-active');
        }
    }

    changePage(page = 0) {
        return this.getData(page)
            .then(
                data => {
                    this.clearContent();
                    this.body.data.page = data.pageable.pageNumber;
                    this.body.data.totalPages = data.totalPages;
                    data.content.forEach(item => {
                        const contentItem = this.createContentItem(item);
                        this.body.content.items.object.append(contentItem);
                        this.body.content.items.values.push(contentItem);
                    });
                    return true;
                }
            );
    }

    getData(page) {
        return new Promise((resolve, reject) => {
            let url = new URL(this.body.data.url);
            url.searchParams.set('page', page);
            url.searchParams.set('searchParam', this.body.input.search.value);
            let products;
            if (this.value) {
                products = [...this.values];
                products.splice(this.values.indexOf(this.value), 1);
            } else {
                products = this.values;
            }
            url.searchParams.set('products', products);

            const request = new XMLHttpRequest();
            request.open('get', url.href, true);
            request.setRequestHeader('Content-Type', 'application/json');
            request.setRequestHeader(this.body.data.csrf.header, this.body.data.csrf.value);
            request.onload = () => {
                if (request.status === 200) resolve(JSON.parse(request.response));
                else reject(new Error('bad request'));
            };
            request.send();
        });
    }

    createPageLinks() {
        this.validatePreviousAndNextLinks();

        const indexes = this.getPageLinkIndexes();

        for (let i = indexes.firstIndex; i <= indexes.lastIndex; i++) {
            if (i < 0 || i > this.body.data.totalPages) continue;
            const pageLink = this.createPageLink(i);
            this.body.content.pagination.items.push(pageLink);
            this.body.content.pagination.object.lastChild.before(pageLink.object);
        }
    }

    clearPageLinks() {
        while (this.body.content.pagination.items.length) {
            this.body.content.pagination.items.pop().object.remove();
        }
    }

    getPageLinkIndexes() {
        let firstIndex, lastIndex;
        const deltaNext = this.body.data.totalPages - 1 - this.body.data.page;
        const countLinks = this.body.data.totalPages - 1 < this.body.data.countLinks ? this.body.data.totalPages - 1 : this.body.data.countLinks;
        if (deltaNext < countLinks / 2) {
            firstIndex = this.body.data.page - countLinks;
            lastIndex = this.body.data.totalPages - 1;
        } else if (this.body.data.page < countLinks / 2) {
            firstIndex = 0;
            lastIndex = countLinks - this.body.data.page;
        } else {
            firstIndex = this.body.data.page - Math.floor(countLinks / 2);
            lastIndex = this.body.data.page + Math.floor(countLinks / 2);
        }

        console.log({
            firstIndex: firstIndex,
            lastIndex: lastIndex
        });

        return {
            firstIndex: firstIndex,
            lastIndex: lastIndex
        };
    }

    createPageLink(index) {
        const item = document.createElement('li');
        item.classList.add('page-item');
        if (index === 0) item.classList.add('active');

        const link = document.createElement('a');
        link.classList.add('page-link');
        if (index === -2) {
            link.href = `#`;
            link.textContent = '<';
            item.classList.add('disabled');
        } else if (index === -1) {
            link.href = `#page=1`;
            link.textContent = '>';
        } else {
            link.href = `#page=${index}`;
            link.textContent = index + 1;
        }
        link.onclick = () => this.pageLinkClick(link);

        item.append(link);

        return {
            object: item,
            link: link
        };
    }

    pageLinkClick(link) {
        this.changePage(link.href.match(/(?<=(#page=))\d+/)[0])
            .then(
                data => this.changePageLinks()
            );
    }

    clearContent() {
        while (this.body.content.items.values.length) {
            this.body.content.items.values.pop().remove();
        }
    }

    createContentItem(data) {
        const item = document.createElement('div')
        item.classList.add('select__content__item');

        const id = document.createElement('span');
        id.textContent = data.id;
        item.onclick = () => this.contentItemClick(data);

        const name = document.createElement('span');
        name.textContent = data.name;

        item.append(id, name);

        return item;
    }

    contentItemClick(data) {
        this.body.input.search.value = data.name;
        this.body.input.product.value = data.id;
        this.body.button.click();
        this.value = data.id;
        if (this.values.indexOf(this.value) === -1) {
            this.values.push(this.value);
        }
    }

    changePageLink(pageLink, index, pageLinkIndex) {
        if (pageLinkIndex === this.body.data.page) {
            if (!pageLink.object.classList.contains('active')) {
                pageLink.object.classList.add('active');
            }
        } else {
            pageLink.object.classList.remove('active');
        }
        pageLink.link.href = `#page=${pageLinkIndex}`;
        pageLink.link.textContent = pageLinkIndex + 1;
    }

    validatePreviousAndNextLinks() {
        if (this.body.data.page === 0 ){
            if (!this.body.content.pagination.previousLink.object.classList.contains('disabled')) {
                this.body.content.pagination.previousLink.object.classList.add('disabled');
            }
        } else {
            this.body.content.pagination.previousLink.link.href = `#page=${this.body.data.page - 1}`;
            this.body.content.pagination.previousLink.object.classList.remove('disabled');
        }
        if (this.body.data.page === this.body.data.totalPages - 1) {
            if (!this.body.content.pagination.nextLink.object.classList.contains('disabled')) {
                this.body.content.pagination.nextLink.object.classList.add('disabled');
            }
        } else {
            this.body.content.pagination.nextLink.link.href = `#page=${this.body.data.page + 1}`;
            this.body.content.pagination.nextLink.object.classList.remove('disabled');
        }
    }

    changePageLinks() {
        let pageLinkIndex = this.getPageLinkIndexes().firstIndex;

        this.validatePreviousAndNextLinks();

        this.body.content.pagination.items.forEach((pageLink, index) => {
            this.changePageLink(pageLink, index, pageLinkIndex);
            pageLinkIndex++;
        });
    }
}