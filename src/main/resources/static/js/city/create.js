const forms = document.getElementsByName('createCity');

const param = {
    body: 'city',
    url: '/admin/city/create',
    redirect: '/admin/city/all',
    method: 'post',
}