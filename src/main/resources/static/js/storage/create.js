const forms = document.getElementsByName('createStorage');

const param = {
    body: 'storage',
    url: '/admin/storage/create',
    redirect: '/admin/storage/all',
    method: 'post',
}