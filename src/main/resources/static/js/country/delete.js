const forms = document.getElementsByName('deleteCountry');

const param = {
    body: 'country',
    url: '/admin/country/delete',
    redirect: '/admin/country/all',
    method: 'post'
}