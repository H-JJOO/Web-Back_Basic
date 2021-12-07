<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="/res/css/board/list.css?ver=1">
<div>
    <c:choose>
        <c:when test="${requestScope.maxPageNum == 0}">
            <div>게시글이 없습니다.</div>
        </c:when>
        <c:otherwise>
            <div>
                <form action="/board/list" method="get">
                    <div>
                        <select name="searchType">
                            <option value="1">제목</option>
                            <option value="2">내용</option>
                            <option value="3">제목/내용</option>
                            <option value="4">글쓴이</option>
                            <option value="5">전체</option>
                        </select>
                        <input type="search" name="searchText">
                        <input type="submit" value="검색">
                    </div>
                </form>
            </div>
            <div>
                <table id="boardTable">
                    <colgroup>
                        <col width="5%">
                        <col>
                        <col>
                        <col width="100px">
                        <col>
                    </colgroup>
                    <tr>
                        <th>no</th>
                        <th>title</th>
                        <th>hits</th>
                        <th>writer</th>
                        <th>reg-datetime</th>
                    </tr>
                    <c:forEach items="${requestScope.list}" var="item">
                        <tr class="record" onclick="moveToDetail(${item.iboard});">
                            <td>${item.iboard}</td>
                            <td><c:out value="${item.title}"/></td>
                            <td>${item.hit}</td>
                            <td><c:out value="${item.writerNm}"/></td>
                            <td>${item.rdt}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

    <div class="pageContainer">
        <c:set var="selectedPage" value="${param.page == null ? 1 : param.page}"></c:set>
        <c:forEach var="page" begin="1" end="${maxPageNum}">
            <div><a href="/board/list?page=${page}"><span class="${selectedPage == page ? 'selected' : ''}"><c:out value="${page}"></c:out></span></a></div>
        </c:forEach>
    </div>
</div>
    </c:otherwise>
</c:choose>
<script src="/res/js/board/list.js"></script>
