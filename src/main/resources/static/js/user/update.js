const forms = document.getElementsByName('updateUser');

const param = {
    body: 'user',
    url: '/user/profile/update',
    redirect: '/user/profile',
    method: 'post',
}