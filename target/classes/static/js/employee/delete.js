const forms = document.getElementsByName('dismissEmployee');

const param = {
    body: 'employee',
    url: '/admin/employee/dismiss',
    redirect: '/admin/employee/all',
    method: 'post',
}