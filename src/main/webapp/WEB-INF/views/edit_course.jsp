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

    <title><spring:message code="edit"/></title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">


    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

</head>

<body>

<div class="container">
    <h2 class="form-signin-heading"><spring:message code="edit"/></h2>
    <form:form method="POST" modelAttribute="editForm">
        <table class="table">
            <tbody>
            <tr>
                <td><spring:message code="courseName"/></td>
                <td>
                    <spring:bind path="courseName">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input type="text" path="courseName" class="form-control" autofocus="true"/>
                            <form:errors path="courseName"/>
                        </div>
                    </spring:bind>
                </td>
            </tr>
            <tr>
                <td><spring:message code="courseTheme"/></td>
                <td>
                    <spring:bind path="courseTheme">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input type="text" path="courseTheme" class="form-control"/>
                            <form:errors path="courseTheme"/>
                        </div>
                    </spring:bind>
                </td>
            </tr>
            <tr>
                <td><spring:message code="duration"/></td>
                <td>
                    <spring:bind path="duration">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input type="text" path="duration" class="form-control"/>
                            <form:errors path="duration"/>
                        </div>
                    </spring:bind>
                </td>
            </tr>
            <tr>
                <td><spring:message code="teacherName"/></td>
                <td>
                    <spring:bind path="teacherName">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:select path="teacherName">
                                <c:forEach items="${teachers}" var="teacherFullName">
                                    <form:option value="${teacherFullName.fullName}">${teacherFullName.fullName}</form:option>
                                </c:forEach>
                            </form:select>
                            <br>
                            <form:errors path="teacherName"/>
                        </div>
                    </spring:bind>
                </td>
            </tr>
            </tbody>
        </table>
        <button class="btn btn-lg btn-primary btn-block" style="width: 13%; float: right" type="submit">
            <spring:message code="save"/>
        </button>
    </form:form>

</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>

