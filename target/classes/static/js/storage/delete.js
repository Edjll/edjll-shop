const forms = document.getElementsByName('deleteStorage');

const param = {
    body: 'storage',
    url: '/admin/storage/delete',
    redirect: '/admin/storage/all',
    method: 'post',
}