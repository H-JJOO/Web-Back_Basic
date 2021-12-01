<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <form action="/user/join" method="post" id="frm" onsubmit="return joinChk();">
        <div><input type="text" name="uid" placeholder="id" required></div>
        <div><input type="password" name="upw" placeholder="password" required></div>
        <div><input type="password" id="reupw" placeholder="password comfirm" required></div>
        <div><input type="text" name="nm" placeholder="name" required></div>
        <div>
            <label>female<input type="radio" name="gender" value="2" checked></label>
            <label>male<input type="radio" name="gender" value="1"></label>
        </div>
        <div>
            <input type="submit" value="join">
            <input type="reset" value="reset">
        </div>
    </form>
</div>
<script src="/res/js/user/join.js"></script>
