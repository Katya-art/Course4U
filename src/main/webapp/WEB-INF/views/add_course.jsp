<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    <title><spring:message code="addTeacher"/></title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">


    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

</head>

<body>

<div class="container">

    <form:form method="POST" modelAttribute="courseForm" class="form-signin">
        <h2 class="form-signin-heading"><spring:message code="newCourse"/></h2>
        <spring:bind path="name">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <spring:message code="courseName" var="name"/>
                <form:input type="text" path="name" class="form-control" placeholder="${name}"
                            autofocus="true"/>
                <form:errors path="name"/>
            </div>
        </spring:bind>

        <spring:bind path="theme">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <spring:message code="theme" var="theme"/>
                <form:input type="text" path="theme" class="form-control" placeholder="${theme}"/>
                <form:errors path="theme"/>
            </div>
        </spring:bind>

        <spring:bind path="duration">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <spring:message code="duration" var="duration"/>
                <form:input type="text" path="duration" class="form-control" placeholder="${duration}"/>
                <form:errors path="duration"/>
            </div>
        </spring:bind>

        <spring:bind path="teacherName">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:select path="teacherName">
                    <form:option value="NONE"><spring:message code="selectTeacher"/></form:option>
                    <c:forEach items="${teachers}" var="teacher">
                        <form:option value="${teacher.fullName}">${teacher.fullName}</form:option>
                    </c:forEach>
                </form:select>
                <br>
                <form:errors path="teacherName"/>
            </div>
        </spring:bind>

        <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="save"/></button>
    </form:form>

</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
