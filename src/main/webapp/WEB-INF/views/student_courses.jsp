<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>My courses</title>
    <!--to show icons-->
    <!--link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"-->
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="elements/navbar.jsp"/>
<div class="container">
    <h2><a href="${pageContext.request.contextPath}/my_courses/not_started"><spring:message code="notStarted"/></a> |
        <a href="${pageContext.request.contextPath}/my_courses/in_progress"><spring:message code="inProgress"/></a> |
        <a href="${pageContext.request.contextPath}/my_courses/completed"><spring:message code="completed"/></a></h2>
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col"><spring:message code="courseName"/></th>
            <th scope="col"><spring:message code="teacherName"/></th>
            <th scope="col"><spring:message code="courseTheme"/></th>
            <th scope="col"><spring:message code="duration"/></th>
            <c:if test="${condition eq 'COMPLETED'}">
                <th scope="col"><spring:message code="mark"/></th>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${courseResponses}" var="courseResponse">
            <tr>
                <td>${courseResponse.course.name}</td>
                <td>${courseResponse.course.teacher.fullName}</td>
                <td>${courseResponse.course.theme}</td>
                <td>${courseResponse.course.duration}</td>
                <c:if test="${condition eq 'COMPLETED'}">
                    <td>${courseResponse.grade}</td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <span style="float: right">
    <a href="?locale=en">en</a>
    |
    <a href="?locale=ua">ua</a>
</span>

</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
