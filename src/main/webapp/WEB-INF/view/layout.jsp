<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/res/css/common.css">
    <title>${requestScope.title}</title>
</head>
<body>
    <div class="container">
        <div class="header">
            header
        </div>
        <div class="body"><jsp:include page="/WEB-INF/view${requestScope.page}.jsp"></jsp:include></div>
        <div class="footer">
            footer
        </div>
    </div>
</body>
</html>
