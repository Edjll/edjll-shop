const forms = document.getElementsByName('createQuestion');

const param = {
    body: 'question',
    url: '/support/question/create',
    redirect: '/',
    method: 'post'
}