const forms = document.getElementsByName('createSupply');

const excludedValues = {
    product: []
}

const param = {
    body: 'supply',
    url: '/admin/supply/create',
    redirect: '/admin/supply/all',
    method: 'post',
}