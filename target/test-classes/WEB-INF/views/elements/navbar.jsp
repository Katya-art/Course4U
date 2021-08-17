<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-default navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">Course4U</a>
            <ul class="navbar-nav">
                <sec:authorize access="hasRole('ADMIN')">
                    <a class="navbar-brand" href="${pageContext.request.contextPath}/add_teacher">
                        <spring:message code="addTeacher"/></a>
                    <a class="navbar-brand" href="${pageContext.request.contextPath}/add_course">
                        <spring:message code="addCourse"/></a>
                    <a class="navbar-brand" href="${pageContext.request.contextPath}/students_list">
                        <spring:message code="studentsList"/></a>
                </sec:authorize>
                <sec:authorize access="hasRole('STUDENT')">
                    <a class="navbar-brand" href="${pageContext.request.contextPath}/my_courses/not_started">
                        <spring:message code="myCourses"/></a>
                </sec:authorize>
                <sec:authorize access="hasRole('TEACHER')">
                    <a class="navbar-brand" href="${pageContext.request.contextPath}/assigned_courses">
                        <spring:message code="myCourses"/></a>
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
                <c:if test="${pageContext.request.userPrincipal.name != null}">
                    <a class="navbar-brand"
                       href="${pageContext.request.contextPath}/user/${pageContext.request.userPrincipal.name}">
                            ${pageContext.request.userPrincipal.name}</a>
                </c:if>
                <a class="navbar-brand" onclick="document.forms['logoutForm'].submit()">
                    <spring:message code="logout"/></a>
            </sec:authorize>
        </div>
    </div>
</nav>