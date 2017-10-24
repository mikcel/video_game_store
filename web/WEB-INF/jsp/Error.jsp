<%@ page contentType="text/html; charset=ISO-8859-1" isErrorPage="true"
		 pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:base_template>
	<jsp:body>
		<h3>Error while processing request</h3>
		${exception}
	</jsp:body>
</t:base_template>