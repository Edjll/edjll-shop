const forms = document.getElementsByName('changePassword');

const param = {
    body: 'user',
    url: '/user/profile/password/update',
    redirect: '/user/profile',
    method: 'post',
}