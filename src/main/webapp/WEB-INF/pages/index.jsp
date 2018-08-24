<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>Spring URL Listener</title>
</head>
<body>
<div align="center">
    <h3>Your login is: ${login}, your roles are:</h3>
    <c:forEach var="s" items="${roles}">
        <h3><c:out value="${s}"/></h3>
    </c:forEach>

    <c:url value="/update" var="updateUrl"/>
    <h3>
        <form action="${updateUrl}" method="POST">
            E-mail:<br/><input type="text" name="email" value="${email}"/><br/>
            Phone:<br/><input type="text" name="phone" value="${phone}"/><br/>
            <input type="submit" value="Update"/>
        </form>
    </h3>

    <c:url value="/logout" var="logoutUrl"/>
    <h3><p>Click to logout: <a href="${logoutUrl}">LOGOUT</a></p></h3>

</div>

<h3>
    <form action="/url_execute" method="POST">
        <br/>Enter URL for listening:<input type="text" name="inurl"><br/>
        <br/>Enter keyword for searching in response body:<input type="text" name="inkeyword"><br/>
        <br/>Enter controlled response limit time, ms:<input type="text" name="inlimtime"><br/>
        <input type="submit"/>
    </form>
</h3>

<br/>
<h3><p><a href="/results">URL listening list and results</a></p></h3>

</body>
</html>
