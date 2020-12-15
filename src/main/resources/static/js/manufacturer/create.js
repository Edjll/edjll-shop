const forms = document.getElementsByName('createManufacturer');

const param = {
    body: 'manufacturer',
    url: '/admin/manufacturer/create',
    redirect: '/admin/manufacturer/all',
    method: 'post',
}