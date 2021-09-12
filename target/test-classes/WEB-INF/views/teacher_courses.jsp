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
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col"><spring:message code="courseName"/></th>
            <th scope="col"><spring:message code="numberOfStudents"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${courses}" var="course">
            <tr>
                <td>${course.name}</td>
                <td>${course.studentsMarks.size()}</td>
                <c:choose>
                    <c:when test="${course.condition.id == 1}">
                        <td><a href="${pageContext.request.contextPath}/start_course/${course.id}"
                               class="btn btn-primary mt-4"><spring:message code="startCourse"/></a></td>
                    </c:when>
                    <c:when test="${course.condition.id == 2}">
                        <td><a href="${pageContext.request.contextPath}/grade_journal/${course.id}"
                               class="btn btn-primary mt-4"><spring:message code="gradeJournal"/></a></td>
                        <td><a href="${pageContext.request.contextPath}/finish_course/${course.id}"
                               class="btn btn-primary mt-4"><spring:message code="finishCourse"/></a></td>
                    </c:when>
                </c:choose>
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
