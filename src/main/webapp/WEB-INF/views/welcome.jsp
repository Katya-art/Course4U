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
    <jsp:include page="elements/navbar.jsp"/>
</head>
<body>
<div class="container">
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">
                <a href="${contextPath}/page/${currentPage}?teacherId=${teacherId}&themeName=${themeName}&sortField=name&sortDir=${reverseSortDir}">
                    <spring:message code="courseName"/></a>
            </th>
            <th scope="col"><spring:message code="teacherName"/></th>
            <th scope="col"><spring:message code="theme"/></th>
            <th scope="col">
                <a href="${contextPath}/page/${currentPage}?teacherId=${teacherId}&themeName=${themeName}&sortField=duration&sortDir=${reverseSortDir}">
                    <spring:message code="duration"/></a>
            </th>
            <th scope="col">
                <a href="${contextPath}/page/${currentPage}?teacherId=${teacherId}&themeName=${themeName}&sortField=numberOfStudents&sortDir=${reverseSortDir}">
                    <spring:message code="numberOfStudents"/></a>
            </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${coursesList}" var="course">
            <c:if test="${course.condition.id != 3}">
                <tr>
                    <td>${course.name}</td>
                    <td><a href="${contextPath}/page/1?teacherId=${course.teacher.id}&themeName=${themeName}
                    &sortField=${sortField}&sortDir=${sortDir}">${course.teacher.fullName}</a></td>
                    <td><a href="${contextPath}/page/1?teacherId=${teacherId}&themeName=${course.theme}
                    &sortField=${sortField}&sortDir=${sortDir}">${course.theme}</a></td>
                    <td>${course.duration}</td>
                    <td>${course.numberOfStudents}</td>
                    <sec:authorize access="hasRole('ADMIN')">
                        <td><a href="${pageContext.request.contextPath}/edit_course/${course.id}?page=${currentPage}&teacherId=${teacherId}&themeName=${themeName}&sortField=${sortField}&sortDir=${sortDir}"
                               class="btn btn-info mt-4"><spring:message code="edit"/></a></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/delete_course/${course.id}?page=${currentPage}&teacherId=${teacherId}&themeName=${themeName}&sortField=${sortField}&sortDir=${sortDir}"
                               class="btn btn-danger mt-4"><spring:message code="delete"/></a></td>
                    </sec:authorize>
                    <c:set var="contains" value="false"/>
                    <c:set var="status" value="1"/>
                    <c:if test="${pageContext.request.userPrincipal.name != null}">
                        <c:forEach var="student" items="${course.studentsMarks.keySet()}">
                            <c:if test="${student.username eq pageContext.request.userPrincipal.name}">
                                <c:set var="contains" value="true"/>
                            </c:if>
                        </c:forEach>
                        <c:set var="status" value="${user.status.id}"/>
                    </c:if>
                    <c:choose>
                        <c:when test="${!contains && status == 1}">
                            <td><a href="${pageContext.request.contextPath}/enroll_course/${course.id}?page=${currentPage}&teacherId=${teacherId}&themeName=${themeName}&sortField=${sortField}&sortDir=${sortDir}"
                                   class="btn btn-primary mt-4"><spring:message code="enroll"/></a></td>
                        </c:when>
                        <c:when test="${status == 2}">
                            <td><spring:message code="yourAccountWasBlocked"/></td>
                        </c:when>
                        <c:otherwise>
                            <sec:authorize access="hasRole('STUDENT')">
                                <td><spring:message code="alreadyEnrolled"/></td>
                            </sec:authorize>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:if>
        </c:forEach>
        </tbody>
    </table>

    <c:if test="${totalPages > 1}">
        <div class="row col-sm-10">
            <!--div class="col-sm-2">
                <spring:message code="totalRows"/>: ${totalItems}
            </div-->
            <div class="col-sm-1">
                <c:forEach var="i" begin="1" end="${totalPages}">
                    <c:choose>
                        <c:when test="${currentPage != i}"><a
                                href="${contextPath}/page/${i}?teacherId=${teacherId}&themeName=${themeName}&sortField=${sortField}&sortDir=${sortDir}">${i}</a></c:when>
                        <c:otherwise>${i}</c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>
            <div class="col-sm-1">
                <c:choose>
                    <c:when test="${currentPage < totalPages}"><a
                            href="${contextPath}/page/${currentPage + 1}?teacherId=${teacherId}&themeName=${themeName}&sortField=${sortField}&sortDir=${sortDir}">
                        <spring:message code="next"/></a> </c:when>
                    <c:otherwise><spring:message code="next"/></c:otherwise>
                </c:choose>
            </div>
            <div class="col-sm-1">
                <c:choose>
                    <c:when test="${currentPage < totalPages}"><a
                            href="${contextPath}/page/${totalPages}?teacherId=${teacherId}&themeName=${themeName}&sortField=${sortField}&sortDir=${sortDir}">
                        <spring:message code="last"/></a></c:when>
                    <c:otherwise><spring:message code="last"/></c:otherwise>
                </c:choose>
            </div>
        </div>
    </c:if>

    <span style="float: right">
    <a href="?teacherId=${teacherId}&themeName=${themeName}&sortField=${sortField}&sortDir=${sortDir}&locale=en">en</a>
    |
    <a href="?teacherId=${teacherId}&themeName=${themeName}&sortField=${sortField}&sortDir=${sortDir}&locale=ua">ua</a>
</span>

</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
