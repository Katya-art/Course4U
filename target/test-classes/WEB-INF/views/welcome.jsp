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

    <title>Welcome</title>
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
            <th scope="col"><spring:message code="teacherName"/></th>
            <th scope="col"><spring:message code="theme"/></th>
            <th scope="col"><spring:message code="duration"/></th>
            <th scope="col"><spring:message code="numberOfStudents"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${coursesList}" var="course">
            <c:if test="${course.status.id != 3}">
            <tr>
                <td>${course.name}</td>
                <td>${course.teacher.fullName}</td>
                <td>${course.theme}</td>
                <td>${course.duration}</td>
                <td>${course.studentsMarks.size()}</td>
                <sec:authorize access="hasRole('ADMIN')">
                    <td><a href="${pageContext.request.contextPath}/edit_course/${course.id}"
                           class="btn btn-info mt-4"><spring:message code="edit"/></a></td>
                    <td><a href="${pageContext.request.contextPath}/delete_course/${course.id}"
                           class="btn btn-danger mt-4"><spring:message code="delete"/></a></td>
                </sec:authorize>
                <sec:authorize access="hasRole('STUDENT')">
                    <c:set var="contains" value="false"/>
                    <c:forEach var="student" items="${course.studentsMarks.keySet()}">
                        <c:if test="${student.username eq pageContext.request.userPrincipal.name}">
                            <c:set var="contains" value="true"/>
                        </c:if>
                    </c:forEach>
                    <c:choose>
                    <c:when test="${!contains}">
                        <td><a href="${pageContext.request.contextPath}/enroll_course/${course.id}"
                               class="btn btn-primary mt-4"><spring:message code="enroll"/></a></td>
                    </c:when>
                        <c:otherwise>
                            <td><spring:message code="alreadyEnrolled"/></td>
                        </c:otherwise>
                    </c:choose>
                </sec:authorize>
            </tr>
            </c:if>
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
