const forms = document.getElementsByName('deleteCity');

const param = {
    body: 'city',
    url: '/admin/city/delete',
    redirect: '/admin/city/all',
    method: 'post',
}