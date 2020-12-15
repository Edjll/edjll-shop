class EdjllToast {
    constructor(toastEvent, title, body, timeToRemove) {
        this.timer = new EdjllTimer();
        this.timeToRemove = timeToRemove;
        this.toastEvent = toastEvent;

        this.parent = document.getElementsByClassName('edjll-toasts')[0];
        if (!this.parent) {
            this.parent = this.createParent();
            document.body.append(this.parent);
        }

        this.node = this.createNode();

        this.header = this.createHeader();

        this.headerTitleWrapper = this.createHeaderInner();
        this.headerEvent = this.createHeaderEvent();
        this.headerTitle = this.createHeaderTitle(title);
        this.headerTitleWrapper.append(this.headerEvent, this.headerTitle);


        this.headerTimerWrapper = this.createHeaderInner();
        this.headerTimer = this.createHeaderTimer();
        this.headerCloseButton = this.createHeaderCloseButton();
        this.headerTimerWrapper.append(this.headerTimer, this.headerCloseButton);

        this.header.append(this.headerTitleWrapper, this.headerTimerWrapper);

        this.body = this.createBody(body);

        this.node.append(this.header, this.body);

        this.parent.append(this.node);

        let delta = (this.node.offsetTop - this.parent.scrollTop) / 10;
        const scroll = setInterval(() => {
            this.parent.scrollTo(0, this.parent.scrollTop + delta);
        }, 50);
        setTimeout(() => clearInterval(scroll), 500);

        this.interval = this.startTimer();
    }

    createParent() {
        const parent = document.createElement('div');
        parent.classList.add('edjll-toasts');

        return parent;
    }

    createNode() {
        const node = document.createElement('div');
        node.classList.add('edjll-toasts__item');

        return node;
    }

    createHeader() {
        const header = document.createElement('div');
        header.classList.add('edjll-toasts__item__header');

        return header;
    }

    createHeaderInner() {
        const headerInner = document.createElement('div');
        headerInner.classList.add('edjll-toasts__item__header__inner');

        return headerInner;
    }

    createHeaderEvent() {
        const headerEvent = document.createElement('div');
        headerEvent.classList.add('edjll-toasts__item__header__event', 'edjll-toasts__item__header__event--' + this.toastEvent);

        return headerEvent;
    }

    createHeaderTitle(title) {
        const headerTitle = document.createElement('span');
        headerTitle.classList.add('edjll-toasts__item__header__title');
        headerTitle.textContent = title;

        return headerTitle;
    }

    createHeaderTimer() {
        const headerTimer = document.createElement('span');
        headerTimer.classList.add('edjll-toasts__item__header__timer');

        return headerTimer;
    }

    createHeaderCloseButton() {
        const headerCloseButton = document.createElement('button');
        headerCloseButton.classList.add('edjll-toasts__item__header__close-button');
        headerCloseButton.onclick = this.close.bind(this);

        const headerCloseButtonIcon = document.createElement('i');
        headerCloseButtonIcon.classList.add('ion-close-round', 'edjll-toasts__item__header__close-button__icon');

        headerCloseButton.append(headerCloseButtonIcon);

        return headerCloseButton;
    }

    createBody(content) {
        const body = document.createElement('div');
        body.classList.add('edjll-toasts__item__body');
        body.textContent = content;

        return body;
    }

    startTimer() {
        this.updateHeaderTimer();
        return setInterval(() => {
            if (new Date().getTime() - this.timer.value >= this.timeToRemove) {
                this.close();
            } else {
                this.updateHeaderTimer();
            }
        }, 1000);
    }

    close() {
        clearInterval(this.interval);
        this.node.remove();
    }

    updateHeaderTimer() {
        this.headerTimer.textContent = this.timer.parseToString();
    }
}