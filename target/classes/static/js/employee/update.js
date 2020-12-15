const forms = document.getElementsByName('updateEmployee');

const param = {
    body: 'employee',
    url: '/admin/employee/update',
    redirect: '/admin/employee/all',
    method: 'post',
}