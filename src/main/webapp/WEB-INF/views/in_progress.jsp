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

    <title>In progress</title>
    <!--to show icons-->
    <!--link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"-->
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="elements/navbar.jsp"/>
<div class="container">
    <h2><a href="${pageContext.request.contextPath}/not_started"><spring:message code="notStarted"/></a> |
        <a href="${pageContext.request.contextPath}/in_progress"><spring:message code="inProgress"/></a> |
        <a href="${pageContext.request.contextPath}/completed"><spring:message code="completed"/></a></h2>
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col"><spring:message code="courseName"/></th>
            <th scope="col"><spring:message code="teacherName"/></th>
            <th scope="col"><spring:message code="theme"/></th>
            <th scope="col"><spring:message code="duration"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${courses}" var="course">
            <tr>
                <td>${course.name}</td>
                <td>${course.teacher.fullName}</td>
                <td>${course.theme}</td>
                <td>${course.duration}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <span style="float: right">
    <a href="?lang=en">en</a>
    |
    <a href="?lang=ua">ua</a>
</span>

</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>

