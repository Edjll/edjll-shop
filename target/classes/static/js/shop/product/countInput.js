const productAction = document.getElementsByClassName('product-action')[0];
const edjllInputCount = productAction ? productAction.getElementsByClassName('edjll-input')[0] : null;
const edjllInput = EdjllInput.create(edjllInputCount);
edjllInput.input.onchange = () => { };
const count = document.getElementsByName('count')[0];