const forms = document.getElementsByName('updateQuestion');

const param = {
    body: 'question',
    url: '/support/question/update',
    redirect: '/admin/question/all',
    method: 'post'
}