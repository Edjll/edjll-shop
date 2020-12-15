const forms = document.getElementsByName('updateReview');

const param = {
    body: 'review',
    url: '/admin/review/update',
    redirect: '/admin/review/all',
    method: 'post',
}