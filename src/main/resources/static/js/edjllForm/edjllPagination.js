class EdjllPagination {
    constructor(
        clickAction,
        page = 0,
        totalPages = 0,
        pageSize = 2,
        visibleLinksNumber = 3
    ) {
        this.page = page;
        this.totalPages = totalPages;
        this.pageSize = pageSize;
        this.visibleLinksNumber = visibleLinksNumber;
        this.clickAction = clickAction;
        this.node = this.createNode();

        this.previousPageLink = this.createPageLink(-1);
        this.previousPageLink.firstElementChild.textContent = '◁';
        this.node.append(this.previousPageLink);

        this.nextPageLink = this.createPageLink(-1);
        this.nextPageLink.firstElementChild.textContent = '▷';
        this.node.append(this.nextPageLink);

        this.links = [];

        if (this.totalPages) this.pageLinksUpdate();
    }

    createNode() {
        const node = document.createElement('ul');
        node.classList.add('edjll-pagination');

        return node;
    }

    createPageLink(page = 0) {
        const linkWrapper = document.createElement('li');
        linkWrapper.classList.add('edjll-pagination__item');
        if (this.page === page) linkWrapper.classList.add('edjll-pagination__item-active');

        const link = document.createElement('a');
        link.href = `#page=${page}`;
        link.textContent = String(page + 1);
        link.classList.add('edjll-pagination__item__link');
        link.onclick = () => this.pageLinkClick(link);

        linkWrapper.append(link);

        return linkWrapper;
    }

    pageLinkClick(link) {
        const page = Number(link.href.match(/(?<=(#page=))\d+/)[0]);
        if (page !== this.page) {
            if (this.clickAction) {
                this.clickAction(page)
                    .then(
                        res => {
                            if (res !== null) {
                                if (res.totalPages !== null) this.totalPages = res.totalPages;
                                this.updatePage(res.page);
                            }
                        }
                    );
            }
        }
    }

    updatePage(page, totalPages = null) {
        if (totalPages !== null) this.totalPages = totalPages;
        this.page = page;
        this.pageLinksUpdate();
    }

    removePageLink(pageLink) {
        pageLink.firstElementChild.remove();
        pageLink.remove();
    }

    pageLinksUpdate() {
        let visibleLinksNumber = this.totalPages < this.visibleLinksNumber ? this.totalPages : this.visibleLinksNumber;
        if (visibleLinksNumber === 0) visibleLinksNumber++;
        let firstPageIndex, lastPageIndex, pageIndex;

        if (this.page < Math.floor((visibleLinksNumber - 1) / 2)) {
            firstPageIndex = 0;
            lastPageIndex = visibleLinksNumber - 1;
        } else if (this.totalPages - 1 - this.page < Math.floor((visibleLinksNumber - 1) / 2)) {
            lastPageIndex = this.totalPages - 1 >= 0 ? this.totalPages - 1 : 0;
            firstPageIndex = this.totalPages - visibleLinksNumber >= 0 ? this.totalPages - visibleLinksNumber : 0;
        } else {
            if (visibleLinksNumber % 2) {
                firstPageIndex = this.page - Math.floor((visibleLinksNumber - 1) / 2);
                lastPageIndex = this.page + Math.floor((visibleLinksNumber - 1) / 2);
            } else {
                if (this.page === this.totalPages - 1) {
                    firstPageIndex = this.page - Math.ceil((visibleLinksNumber - 1) / 2);
                    lastPageIndex = this.page;
                } else {
                    firstPageIndex = this.page;
                    lastPageIndex = this.page + Math.ceil((visibleLinksNumber - 1) / 2);
                }
            }
        }

        pageIndex = lastPageIndex;

        if (this.links.length < visibleLinksNumber) {
            const deltaLinks = visibleLinksNumber - this.links.length;
            for (let i = deltaLinks - 1; i >= 0; i--) {
                const pageLink = this.createPageLink(lastPageIndex - i);
                this.links.push(pageLink);
                this.node.lastElementChild.before(pageLink);
                pageIndex--;
            }
        } else if (this.links.length > visibleLinksNumber) {
            const deltaLinks = this.links.length - visibleLinksNumber;
            for (let i = 0; i < deltaLinks; i++) {
                this.removePageLink(this.links.pop());
            }
        }

        for (let i = pageIndex; i >= firstPageIndex; i--) {
            this.pageLinkUpdate(this.links[i - firstPageIndex], i);
        }

        this.previousPageLinkUpdate();
        this.nextPageLinkUpdate();
    }

    pageLinkUpdate(pageLink, page) {
        if (page === this.page) {
            if (!pageLink.classList.contains('edjll-pagination__item-active')) {
                pageLink.classList.add('edjll-pagination__item-active');
            }
        } else {
            pageLink.classList.remove('edjll-pagination__item-active');
        }
        pageLink.firstElementChild.href = `#page=${page}`;
        pageLink.firstElementChild.textContent = page + 1;
    }

    previousPageLinkUpdate() {
        if (this.page === 0) {
            if (!this.previousPageLink.classList.contains('edjll-pagination__item-disabled')) {
                this.previousPageLink.classList.add('edjll-pagination__item-disabled');
            }
        } else {
            this.previousPageLink.classList.remove('edjll-pagination__item-disabled');
        }
        this.previousPageLink.firstElementChild.href = `#page=${this.page > 0 ? this.page - 1 : this.page}`;
    }

    nextPageLinkUpdate() {
        if (this.page >= this.totalPages - 1) {
            if (!this.nextPageLink.classList.contains('edjll-pagination__item-disabled')) {
                this.nextPageLink.classList.add('edjll-pagination__item-disabled');
            }
        } else {
            this.nextPageLink.classList.remove('edjll-pagination__item-disabled');
        }
        this.nextPageLink.firstElementChild.href = `#page=${this.page >= this.totalPages - 1 ? this.page : this.page + 1}`;
    }
}