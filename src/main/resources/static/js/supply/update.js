const forms = document.getElementsByName('updateSupply');

const param = {
    body: 'supply',
    url: '/admin/supply/update',
    redirect: '/admin/supply/all',
    method: 'post',
}