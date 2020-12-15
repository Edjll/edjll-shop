const forms = document.getElementsByName('updateCity');

const param = {
    body: 'city',
    url: '/admin/city/update',
    redirect: '/admin/city/all',
    method: 'post',
}