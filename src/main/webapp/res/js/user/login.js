var frm = document.querySelector('#frm');
// if (frm) {
    // function proc() {
    //     alert('전송!!!');
    // }
    //
    // var proc2 = function () {
    //     alert('전송!!!')
    // }
    //
    // frm.addEventListener('submit', proc);
    // frm.addEventListener('submit', proc2);
    // frm.addEventListener('submit', function (e) {
    //     alert('전송!!!');
    //     e.preventDefault();//원래 'submit' 이 하는일을 막는다. (문제가 생겼을때)
    // });
if (frm) {
    function frmSubmitEvent(e) {
        if (frm.uid.value.length < 5 || frm.uid.value.length > 20) {
            //아이디가 5글자 미만 혹은 20글자 초과되면 alert, No send
            alert('아이디는 5~20글자 입니다.');
            e.preventDefault();
            return;

        }
        if (frm.upw.value.length < 5) {
            //비밀번호가 5글자 미만이면 alert, No send
            alert('비밀번호를 확인해 주세요.');
            e.preventDefault();
            return;


        }
    }
    frm.addEventListener('submit', frmSubmitEvent);

    var btnShowPw = document.querySelector('#btnShowPw');//주소값 주기
    if (btnShowPw) {
        btnShowPw.addEventListener('click', function () {
            //클릭하면 function 실행
            if (frm.upw.type === 'password') {//upw 타입이 'password'면
                frm.upw.type = 'text';//upw 타입을 'text'로 변경
                btnShowPw.value = 'hide PW';//btnShowPw 의 value를 변경
            } else {
                frm.upw.type = 'password';//upw 타입을 'password'로 변경
                btnShowPw.value = 'show PW';//btnShowPw 의 value를 변경
            }
        });
    }
}

