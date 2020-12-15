const forms = document.getElementsByName('updateStorage');

const param = {
    body: 'storage',
    url: '/admin/storage/update',
    redirect: '/admin/storage/all',
    method: 'post',
}