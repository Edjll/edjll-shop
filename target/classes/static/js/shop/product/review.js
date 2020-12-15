const rating = document.getElementsByClassName('review-rating')[0];

if (rating) {
    let edjllInput = edjllForms[0].searchFormItemByName('rating');
    if (edjllInput == null) {
        edjllInput = new EdjllInput(edjllForms[0], edjllForms[0], {name: "rating", type: "hidden", defaultValue:5});
        edjllForms[0].items.push(edjllInput);
    }
    let max = 5;
    let checked = false;
    for (let i = 0; i < rating.childElementCount; i++) {
        rating.children[i].onclick = () => {
            for (let child of rating.children) { child.classList.remove('current') }
            if (!rating.children[i].classList.contains('current')) {
                rating.children[i].classList.add('current');
            }
            edjllInput.setValue(max - i);
        }
        if (rating.children[i].classList.contains('current')) {
            checked = true;
        }
    }
    if (!checked && !rating.children[0].classList.contains('current')) {
        rating.children[0].classList.add('current');
    }
}