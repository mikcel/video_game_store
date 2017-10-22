<%@ page contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:base_template>
	<jsp:body>
		<h1>Welcome to Game Dungeon!</h1>

		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">Links</h3>
			</div>
			<div class="panel-body">
				<c:if test="${sessionScope.u_id == null}">
					<a href="login/" class="page-link">Login</a><br>
					<a href="register/" class="page-link">Register</a><br>
				</c:if>
				<a href="searchPage/" class="page-link">Search Games</a>
			</div>
		</div>
	</jsp:body>
</t:base_template>

