const forms = document.getElementsByName('deleteReview');

const param = {
    body: 'review',
    url: '/review/delete',
    redirect: '/user/profile/reviews',
    method: 'post'
}