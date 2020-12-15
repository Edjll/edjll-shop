const forms = document.getElementsByName('addEmployee');

const param = {
    body: 'employee',
    url: '/admin/employee/add',
    redirect: '/admin/employee/all',
    method: 'post',
}