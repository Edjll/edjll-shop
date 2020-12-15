const productCategoryInput = document.getElementsByName('category')[0];
const productCategoryText = document.getElementById('category');
const productCategorySelect = document.getElementsByClassName('product-category-select')[0];
const productCategorySelectButton = document.getElementsByClassName('product-category-select-button')[0];

productCategorySelectButton.onclick = () => {
    if (productCategorySelect.classList.contains('hidden')) {
        productCategorySelect.classList.remove('hidden');
        productCategorySelectButton.classList.add('rotate');
    } else {
        productCategorySelect.classList.add('hidden');
        productCategorySelectButton.classList.remove('rotate');
    }
}

function nextLevel(id, target) {
    const request = new XMLHttpRequest();
    request.open('GET', `/category/${id}/children`, true);
    request.onload = () => {
        const categories = JSON.parse(request.response);
        if (categories.length) {
            changeNextLevel(target, categories);
            productCategoryInput.value = "";
            productCategoryText.textContent = "";
        } else {
            productCategoryInput.value = id;
            productCategoryText.textContent = target.textContent;
            productCategorySelectButton.click();
        }
    }
    request.send();
}

function createNextLevelBody(categories) {
    const div = document.createElement('div');
    div.classList.add('product-category-select-body-level', 'current');

    categories.forEach(category => {
        const span = document.createElement('span');
        span.classList.add('product-category-select-body-element');
        span.textContent = category.name;
        span.onclick = () => nextLevel(category.id, span);
        div.appendChild(span);
    });

    return div;
}

function createNextLevelHeader() {
    const span = document.createElement('span');
    span.classList.add('product-category-select-header-element', 'current');
    span.textContent = document.getElementsByClassName('product-category-select-header')[0].children.length + ' уровень';
    span.onclick = () => changeLevel(span);
    return span;
}

function changeNextLevel(target, categories) {
    const index = getIndex(target.parentNode);
    const headerTarget = document.getElementsByClassName('product-category-select-header')[0].children.item(index);
    while (headerTarget.nextElementSibling) {
        headerTarget.nextElementSibling.remove();
    }
    headerTarget.after(createNextLevelHeader());
    headerTarget.classList.remove('current');
    const currentLevel = target.parentNode;
    while (currentLevel.nextElementSibling) {
        currentLevel.nextElementSibling.remove();
    }
    currentLevel.after(createNextLevelBody(categories));
    currentLevel.classList.remove('current');
    currentLevel.classList.add('previous');
}

function changeLevel(target) {
    const currentHeader = document.getElementsByClassName('product-category-select-header-element current')[0];
    const currentIndex = getIndex(currentHeader);
    const newIndex = getIndex(target);
    const bodies = document.getElementsByClassName('product-category-select-body-level');
    const currentBody = bodies.item(currentIndex);
    const newBody = bodies.item(newIndex);

    if (currentIndex > newIndex) {
        let elementBody = newBody.nextElementSibling;
        while (elementBody) {
            if (!elementBody.classList.contains('next')) {
                elementBody.classList.add('next');
            }
            elementBody.classList.remove('previous');
            elementBody = elementBody.nextElementSibling;
        }
        newBody.classList.remove('previous');
    } else if (currentIndex < newIndex) {
        let elementBody = newBody.previousElementSibling;
        while (elementBody) {
            if (!elementBody.classList.contains('previous')) {
                elementBody.classList.add('previous');
            }
            elementBody.classList.remove('next');
            elementBody = elementBody.previousElementSibling;
        }
        newBody.classList.remove('next');
    }

    currentHeader.classList.remove('current');
    target.classList.add('current');

    currentBody.classList.remove('current');
    newBody.classList.add('current');

}

function getIndex(target) {
    let index = 0;
    let element = target;
    while (element.previousElementSibling) {
        element = element.previousElementSibling;
        index++;
    }
    return index;
}