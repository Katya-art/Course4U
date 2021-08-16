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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="elements/navbar.jsp"/>
<div class="container">
    <h2 style="text-align: center">${user.username}</h2>
    <h4 style="text-align: center">${user.role.name.replace("ROLE_", "")}</h4>
    <table class="table">
        <tbody>
        <tr>
            <td><spring:message code="fullName"/></td>
            <td>${user.fullName}</td>
        </tr>
        <tr>
            <td><spring:message code="email"/></td>
            <td>${user.email}</td>
        </tr>
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
