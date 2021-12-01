function joinChk() {
    var frm = document.querySelector('#frm');
    if (frm.uid.value.length < 5 || frm.uid.value.length > 20) {//아이디 문자열 개수가 5개 미만이면 alert
        alert('아이디를 5~20자 사이로 작성해 주세요.')
        return false;
    } else if (frm.upw.value.length < 5) {//패스워드 문자열 개수가 5개 미만이면 alert
        alert('비밀번호를 5자 이상 작성해 주세요.');
        return false;
    } else if (frm.upw.value !== frm.reupw.value) {//패스워드 와 패스워드 확인이 다르면 alert
        alert('비밀번호를 확인해 주세요.')
        return false;
    } else if (frm.nm.value.length > 5) {//이름이 다섯글자 초과하면 alert
        alert('이름을 확인해 주세요.')
        return false;
    }
    return true;
}