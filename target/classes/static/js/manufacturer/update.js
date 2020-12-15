const forms = document.getElementsByName('updateManufacturer');

const param = {
    body: 'manufacturer',
    url: '/admin/manufacturer/update',
    redirect: '/admin/manufacturer/all',
    method: 'post',
}