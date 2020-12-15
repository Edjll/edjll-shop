const forms = document.getElementsByName('updateReview');

const param = {
    body: 'review',
    url: '/review/update',
    redirect: '/user/profile/reviews',
    method: 'post',
}