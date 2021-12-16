var frmElem = document.querySelector('#frm');
var submitBtnElem = document.querySelector('#submitBtn');

submitBtnElem.addEventListener('click', function () {
    if (frmElem.upw.value.length < 5 ) {
        alert('비밀번호를 5자이상 작성해 주세요.');
        return false;
    }
    if (frmElem.changedUpw.value.length < 5) {
        alert('변경할 비밀번호를 5자이상 작성해 주세요.');
        return false;
    }
    if (frmElem.changedUpwConfirm.value != frmElem.changedUpw.value) {
        alert('비밀번호를 확인해주세요.');
        return false;
    }
    frmElem.submit();
});