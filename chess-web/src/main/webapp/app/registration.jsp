<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">

    <title>Registration page</title>
    <link rel="stylesheet" href="css/main.css" type="text/css">
</head>
<body>
<div>
    <div class="header">
        <h3>ChessApp: registration</h3>
    </div>

    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
    <c:if test="${not empty msg}">
        <div class="msg">${msg}</div>
    </c:if>

    <div>
        <form name="registration" method="POST" action="/registration">
            <div>
                <label for="username">Your Name</label>
                <input type="text" name="username" size="25" title=" Enter your name " placeholder="your name"
                       value="test" required>
            </div>
            <div>
                <label for="usermail">Your E-mail</label>
                <input type="EMAIL" name="usermail" size="25" title=" Enter your email "
                       placeholder="your mail@example.com" value="test@test.test" required>
            </div>
            <div>
                <label for="userpassword">Create Password</label>
                <input type="password" name="userpassword" size="25"
                       title=" Create password with more than 8 chars  " placeholder="password" value="test"
                       required>
            </div>
            <div>
                <input type="submit" value="Confirm">
            </div>

            <%-- spring security --%>
            <input type="hidden" name="${_csrf.parameterName}"
                   value="${_csrf.token}"/>
        </form>
    </div>

    <div>
        <div> For existing users:
            <br/>If You already have a login and password, please make login
        </div>
        <div>
            <a href="login.jsp" target="_blank">
                <button type="button" name="ref-login" value="login"> Login</button>
            </a>
        </div>
    </div>

    <div class="footer">
    </div>
</div>
</body>
</html>
