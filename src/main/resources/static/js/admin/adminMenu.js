class AdminMenu {
    constructor(node) {
        this.items = []
        this.node = node;
        this.activeItem = null;
    }

    scan() {
        const items = this.node.getElementsByClassName('admin-menu__item');
        for (let i = 0; i < items.length; i++) {
            const item = items[i];
            const title = item.getElementsByClassName('admin-menu__item__title')[0];
            const list = item.getElementsByClassName('admin-menu__item__list')[0];
            const inner = list ? list.getElementsByClassName('admin-menu__item__list__inner')[0] : null;
            this.items.push(
                {
                    node: item,
                    title: title,
                    list: {
                        node: list,
                        inner: inner
                    }
                }
            );
            item.onclick = () => this.click(i, this);
        }
    }

    click(index, adminMenu) {
        const item = adminMenu.items[index];
        if (this.activeItem) {
            this.activeItem.node.classList.remove('admin-menu__item--active');
            if (this.activeItem.list.node) {
                this.activeItem.list.node.style = '0px';
            }
        }
        if (this.activeItem === item) {
            this.activeItem = null;
            return;
        }
        item.node.classList.add('admin-menu__item--active');
        if (item.list.node) {
            item.list.node.style.height = `${item.list.inner.offsetHeight}px`;
        }
        this.activeItem = item;
    }
}