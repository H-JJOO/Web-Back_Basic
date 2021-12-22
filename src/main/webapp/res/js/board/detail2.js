var cmtListContainerElem = document.querySelector('#cmtListContainer');
var cmtModContainerElem = document.querySelector('.cmtModContainer');

//댓글 수정 취소 버튼 클릭 이벤트 연결
var btnCancelElem = document.querySelector('#btnCancel');
btnCancelElem.addEventListener('click', function () {
    cmtModContainerElem.style.display = 'none';
    var selectedTrElem = cmtListContainerElem.querySelector('.cmt_selected');//클래스 찾아서
    selectedTrElem.classList.remove('cmt_selected');//지운다

});

var cmtModFrmElem = cmtModContainerElem.querySelector('#cmtModFrm');
var submitBtnElem = cmtModFrmElem.querySelector('input[type=submit][value=수정]');
submitBtnElem.addEventListener('click', function (e) {
    e.preventDefault();//submit 막기(하는일 못하게)
    var url = '/board/cmt?proc=upd';

    //댓글 수정 : ctnt, icmt
    var param = {//내용(객체)
        'icmt' : cmtModFrmElem.icmt.value,
        'ctnt' : cmtModFrmElem.ctnt.value
    };

    fetch(url, {//POST 방식때는 객체필요, 객체안에는 ; 넣으면 안된다.
        'method' : 'POST',
        'headers' : {'Content-Type' : 'application/json'},//객체
        'body' : JSON.stringify(param)//JSON 형태로 변환(문자열)
    }).then(function (res) {//res 로 통신에 대한 정보 들어감
        return res.json();//공식문서에서 권장, 응답된 결과물이 담겨서 자바객체로 변환되어서 리턴
    }).then(function (data) {//통신 결과물
        console.log(data.result);
        switch (data.result) {
            case 0://수정 실패
                alert('댓글 수정을 할 수 없습니다.')
                break;
            case 1://수정 성공
                modCtnt(param.ctnt);
                var e = new Event('click');
                btnCancelElem.dispatchEvent(e);
                break;
        }
    }).catch(function (err) {//에러대비
        console.log(err);
        alert('댓글 수정 실패');
    });

});

function modCtnt(ctnt) {
    var selectedTrElem = cmtListContainerElem.querySelector('.cmt_selected');
    var tdCtntElem = selectedTrElem.children[0];
    tdCtntElem.innerText = ctnt;
}

if (cmtListContainerElem) {
    function openModForm(icmt, ctnt) {//구조분해 할당 사용
        cmtModContainerElem.style.display = 'flex';
        cmtModFrmElem.icmt.value = icmt;
        cmtModFrmElem.ctnt.value = ctnt;
    }

    function getList() {
        var iboard = cmtListContainerElem.dataset.iboard;//속성값은 소문자
        var url = '/board/cmt?iboard=' + iboard;
        console.log('url : ' + url);
        fetch(url).then(function (res) {
            return res.json();
        }).then(function (data) {
            console.log(data);
            displayCmt2(data);
        }).catch(function (err) {
            console.error(err);
        });
    }
    function displayCmt2(data) {
        var tableElem = document.createElement('table');
        tableElem.innerHTML = `
            <tr>
                <th>내용</th>
                <th>작성자</th>
                <th>작성일</th>
                <th>비고</th>
            </th>
        `;//템플릿 리터널, 변수값 바로 넣을수 있음 ES6 문법
        cmtListContainerElem.appendChild(tableElem);

        var loginUserPk = cmtListContainerElem.dataset.loginuserpk === '' ? 0 : Number(cmtListContainerElem.dataset.loginuserpk);
        //HTML 에서 값 가져온다 = 무조건 문자열
        console.log('loginUserPk : ' + loginUserPk);

        data.forEach(function (item) {
           var tr = document.createElement('tr');

           var ctnt = item.ctnt.replaceAll('<', '$lt;').replaceAll('>', '&gt;');

           tr.innerHTML = `
                <td>${ctnt}</td>
                <td>${item.writerNm}</td>
                <td>${item.rdt}</td>
           `;//${} 여기서 쓰는거 EL식 아님, 변수의 값을 집어넣을때 쓰는 JS 문법, EL식은 jsp 파일에서만 사용가능
           tableElem.appendChild(tr);

           var lastTd = document.createElement('td');
           tr.appendChild(lastTd);

           if (loginUserPk === item.writer) {

               var btnMod = document.createElement('button');
               btnMod.innerText = '수정';
               btnMod.addEventListener('click', function () {
                   tr.classList.add('cmt_selected');
                   var ctnt = tr.children[0].innerText;
                   openModForm(item.icmt, ctnt);//구조 분해 할당 사용
               });
               var btnDel = document.createElement('button');
               btnDel.innerText = '삭제';

               lastTd.appendChild(btnMod);
               lastTd.appendChild(btnDel);
           }
        });

    }

    function displayCmt(data) {
        var tableElem = document.createElement('table');

        var tr = document.createElement('tr');
        var th1 = document.createElement('th');
        th1.innerText = '내용';
        var th2 = document.createElement('th');
        th2.innerText = '작성자';
        var th3 = document.createElement('th');
        th3.innerText = '작성일';
        var th4 = document.createElement('th');
        th4.innerText = '비고';
        tr.appendChild(th1);
        tr.appendChild(th2);
        tr.appendChild(th3);
        tr.appendChild(th4);

        tableElem.appendChild(tr);
        cmtListContainerElem.appendChild(tableElem);

    }
    getList();
}
