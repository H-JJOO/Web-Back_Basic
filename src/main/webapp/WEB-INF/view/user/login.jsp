<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="/res/css/user/login.css">
<div class="wrap">
    <div>
        <form action="/user/login" method="post" id="frm">
            <div><input type="text" name="uid" placeholder="아이디" value="hong123"></div>
            <div><input type="password" name="upw" placeholder="password" value="123123"></div>
            <div class="logBtn">
                <input type="submit" value="login">
            </div>
            <div class="jsBtn">
                <a href="/user/join"><input type="button" value="join"></a>
                <input type="button" value="show PW" id="btnShowPw">
            </div>
        </form>
    </div>
</div>
<script src="/res/js/user/login.js" ver="3"></script>