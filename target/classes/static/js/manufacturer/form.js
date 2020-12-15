const formUploads = document.getElementsByClassName('form-upload');

for (let i = 0; i < formUploads.length; i++) {
    const formUpload = formUploads.item(i);
    const formUploadInput = formUpload.getElementsByTagName('input')[0];
    const formUploadImage = formUpload.getElementsByClassName('form-upload-image')[0];
    const formUploadError = formUpload.getElementsByClassName('form-upload-error')[0];
    if (formUploadInput) {
        formUploadInput.onchange = () => {
            const file = formUploadInput.files.item(0);
            if (file) {
                if (!file.type.match('image.png|image.jpeg')) {
                    if (!formUpload.classList.contains('error')) {
                        formUpload.classList.add('error');
                    }
                    if (formUploadError && !formUploadError.classList.contains('visible')) {
                        formUploadError.classList.add('visible');
                    }
                } else {
                    const reader = new FileReader();
                    reader.onload = (event) => {
                        formUpload.classList.add('show-preview');
                        if (formUploadImage) {
                            formUploadImage.classList.add('hidden');
                        }
                        const formUploadPreview = formUpload.getElementsByClassName('form-upload-preview')[0];
                        if (formUploadPreview) {
                            formUploadPreview.remove();
                        }
                        if (formUpload.classList.contains('error')) {
                            formUpload.classList.remove('error');
                        }
                        if (formUploadError && formUploadError.classList.contains('visible')) {
                            formUploadError.classList.remove('visible');
                        }
                        formUpload.append(createPreviewImage(event.target.result));
                    }
                    reader.readAsDataURL(file);
                }
            } else {
                const formUploadPreview = formUpload.getElementsByClassName('form-upload-preview')[0];
                if (formUploadPreview) {
                    formUploadPreview.remove();
                }
                formUpload.classList.remove('show-preview');
                if (formUploadImage) {
                    formUploadImage.classList.remove('hidden');
                }
            }
        }
    }
}

function createPreviewImage(src) {
    const img = document.createElement('img');
    img.classList.add('form-upload-preview');
    img.src = src;
    return img;
}