const attributeSelect = document.getElementsByClassName('attribute-select')[0];
const attributeSelectButtons = document.getElementsByClassName('attribute-select-button');
const attributeAddButtons = document.getElementsByClassName('attribute-add-element');
const container = document.getElementsByClassName('attributes')[0];
const attributesSettings = document.getElementsByClassName('attributes-settings');

for (let i = 0; i < attributeSelectButtons.length; i++) {
    attributeSelectButtons[i].onclick = () => attributeSelectButtonClick(attributeSelectButtons[i]);
}

for (let i = 0; i < attributeAddButtons.length; i++) {
    attributeAddButtons[i].onclick = () => createAttribute(attributeAddButtons[i]);
}

container.onscroll = () => {
    attributesSettings[0].style.top = `${container.scrollTop - 40}px`;
}

attributesSettings[0].onclick = () => {
    if (container.classList.contains('fullscreen')) {
        container.classList.remove('fullscreen');
    } else {
        container.classList.add('fullscreen');
    }

}

function attributeSelectButtonClick(target) {
    if (attributeSelect.classList.contains('show')) {
        attributeSelect.classList.remove('show');
        container.classList.remove('overflow-hidden');
        target.classList.remove('rotate');
        if (target.parentNode.nextElementSibling) {
            target.parentNode.nextElementSibling.classList.remove('hidden');
        }
        target.parentNode.parentNode.classList.remove('p-0');
    } else {
        attributeSelect.style.top = `${target.parentNode.offsetTop + 39}px`;
        attributeSelect.classList.add('show');
        container.classList.add('overflow-hidden');
        target.classList.add('rotate');
        scrollElement(target.parentNode);
        if (target.parentNode.nextElementSibling) {
            target.parentNode.nextElementSibling.classList.add('hidden');
            target.parentNode.parentNode.classList.add('p-0');
        }
    }
}

function scrollElement(target) {
    container.scrollTo(0, target.offsetTop);
}

function createAttribute(target) {
    const attributeWrapper = document.createElement('div');
    attributeWrapper.classList.add('attribute-element');

    const nameWrapper = document.createElement('label');
    nameWrapper.classList.add('attribute-element-name');

    const nameInput = document.createElement('input');
    nameInput.type = 'text';
    nameInput.placeholder = 'Название характеристики';

    nameWrapper.append(nameInput, createSelectButton());
    attributeWrapper.appendChild(nameWrapper);

    const descriptionTextarea = document.createElement('textarea');
    descriptionTextarea.placeholder = 'Описание характеристики';
    descriptionTextarea.classList.add('attribute-element-description');
    attributeWrapper.appendChild(descriptionTextarea);

    const row = document.createElement('div');
    row.classList.add('attribute-element-row');

    const valueWrapper = document.createElement('label');
    valueWrapper.classList.add('attribute-element-value');

    const valueInput = document.createElement('input');
    valueInput.type = 'text';
    valueInput.placeholder = 'Значение характеристики';

    valueWrapper.append(valueInput, createSelectButton());
    row.appendChild(valueWrapper);

    const unitWrapper = document.createElement('label');
    unitWrapper.classList.add('attribute-element-unit');

    const unitInput = document.createElement('input');
    unitInput.type = 'text';
    unitInput.placeholder = 'Единицы измерения характеристики';

    unitWrapper.append(unitInput);
    row.appendChild(unitWrapper);

    attributeWrapper.appendChild(row);

    target.parentNode.before(attributeWrapper);
}

function createSelectButton() {
    const wrapper = document.createElement('div');
    wrapper.classList.add('attribute-select-button');
    wrapper.onclick = () => attributeSelectButtonClick(wrapper);

    const leftRow = document.createElement('div');
    const rightRow = document.createElement('div');

    wrapper.append(leftRow, rightRow);

    return wrapper;
}