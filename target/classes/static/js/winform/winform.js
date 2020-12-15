function searchNode(sourceNode, desiredNode) {
    let node = sourceNode;
    while (true) {
        if (node.classList.contains(desiredNode)) {
            return node;
        } else if (node.tagName == 'body') {
            return null;
        }
        node = node.parentNode;
    }
}

function getItemIndex(sourceNode) {
    let index = 0;
    let node = sourceNode;
    while (node.previousElementSibling) {
        index++;
        node = node.previousElementSibling;
    }
    return index;
}

function getCurrentIndex(sourceNode) {
    const parentNode = sourceNode.parentNode;
    const nodes = parentNode.children;
    for (let i = 0; i < nodes.length; i++) {
        const node = nodes[i];
        if (node.classList.contains('header-tags__item-current')) {
            return i;
        }
    }
    return 0;
}

const winforms = document.getElementsByClassName('winform');
const formGroupsDOM = document.getElementsByClassName('edjll-form-group');
const formGroups = [];
for (let i = 0; i < formGroupsDOM.length; i++) {
    if (formGroupsDOM[i].parentNode.closest('.edjll-form-group') !== null) {
        i++;
    } else {
        const formGroup = FormGroup.create(formGroupsDOM[i]);
        formGroup.scan();
        formGroups.push(formGroup);
    }
}

for (let i = 0; i < winforms.length; i++) {
    const winform = winforms[i];
    const winformTags = winform.getElementsByClassName('header-tag');
    const winformPages = winform.getElementsByClassName('winform-pages__item');
    const winformResizeButton = winform.getElementsByClassName('winform-resize')[0];
    const winformAddPageButton = winform.getElementsByClassName('winform-tag-add__button')[0];

    winformAddPageButton.onclick = () => winformAddPageButtonClick(winform);

    winformResizeButton.onclick = () => winformResizeButtonClick(winform, winformResizeButton);

    for (let j = 0; j < winformTags.length; j++) {
        const winformTag = winformTags[j];
        const winformTagCloseButton = winformTag.getElementsByClassName('header-tag__close-button')[0];
        const winformPage = winformPages[j];
        const winformPageHeaderInput = winformPage.getElementsByClassName('winform-page-header__input')[0].getElementsByTagName('input')[0];
        const winformPageHeader = winformPage.getElementsByClassName('winform-page__header')[0];
        const winformPageHeaderSelect = winformPage.getElementsByClassName('winform-page-header__select')[0];
        const winformPagesSelect = winform.getElementsByClassName('winform-pages__select')[0];
        const winformPageHeaderTags = winform.getElementsByClassName('winform-header__tags')[0];
        const winformPageContent = winformPage.getElementsByClassName('winform-page-content')[0];
        const winformPageAddButton = winformPage.getElementsByClassName('winform-page-add')[0];

        winformPageAddButton.onclick = () => winformPageAddButtonClick(winformPageContent);

        winformPageHeaderTags.onwheel = (e) => winformPageHeaderTagsWheel(winformPageHeaderTags, e);

        winformPageHeaderInput.oninput = () => winformPageHeaderInputOninput(winformTag, winformPageHeaderInput);

        winformPageHeaderSelect.onclick = () => winformPageHeaderSelectClick(winformPageHeader, winformPagesSelect);

        winformTagCloseButton.onclick = () => winformTagCloseButtonClick(winform, winformTag);
    
        winformTag.onclick = () => winformTagClick(winform, winformTag);
        
        const winformPageItems = winformPage.getElementsByClassName('winform-page-attribute__item');

        for (let k = 0; k < winformPageItems.length; k++) {
            const winformPageItem = winformPageItems[k];
            const winformPageAttributeItemSelectButton = winformPageItem.getElementsByClassName('winform-page-attribute__item__select-button')[0];
            const winformPageAttributeItemSelectContent = winformPageItem.getElementsByClassName('winform-page-attribute__item__select-content')[0];
            
            if (winformPageAttributeItemSelectButton && winformPageAttributeItemSelectContent) {
                winformPageAttributeItemSelectButton.onclick = () => {
                    if (winformPageAttributeItemSelectButton.classList.contains('winform-page-attribute__item__select-button-active')) {
                        winformPageAttributeItemSelectButton.classList.remove('winform-page-attribute__item__select-button-active');
                        winformPageAttributeItemSelectContent.classList.remove('winform-page-attribute__item__select-content-active');
                    } else {
                        winformPageAttributeItemSelectButton.classList.add('winform-page-attribute__item__select-button-active');
                        winformPageAttributeItemSelectContent.classList.add('winform-page-attribute__item__select-content-active');
                    }
                    
                }
            }
        }
    }
}

