const forms = document.getElementsByName('deleteManufacturer');

const param = {
    body: 'manufacturer',
    url: '/admin/manufacturer/delete',
    redirect: '/admin/manufacturer/all',
    method: 'post',
}