'use strict';
//<!--Cкрипт imageLoader.js загружает фото и показывает его на форме. Фото загружается в БД в поле BLOB- ImageData->
//На форме используются три элемента
//<!-- Если фото есть - показали, если нет - дефолтная картинка -->
//   <!-- <img th:if="${wuRequestPayment.getImageData() != null}" th:src="@{/admin/processRequestChangePayment/{id}/image(id = ${wuRequestPayment.getId()})}" class="img-thumbnail" th:id="${'imageBook'}"/>
//        <img th:if="${wuRequestPayment.getImageData() == null}" data-src="holder.js/50x100" th:alt="${wuRequestPayment.getId()}" class="img-thumbnail" th:id="${'imageBook'}"/>
//        <button type="button" class="btn btn-outline-primary mt-2" id="imageUpload">Загрузить файл</button>
//        <input type="file" name="file" hidden="hidden" multiple="" accept="image/*" id="imageInput"/>-->
//
// Запуск скрипта imageLoader на форме
//<th:block layout:fragment="scripts">
//     <script th:src="@{'/js/imageLoader.js'}"></script>
//     <script th:inline="javascript">
//         var imageLoader = new ImageLoader('imageUpload', 'imageInput', 'imageBook');
//         imageLoader.init();
//     </script>
// </th:block>


//window.onload=function() {

    var ImageLoader = (function () {

        function ImageLoader(elementEventId, inputFileId, outputElementId) {
            this.elementEvent = document.getElementById(elementEventId);
            this.inputFile = document.getElementById(inputFileId);
            this.outputElement = document.getElementById(outputElementId);
        }

        ImageLoader.prototype.init = function () {
            var _this = this;
            this.elementEvent.addEventListener('click', function (ev) {
                ev.preventDefault();
                _this.inputFile.addEventListener('change', function (e) {
                    return _this._loadFile(e, _this.outputElement);
                });
                _this.inputFile.click();
                return false;
            });
        };
        ImageLoader.prototype._loadFile = function (event, output) {
            var reader;
            reader = new FileReader();
            reader.onload = function () {
                output.value = event.target.value;
                output.src = reader.result;
                output.style = null;
            };
            return reader.readAsDataURL(event.target.files[0]);
        };
        return ImageLoader;
    }());
//}