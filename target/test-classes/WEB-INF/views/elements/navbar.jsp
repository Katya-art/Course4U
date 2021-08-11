<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<nav class="navbar navbar-default navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">Course4U</a>
            <ul class="navbar-nav">
                <sec:authorize access="hasRole('ADMIN')">
                    <a class="navbar-brand" href="${pageContext.request.contextPath}/add_teacher">
                        <spring:message code="addTeacher"/></a>
                </sec:authorize>
            </ul>
        </div>
        <div class="navbar-header navbar-right">
            <sec:authorize access="isAnonymous()">
                <a class="navbar-brand" href="${pageContext.request.contextPath}/login">
                    <spring:message code="login"/></a>
                <a class="navbar-brand" href="${pageContext.request.contextPath}/registration">
                    <spring:message code="register"/></a>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">

                <form id="logoutForm" method="POST" action="${pageContext.request.contextPath}/logout">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
                <a class="navbar-brand" onclick="document.forms['logoutForm'].submit()">
                    <spring:message code="logout"/></a>
            </sec:authorize>
        </div>
    </div>
</nav>