function winformPageAddButtonClick(winformPageContent) {
    const attributeWrapper = createNewWinformAttribute();
    winformPageContent.append(attributeWrapper);
    winformPageContent.scrollTo(0, attributeWrapper.offsetTop);
}

function winformPageHeaderSelectClick(winformPageHeader, winformPagesSelect) {
    if (winformPageHeader.classList.contains('winform-page__header-active')) {
        winformPageHeader.classList.remove('winform-page__header-active');
        winformPagesSelect.classList.remove('winform-pages__select-active');
    } else {
        winformPageHeader.classList.add('winform-page__header-active');
        winformPagesSelect.classList.add('winform-pages__select-active');
    }
}

function winformAddPageButtonClick(winform) {
    const newWinformTag = createWinformTag(winform);
    const newWinformPage = createNewWinformPage(winform);
    const winformTags = winform.getElementsByClassName('header-tag');
    const winformPages = winform.getElementsByClassName('winform-pages__item');
    winformTags[winformTags.length - 1].after(newWinformTag);
    winformPages[winformPages.length - 1].after(newWinformPage);
    newWinformTag.click();
}

function winformPageHeaderTagsWheel(winformPageHeaderTags, e) {
    winformPageHeaderTags.scrollTo(winformPageHeaderTags.scrollLeft - e.deltaY, 0);
}

function winformResizeButtonClick(winform, winformResizeButton) {
    if (winformResizeButton.classList.contains('winform-resize-active')) {
        winformResizeButton.classList.remove('winform-resize-active');
        winform.classList.remove('winform-fullscreen');
        document.body.classList.remove('body-overflow-hidden');
    } else {
        winformResizeButton.classList.add('winform-resize-active');
        winform.classList.add('winform-fullscreen');
        document.body.classList.add('body-overflow-hidden');
    }
}

function winformPageHeaderInputOninput(winformTag, winformPageHeaderInput) {
    const winformTagText = winformTag.getElementsByClassName('header-tag__text')[0];
    let text = winformPageHeaderInput.value;

    if (text === '') {
        text = 'Категория';
    }

    winformTagText.textContent = text;
}

function winformTagClick(winform, winformTag) {
    const index = getItemIndex(winformTag);
    const currentIndex = getCurrentIndex(winformTag);
    const winformTags = winform.getElementsByClassName('header-tag');
    const winformPages = winform.getElementsByClassName('winform-pages__item');
    const winformPagesSelect = winform.getElementsByClassName('winform-pages__select')[0];

    if (currentIndex === index) return;

    if (winformPagesSelect.classList.contains('winform-pages__select-active')) {
        const winformPage = winformPages.item(currentIndex);
        const winformPageHeader = winformPage.getElementsByClassName('winform-page__header')[0];
        winformPageHeaderSelectClick(winformPageHeader, winformPagesSelect);
    }

    winformTag.parentNode.scrollTo(winformTag.offsetLeft, 0);

    if (currentIndex < index) {
        for (let k = currentIndex; k < index; k++) {
            const wp = winformPages[k];

            wp.classList.remove('winform-pages__item-next');
            wp.classList.add('winform-pages__item-previous');
        }
        winformPages.item(index).classList.remove('winform-pages__item-next');
    } else if (currentIndex > index) {
        for (let k = currentIndex; k > index; k--) {
            const wp = winformPages[k];

            wp.classList.remove('winform-pages__item-previous');
            wp.classList.add('winform-pages__item-next');
        }
        winformPages.item(index).classList.remove('winform-pages__item-previous');
    }

    winformTags.item(currentIndex).classList.remove('header-tags__item-current');
    winformTags.item(index).classList.add('header-tags__item-current');

    winformPages.item(currentIndex).classList.remove('winform-pages__item-current');
    winformPages.item(currentIndex).classList.add('winform-pages__item-visible');
    setTimeout(() => {
        winformPages.item(currentIndex).classList.remove('winform-pages__item-visible');
    }, 300);
    winformPages.item(index).classList.add('winform-pages__item-current');
}

