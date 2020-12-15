const forms = document.getElementsByName('createCountry');

const param = {
    body: 'country',
    url: '/admin/country/create',
    redirect: '/admin/country/all',
    method: 'post',
}