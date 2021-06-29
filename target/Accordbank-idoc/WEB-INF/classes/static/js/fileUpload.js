//Скрипт для загрузки файла на сервер



window.onload=function() {

//    var singleUploadForm = document.querySelector("#singleUploadForm");

    //Input в который загружаем файл
    var singleFileUploadInput = document.querySelector("#singleFileUploadInput");
    //в этом элементе показываем сообщение об ошибке
    var singleFileUploadError = document.querySelector("#singleFileUploadError");
    //в этом элементе показываем путь к загруженному файлу
    var singleFileUploadSuccess = document.querySelector("#singleFileUploadSuccess");
    //кнопка на которой событие загрузки файла
    var buttSingleUpload=document.querySelector("#buttSingleUpload");

    //ПОказать название файла,который загружаем в input
    $('.custom-file-input').on('change', function () {
        var fileName = $(this).val().split('\\').pop();
        $(this).next('.custom-file-label').addClass("selected").html(fileName);
    });



    function uploadSingleFile(file) {
        var formData = new FormData();
        formData.append("file", file);

        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/uploadFile");

        xhr.onload = function () {
            console.log(xhr.responseText);
            var response = JSON.parse(xhr.responseText);
            if (xhr.status == 200) {
                singleFileUploadError.style.display = "none";
                singleFileUploadSuccess.innerHTML = "<p> Файл завантажено успішно : <a href='" + response.fileDownloadUri + "' target='_blank'>" + response.fileDownloadUri + "</a></p>";
                singleFileUploadSuccess.style.display = "block";
            } else {
                singleFileUploadSuccess.style.display = "none";
                singleFileUploadError.innerHTML = (response && response.message) || "Some Error Occurred";
            }
        }

        xhr.send(formData);
    }



    //Загружаем файл на сервер по нажатии кнопки
    buttSingleUpload.onclick = function() {
        var files = singleFileUploadInput.files;
        if (files.length === 0) {
            singleFileUploadError.innerHTML = "Выберите файл";
            singleFileUploadError.style.display = "block";
        }
        uploadSingleFile(files[0]);
        event.preventDefault();

    }


}