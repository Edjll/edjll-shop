const forms = document.getElementsByName('updateCountry');

const param = {
    body: 'country',
    url: '/admin/country/update',
    redirect: '/admin/country/all',
    method: 'post',
}