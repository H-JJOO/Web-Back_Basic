<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div>
    <div>프로필 이미지</div>
    <div>
        <div>이름 : ${sessionScope.loginUser.nm}</div>
        <div>성별 : ${sessionScope.loginUser.gender}</div>
    </div>
    <div>
        <form action="/user/profile" method="post" enctype="multipart/form-data">
            <div><label>이미지 : <input type="file" name="profileImg"></label></div>
            <div><input type="submit" value="프로필 이미지 변경"></div>


        </form>
    </div>
</div>