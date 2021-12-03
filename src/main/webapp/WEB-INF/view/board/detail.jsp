<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="/res/css/board/detail.css">
<div>
    <c:if test="${sessionScope.loginUser.iuser == requestScope.data.writer}">
    <div>
        <a href="/board/del?iboard=${requestScope.data.iboard}"><button>삭제</button></a>
        <a href="/board/regmod?iboard=${requestScope.data.iboard}"><button>수정</button></a>
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
</div>
<script src="/res/js/board/detail.js"></script>
