const adminPanel = document.getElementsByClassName('admin-panel')[0];
if (localStorage.getItem('adminMenuCoagulation') === 'true') {
    adminPanel.classList.add('admin-panel--quietly', 'admin-panel--coagulation');
    setTimeout(() => adminPanel.classList.remove('admin-panel--quietly'), 200);
}

const adminMenu = new AdminMenu(document.getElementsByClassName('admin-menu')[0]);
adminMenu.scan();


const controlMenuButton = document.getElementsByClassName('admin-page-controls__buttons__control-menu')[0];

if (controlMenuButton) {
    controlMenuButton.onclick = () => {
        if (adminPanel.classList.contains('admin-panel--coagulation')) {
            adminPanel.classList.remove('admin-panel--coagulation');
            localStorage.setItem('adminMenuCoagulation', 'false');
        } else {
            adminPanel.classList.add('admin-panel--coagulation');
            localStorage.setItem('adminMenuCoagulation', 'true');
        }
    }
}
