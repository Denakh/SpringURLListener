<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>Spring URL Listener</title>
</head>
<body>

<h2> Executed URL list </h2>
<form action="/get_results" method="POST">
    <table border="1">
        <thead>
        <tr>
            <td><b> Choice for delete </b></td>
            <td><b> Choice for results </b></td>
            <td><b> Server name </b></td>
            <td><b> URI </b></td>
            <td><b> Entry date </b></td>
            <td><b> Keyword for searching </b></td>
            <td><b> Limit response time </b></td>
        </tr>
        </thead>
        <c:choose>
            <c:when test="${not empty exeURLList}">
                <c:forEach items="${exeURLList}" var="exeURL">
                    <tr>
                        <td><input type="checkbox" name="dellist" value="${exeURL.id}"/></td>
                        <td><input type="radio" name="urlresults" value="${exeURL.id}"/></td>
                        <td>${exeURL.server}</td>
                        <td>${exeURL.uri}</td>
                        <td>${exeURL.date}</td>
                        <td>${exeURL.keyword}</td>
                        <td>${exeURL.limitedTime}</td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="2"><input type="submit"/></td>
                </tr>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="7">Effective entries don't exist</td>
                </tr>
            </c:otherwise>
        </c:choose>
    </table>
</form>

<h2><p><a href="/">Main menu</a></p></h2>

</body>
</html>