function winformTagCloseButtonClick(winform, winformTag) {
    winformTag.onclick = null;
    const index = getItemIndex(winformTag);
    const winformTagNext = (winformTag.previousElementSibling == null) ? winformTag.nextElementSibling : winformTag.previousElementSibling;
    const winformPages = winform.getElementsByClassName('winform-pages__item');
    const winformPageNext = winformPages.item(getItemIndex(winformTagNext));
    
    winformTag.remove();
    winformPages.item(index).remove();
    
    if (winformTag.classList.contains('header-tags__item-current')) {
        winformTagNext.classList.add('header-tags__item-current');
        winformPageNext.classList.remove('winform-pages__item-previous');
        winformPageNext.classList.remove('winform-pages__item-next');
        winformPageNext.classList.add('winform-pages__item-current');
    }
    
}

function createWinformTag(winform) {
    const headerTag = document.createElement('div');
    headerTag.classList.add('header-tags__item', 'header-tag');
    headerTag.onclick = () => winformTagClick(winform, headerTag);

    const headerTagText = document.createElement('span');
    headerTagText.classList.add('header-tag__text');
    headerTagText.textContent = 'Категория';

    const headerTagCloseButton = document.createElement('div');
    headerTagCloseButton.classList.add('header-tag__close-button');
    headerTagCloseButton.onclick = () => winformTagCloseButtonClick(winform, headerTag);

    headerTag.append(headerTagText, headerTagCloseButton);

    return headerTag;
}

function createNewWinformPage(winform) {
    const winformPagesSelect = winform.getElementsByClassName('winform-pages__select')[0];

    const page = document.createElement('div');
    page.classList.add('winform-pages__item', 'winform-page', 'winform-pages__item-next');

    const pageHeader = document.createElement('div');
    pageHeader.classList.add('winform-page__header', 'winform-page-header');

    const pageHeaderInner = document.createElement('div');
    pageHeaderInner.classList.add('winform-page-header__inner');

    const pageHeaderInput = document.createElement('div');
    pageHeaderInput.classList.add('winform-page-header__input');

    pageHeaderInputItem = document.createElement('input');

    const pageHeaderSelect = document.createElement('div');
    pageHeaderSelect.classList.add('winform-page-header__select');
    pageHeaderSelect.onclick = () => winformPageHeaderSelectClick(pageHeader, winformPagesSelect);

    pageHeaderInput.append(pageHeaderInputItem);
    pageHeaderInner.append(pageHeaderInput, pageHeaderSelect);
    pageHeader.append(pageHeaderInner);

    const pageContent = document.createElement('div');
    pageContent.classList.add('winform-page__content', 'winform-page-content');
    pageContent.append(createNewWinformAttribute());

    const pageAdd = document.createElement('div');
    pageAdd.classList.add('winform-page__add', 'winform-page-add');

    const pageAddButton = document.createElement('div');
    pageAddButton.classList.add('winform-page-add__button');
    pageAddButton.onclick = () => winformPageAddButtonClick(pageContent);

    pageAdd.append(pageAddButton);

    page.append(pageHeader, pageContent, pageAdd);

    return page;
}

