<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="/res/css/board/detail.css" ver="1">
<div>
    <c:if test="${sessionScope.loginUser.iuser == requestScope.data.writer}">
        <div>
            <a href="/board/del?iboard=${requestScope.data.iboard}"><button>삭제</button></a>
            <a href="/board/regmod?iboard=${requestScope.data.iboard}"><button>수정</button></a>
        </div>
    </c:if>

    <c:if test="${sessionScope.loginUser != null}">
        <div class="fav">
            <c:choose>
                <c:when test="${requestScope.isHeart == 1}">
                    <a href="/board/heart?proc=2&iboard=${requestScope.data.iboard}"><i class="fas fa-thumbs-up"></i></a>
                </c:when>
                <c:otherwise>
                    <a href="/board/heart?proc=1&iboard=${requestScope.data.iboard}"><i class="far fa-thumbs-up"></i></a>
                </c:otherwise>
            </c:choose>

        </div>
    </c:if>

    <div>글번호 : ${requestScope.data.iboard}</div>
    <div>조회수 : ${requestScope.data.hit}</div>
    <div>작성자 : ${requestScope.data.writerNm}</div>
    <div>등록일시 : ${requestScope.data.rdt}</div>
    <div>제목 : ${requestScope.data.title}</div>
    <div>내용 : ${requestScope.data.ctnt}</div>

    <div>
        <c:if test="${requestScope.prevIboard != 0 }">
            <a href="/board/detail?iboard=${requestScope.prevIboard}"><input type="button" value="이전글"></a>
        </c:if>
        <c:if test="${requestScope.nextIboard != 0 }">
            <a href="/board/detail?iboard=${requestScope.nextIboard}"><input type="button" value="다음글"></a>
        </c:if>
    </div>

    <c:if test="${sessionScope.loginUser != null}">
        <div>
            <form id="cmtNewFrm">
                <div>
                    <input type="text" name="ctnt" placeholder="댓글 내용">
                    <input type="submit" value="등록">
                </div>
            </form>
        </div>
    </c:if>

    <div id="cmtListContainer" data-iboard="${requestScope.data.iboard}"
         data-loginuserpk="${sessionScope.loginUser.iuser}"></div>

</div>
<div class="cmtModContainer">
    <div class="cmtModBody">
        <form id="cmtModFrm">
            <input type="hidden" name="icmt">
            <div><input type="text" name="ctnt" placeholder="댓글 내용"></div>
            <div>
                <input type="submit" value="수정">
                <input type="button" value="취소" id="btnCancel">
            </div>
        </form>
    </div>

</div>
<script src="/res/js/board/detail2.js" ver="2"></script>
