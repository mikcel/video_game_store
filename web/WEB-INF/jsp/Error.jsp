<%@ page contentType="text/html; charset=ISO-8859-1" isErrorPage="true"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:base_template>
	<jsp:body>
		<h3 class="text-center">Error while processing request</h3>
		<p>${pageContext.errorData.throwable}</p>
	</jsp:body>
</t:base_template>