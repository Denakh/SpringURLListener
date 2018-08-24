<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>Spring URL Listener</title>
</head>
<body>

@Temporal(value = TemporalType.TIMESTAMP)
private Date date;

private long respTime;
private int respCode;
private long contentLength;
private boolean keywordInclusion;
private boolean respTimeExcess;

<h2> Listening results for URL : ${url} </h2>
    <table border="1">
        <thead>
        <tr>
            <td><b> Entry date </b></td>
            <td><b> Response code </b></td>
            <td><b> Response time, ms </b></td>
            <td><b> Response time excess </b></td>
            <td><b> Content length </b></td>
            <td><b> Keyword inclusion </b></td>
        </tr>
        </thead>
        <c:choose>
            <c:when test="${not empty exeResultsList}">
                <c:forEach items="${exeResultsList}" var="exeResult">
                    <tr>
                        <td>${exeResult.date}</td>
                        <td>${exeResult.respCode}</td>
                        <td>${exeResult.respTime}</td>
                        <td>
                            <c:if test="${respTimeExcess = true}">
                                <b style="color: crimson"> Yes </b>
                            </c:if>
                            <c:if test="${respTimeExcess = false}">
                                <b style="color: blue"> No </b>
                            </c:if>
                        </td>
                        <td>${exeResult.contentLength}</td>
                        <td>
                            <c:if test="${keywordInclusion = true}">
                                <b style="color: blue"> Yes </b>
                            </c:if>
                            <c:if test="${keywordInclusion = false}">
                                <b style="color: #00ff3c"> No </b>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="7">Effective entries don't exist</td>
                </tr>
            </c:otherwise>
        </c:choose>
    </table>

<h2><p><a href="/">Main menu</a></p></h2>

</body>
</html>