function createNewWinformAttribute() {
    const formGroup = new FormGroup();
    const attributeWrapper = document.createElement('div');
    attributeWrapper.classList.add('winform-page-content__item', 'winform-page-attribute', 'form-group');

    const attributeNameWrapper = document.createElement('div');
    attributeNameWrapper.classList.add('winform-page-attribute__item', 'winform-attribute-name');

    const attributeNameWrapperSelectContent = document.createElement('div');
    attributeNameWrapperSelectContent.classList.add('winform-page-attribute__item__select-content');

    const attributeNameLabel = document.createElement('label');
    attributeNameLabel.classList.add('winform-attribute-name__inner');

    const attributeNameTitle = document.createElement('span');
    attributeNameTitle.classList.add('winform-attribute-name__title');
    attributeNameTitle.textContent = 'Название';

    const attributeNameInputWrapper = new Select('name', [{id: 1, name: 'name1'},{id: 2, name: 'name2'},{id: 3, name: 'name3'},{id: 4, name: 'name4'}]);
    formGroup.addItem(attributeNameInputWrapper);

    attributeNameLabel.append(attributeNameTitle, attributeNameInputWrapper.node);
    attributeNameWrapper.append(attributeNameWrapperSelectContent, attributeNameLabel);

    const attributeDescriptionWrapper = document.createElement('div');
    attributeDescriptionWrapper.classList.add('winform-page-attribute__item', 'winform-attribute-description');

    const attributeDescriptionLabel = document.createElement('label');
    attributeDescriptionLabel.classList.add('winform-attribute-description__inner');

    const attributeDescriptionTitle = document.createElement('span');
    attributeDescriptionTitle.classList.add('winform-attribute-description__title');
    attributeDescriptionTitle.textContent = 'Описание';

    const attributeDescriptionTextarea = new Textarea('description');
    formGroup.addItem(attributeDescriptionTextarea);

    attributeDescriptionLabel.append(attributeDescriptionTitle, attributeDescriptionTextarea.node);
    attributeDescriptionWrapper.append(attributeDescriptionLabel);

    const attributeWrapperInner = document.createElement('div');
    attributeWrapperInner.classList.add('winform-page-attribute__inner');

    const attributeValueWrapper = document.createElement('div');
    attributeValueWrapper.classList.add('winform-page-attribute__item', 'winform-attribute-value');

    const attributeValueWrapperSelectContent = document.createElement('div');
    attributeValueWrapperSelectContent.classList.add('winform-page-attribute__item__select-content');

    const attributeValueLabel = document.createElement('label');
    attributeValueLabel.classList.add('winform-attribute-value__inner');

    const attributeValueTitle = document.createElement('span');
    attributeValueTitle.classList.add('winform-attribute-value__title');
    attributeValueTitle.textContent = 'Значение';

    const attributeValueInputWrapper = new Select('value', [{id: 1, name: 'name1'},{id: 2, name: 'name2'},{id: 3, name: 'name3'},{id: 4, name: 'name4'}]);
    formGroup.addItem(attributeValueInputWrapper);

    attributeValueLabel.append(attributeValueTitle, attributeValueInputWrapper.node);
    attributeValueWrapper.append(attributeValueWrapperSelectContent, attributeValueLabel);

    const attributeUnitWrapper = document.createElement('div');
    attributeUnitWrapper.classList.add('winform-page-attribute__item', 'winform-attribute-unit');

    const attributeUnitLabel = document.createElement('label');
    attributeUnitLabel.classList.add('winform-attribute-unit__inner');

    const attributeUnitTitle = document.createElement('span');
    attributeUnitTitle.classList.add('winform-attribute-unit__title');
    attributeUnitTitle.textContent = 'Единицы измерения';

    const attributeUnitInput = new Input('unit');
    formGroup.addItem(attributeUnitInput);

    attributeUnitLabel.append(attributeUnitTitle, attributeUnitInput.node);
    attributeUnitWrapper.append(attributeUnitLabel);

    attributeWrapperInner.append(attributeValueWrapper, attributeUnitWrapper);
    attributeWrapper.append(attributeNameWrapper, attributeDescriptionWrapper, attributeWrapperInner);

    return attributeWrapper;
}

function loadTagSelectItems() {
    const request = new XMLHttpRequest();
    request.open('get', '/admin/rest/attributeCategories', true);
    request.onload = () => {
        console.log(request.response);
    };
    request.send();
}

function parseWinform() {
    const winform = document.getElementsByClassName('winform')[0];
    const winformPages = winform.getElementsByClassName('winform-page');
    const attributes = {
        attributeCategories: []
    };
    for (let i = 0; i < winformPages.length; i++) {
        const winformPage = winformPages[i];
        const winformPageHeader = winformPage.getElementsByClassName('winform-page-header__input-value')[0];
        const winformPageItems = winformPage.getElementsByClassName('winform-page-content__item');
        attributes.attributeCategories[i] = {
            attributeCategoryName: winformPageHeader.value,
            attributes: []
        };
        for (let j = 0; j < winformPageItems.length; j++) {
            const winformPageItem = winformPageItems[j];
            const winformPageItemName = winformPageItem.getElementsByClassName('winform-attribute-name__input-value')[0];
            const winformPageItemDescription = winformPageItem.getElementsByClassName('winform-attribute-description__input')[0];
            const winformPageItemValue = winformPageItem.getElementsByClassName('winform-attribute-value__input-value')[0];
            const winformPageItemUnit = winformPageItem.getElementsByClassName('winform-attribute-unit__input')[0];
            attributes.attributeCategories[i].attributes[j] = {
                attributeName: winformPageItemName.value,
                attributeDescription: winformPageItemDescription.value,
                attributeValue: winformPageItemValue.value,
                attributeUnit: winformPageItemUnit.value
            };
        }
    }
    return attributes;
}