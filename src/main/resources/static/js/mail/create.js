const forms = document.getElementsByName('createMail');

const param = {
    body: 'mail',
    url: '/admin/mail/send',
    redirect: '/admin',
    method: 'post',
}