var cmtListContainerElem = document.querySelector('#cmtListContainer');
var cmtModContainerElem = document.querySelector('.cmtModContainer');
var cmtModFrmElem = cmtModContainerElem.querySelector('#cmtModFrm');
var btnCancelElem = document.querySelector('#btnCancel');
var submitBtnElem = cmtModFrmElem.querySelector('input[type=submit][value=수정]')
var cmtNewFrmElem = document.querySelector('#cmtNewFrm')

if (cmtNewFrmElem) {
    var newSubmitBtnElem = cmtNewFrmElem.querySelector('input[type=submit]');
    newSubmitBtnElem.addEventListener('click', function (e) {
        e.preventDefault();

        if (cmtNewFrmElem.ctnt.value === 0) {
            alert('댓글 내용을 작성해 주세요.')
            return;
        }
        var url = '/board/cmt?proc=ins'

        var param = {
            //iboard, ctnt
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
                    getList();
                    break;
            }
        }).catch(function (err) {
            console.log(err);
            alert('댓글 달기에 실패하였습니다.');
        })
    })
}


submitBtnElem.addEventListener('click', function (e) {
    e.preventDefault();
    var url = '/board/cmt?proc=upd';

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
                var e = new Event('click');
                btnCancelElem.dispatchEvent(e);
                break;
        }
    }).catch(function (err) {
        console.error(err);
        alert(err);
    })
})

function modCtnt(ctnt) {
    var selectedTrElem = cmtListContainerElem.querySelector('.cmt_selected');
    var tdCtntElem = selectedTrElem.children[0];
    tdCtntElem.innerText = ctnt;
}

btnCancelElem.addEventListener('click', function () {
    cmtModContainerElem.style.display = 'none';
    var selectedTrElem = cmtListContainerElem.querySelector('.cmt_selected');
    selectedTrElem.classList.remove('cmt_selected');
});

if (cmtListContainerElem) {
    function openModForm(icmt, ctnt) {
        cmtModContainerElem.style.display = 'flex';

        cmtModFrmElem.icmt.value = icmt;
        cmtModFrmElem.ctnt.value = ctnt;
    }

    function getList() {
        var iboard = cmtListContainerElem.dataset.iboard;
        var url = '/board/cmt?iboard=' + iboard;

        fetch(url).then(function (res) {
            return res.json();
        }).then(function (data) {
            console.log(data);
            displayCmt(data);
        }).catch(function (err) {
            console.error(err);
        });
    }
    getList();

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
                    tr.classList.add('cmt_selected');
                    var ctnt = tr.children[0].innerText;
                    openModForm(item.icmt, ctnt);
                });
                var btnDel = document.createElement('button');
                btnDel.innerText = '삭제';
                btnDel.addEventListener('click', function () {
                    if (confirm('삭제 하시겠습니까?')) {
                        //Ajax 통신으로 처리
                        var url = '/board/cmt?proc=del';
                        var param = {
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

