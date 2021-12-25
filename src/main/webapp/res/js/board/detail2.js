var cmtListContainerElem = document.querySelector('#cmtListContainer');
var cmtModContainerElem = document.querySelector('.cmtModContainer');
var cmtModFrmElem = cmtModContainerElem.querySelector('#cmtModFrm');
var btnCancelElem = document.querySelector('#btnCancel');
var submitBtnElem = cmtModFrmElem.querySelector('input[type=submit][value=수정]')
var cmtNewFrmElem = document.querySelector('#cmtNewFrm')

//댓글 등록 기능
if (cmtNewFrmElem) {
    var newSubmitBtnElem = cmtNewFrmElem.querySelector('input[type=submit]');
    newSubmitBtnElem.addEventListener('click', function (e) {
        e.preventDefault();
        //댓글 등록 안하고 클릭하면
        if (cmtNewFrmElem.ctnt.value === 0) {
            alert('댓글 내용을 작성해 주세요.')
            return;
        }
        var url = '/board/cmt?proc=ins';

        var param = {
            //iboard, ctnt, 어떤게시글에 어떤내용
            'iboard' : cmtListContainerElem.dataset.iboard,
            'ctnt' : cmtNewFrmElem.ctnt.value
        };
        fetch(url, {
            'method' : 'POST',
            'headers' : {'Content-Type' : 'application/json'},
            'body' : JSON.stringify(param)
        }).then(function (res) {
            return res.json();
        }).then(function (data) {
            switch (data.result) {
                case 0:
                    alert('댓글 달기를 할 수 없습니다.')
                    break;
                case 1:
                    cmtNewFrmElem.ctnt.value = '';//입력란 지우기
                    cmtListContainerElem.innerHTML = '';//기존에있던 cmtList 지우기
                    getList();//새로 댓글리스트 불러오기 (DB 에 최신 등록분 포함)
                    break;
            }
        }).catch(function (err) {
            console.log(err);
            alert('댓글 달기에 실패하였습니다.');
        })
    })
}

//댓글 수정 기능
submitBtnElem.addEventListener('click', function (e) {
    e.preventDefault();
    var url = '/board/cmt?proc=upd';

    //어떤댓글에 어떤내용
    var param = {
        'icmt' : cmtModFrmElem.icmt.value,
        'ctnt' : cmtModFrmElem.ctnt.value
    };

    fetch(url, {
        'method' : 'POST',
        'headers' : {'Content-Type' : 'application/json'},
        'body' : JSON.stringify(param)
    }).then(function (res) {
        return res.json();
    }).then(function (data) {
        switch (data.result) {
            case 0:
                alert('댓글을 수정할 수 없습니다.');
                break;
            case 1:
                modCtnt(param.ctnt);
                var e = new Event('click');//click 이벤트 추가
                btnCancelElem.dispatchEvent(e);//수정하는 tr에 추가한 cmt_selected 클래스 삭제하는 이벤트
                break;
        }
    }).catch(function (err) {
        console.error(err);
        alert(err);
    })
})

//댓글 내용부분 최신 수정본으로 변경
function modCtnt(ctnt) {
    var selectedTrElem = cmtListContainerElem.querySelector('.cmt_selected');
    var tdCtntElem = selectedTrElem.children[0];
    tdCtntElem.innerText = ctnt;
}

//수정시 취소버튼 이벤트 추가
btnCancelElem.addEventListener('click', function () {
    cmtModContainerElem.style.display = 'none';//display flex - > none 변경
    var selectedTrElem = cmtListContainerElem.querySelector('.cmt_selected');
    selectedTrElem.classList.remove('cmt_selected');//수정버튼 누를때 추가된 cmt_selected 제거
});

//cmtList 가 있을경우 실행
if (cmtListContainerElem) {
    function openModForm(icmt, ctnt) {//수정버튼 눌렀을때 적용
        cmtModContainerElem.style.display = 'flex';//js 에서는 display default 값이 none, none -> flex 변경
        cmtModFrmElem.icmt.value = icmt;
        cmtModFrmElem.ctnt.value = ctnt;
    }

    function getList() {//ctntList
        var iboard = cmtListContainerElem.dataset.iboard;//iboard 값을 jsp 파일에서 받아옴
        var url = '/board/cmt?iboard=' + iboard;

        fetch(url).then(function (res) {//res 로 통신에 대한 정보 (url) 넘어감
            return res.json();//결과물이 자바객체로 변환
        }).then(function (data) {//변환 결과물
            console.log(data);
            displayCmt(data);//테이블에 결과물 대입?
        }).catch(function (err) {
            console.error(err);
        });
    }
    getList();
    //JS 로 테이블 만들기
    function displayCmt(data) {
        var tableElem = document.createElement('table');
        tableElem.innerHTML =`
        <tr>
            <th>내용</th>
            <th>작성자</th>
            <th>작성일</th>
            <th>비고</th>
        </tr>
       `;
        cmtListContainerElem.appendChild(tableElem);

        //로그인 여부 확인
        var loginUserPk = cmtListContainerElem.dataset.loginuserpk === '' ? 0 : Number(cmtListContainerElem.dataset.loginuserpk);

        data.forEach(function (item) {
            var tr = document.createElement('tr');

            var ctnt = item.ctnt.replaceAll('<', '&lt').replaceAll('>', '&gt');

            tr.innerHTML = `
            <td>${ctnt}</td>
            <td>${item.writerNm}</td>
            <td>${item.rdt}</td>
            `;
            tableElem.appendChild(tr);

            var lastTd = document.createElement('td');
            tr.appendChild(lastTd);

            if (loginUserPk === item.writer) {
                var btnMod = document.createElement('button');
                btnMod.innerText = '수정';
                btnMod.addEventListener('click', function () {
                    tr.classList.add('cmt_selected');//수정 버튼 누르면 'cmt_selected' 클래스 추가
                    var ctnt = tr.children[0].innerText;//댓글의 첫번째, 내용칸의 내용
                   openModForm(item.icmt, ctnt);
                });
                var btnDel = document.createElement('button');
                btnDel.innerText = '삭제';
                btnDel.addEventListener('click', function () {
                   if (confirm('삭제 하시겠습니까?')) {
                       //Ajax 통신으로 처리
                       var url = '/board/cmt?proc=del';
                       var param = {//삭제할때는 icmt 값만 있으면 됨
                           'icmt' : item.icmt
                       };
                       fetch(url, {
                           'method' : 'post',
                           'headers' : {'Content-Type' : 'application/json'},
                           'body' : JSON.stringify(param)
                       }).then(function (res) {
                           return res.json();
                       }).then(function (data) {
                           //결과값
                           switch (data.result) {
                               case 0 :
                                   alert('댓글 삭제를 할 수 없습니다.')
                                   break;
                               case 1 :
                                   tr.remove();
                                   break;
                           }
                       }).catch(function (err) {
                           console.error(err);
                           alert('댓글 삭제를 실패하였습니다.');
                       });
                   }
                });

                lastTd.appendChild(btnMod);
                lastTd.appendChild(btnDel);
            }
        });
    }
}

