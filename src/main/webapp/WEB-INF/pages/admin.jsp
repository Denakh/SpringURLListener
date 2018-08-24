<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>Spring URL Listener</title>
</head>
<body>
<div align="center">
    <h2>Page for admins only!</h2>

    <c:url value="/logout" var="logoutUrl"/>
    <h3><p>Click to logout: <a href="${logoutUrl}">LOGOUT</a></p></h3>
</div>
</body>
</html>
