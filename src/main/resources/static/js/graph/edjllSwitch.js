class EdjllSwitch {
    constructor(action, container = null, titleText = null, leftIconSrc = null, rightIconSrc = null, icon = false) {
        this.action = action;
        this.icon = icon;
        this.target = this.createTarget(titleText, leftIconSrc, rightIconSrc);
        if (container) container.append(this.target.wrapper);
    }

    createTarget(titleText, leftIconSrc, rightIconSrc) {
        const wrapper = document.createElement('div');
        wrapper.classList.add('edjll-graph__controls__switch');

        if (titleText) {
            const title = document.createElement('span');
            title.classList.add('edjll-graph__controls__switch__title');
            title.textContent = titleText;
            wrapper.append(title);
        }

        const buttonWrapper = document.createElement('div');
        buttonWrapper.classList.add('edjll-graph__controls__switch__inner');

        let leftIcon, rightIcon;

        if (leftIconSrc && rightIconSrc) {
            if (this.icon) {
                leftIcon = document.createElement('i');
                leftIcon.classList.add(leftIconSrc);

                rightIcon = document.createElement('i');
                rightIcon.classList.add(rightIconSrc);
            } else {
                leftIcon = document.createElement('img');
                leftIcon.src = leftIconSrc;

                rightIcon = document.createElement('img');
                rightIcon.src = rightIconSrc;
            }
        }
        
        const button = document.createElement('div');
        button.classList.add('edjll-graph__controls__switch__button');
        button.onclick = () => this.click();

        if (leftIcon) buttonWrapper.append(leftIcon);
        buttonWrapper.append(button);
        if (rightIcon) buttonWrapper.append(rightIcon);

        wrapper.append(buttonWrapper);

        return {
            wrapper: wrapper,
            button: button
        };
    }

    click() {
        if (this.target.button.classList.contains('edjll-graph__controls__switch__button--active')) {
            this.target.button.classList.remove('edjll-graph__controls__switch__button--active');
        } else {
            this.target.button.classList.add('edjll-graph__controls__switch__button--active');
        }
        this.action();
    }
}