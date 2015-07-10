<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true" %>

<html>
<head>
    <meta http-equiv="Content-Type" charset="utf-8">
    <title>Login page</title>
    <%--<link href="styles/style.css" rel="stylesheet" type="text/css">--%>
</head>

<body>
    <div>
        <div class="header">
            <h3>ChessApp: login</h3>
        </div>

        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        <c:if test="${not empty msg}">
            <div class="msg">${msg}</div>
        </c:if>

        <div>
            <form name="loginForm" action="<c:url value="/j_spring_security_check" />" method="post">
                <div>
                    <label for="username">Your E-mail</label>
                    <input type="EMAIL" name="username" size="25" title=" Enter your email/login "
                           placeholder="yourmail@example.com" value="test@test.test" required>
                </div>

                <div>
                    <label for="password">Your Password</label>
                    <input type="password" name="password" size="25" title=" Enter your password "
                           placeholder="password" value="test" required>
                </div>

                <div>
                    <button type="submit" name="submit" value="submit">Login</button>
                </div>

                <input type="hidden" name="${_csrf.parameterName}"
                       value="${_csrf.token}"/>
           </form>
        </div>
        <div>
            <div> For new users:
                <br/>If You don't have a login and password, please make it!
            </div>
            <div>
                <a href="/registration">
                    <button type="button" name="ref-login"> Registration</button>
                </a>
            </div>
        </div>

        <div class="footer">
        </div>
    </div>
</body>
</html>
