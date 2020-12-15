const forms = document.getElementsByName('addReview');

const param = {
    body: 'review',
    url: '/review/create',
    redirect: '/user/profile/reviews',
    method: 'post',
}