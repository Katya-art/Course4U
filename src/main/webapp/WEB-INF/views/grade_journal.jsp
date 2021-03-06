<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Journal</title>
    <!--to show icons-->
    <!--link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"-->
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="elements/navbar.jsp"/>
<div class="container">
    <form method="POST" action="${contextPath}/save_journal/${courseId}">
        <table class="table">
            <thead class="thead-dark">
            <tr>
                <th scope="col"><spring:message code="studentName"/></th>
                <th scope="col"><spring:message code="mark"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${userCourseGrades}" var="userCourseGrade">
                <tr>
                    <input type="hidden" name="students" value="${userCourseGrade.student.id}" />
                    <td>${userCourseGrade.student.fullName}</td>
                    <td>
                        <select id="grades" name="grades">
                            <c:forEach items="${grades}" var="grade">
                                <c:choose>
                                    <c:when test="${grade eq userCourseGrade.grade}">
                                        <option value="${grade}"
                                                selected="selected">${grade}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${grade}">${grade}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="save"/></button>
    </form>

</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>

