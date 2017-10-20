<%@ page contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:base_template>
	<jsp:body>
        <h1>Login Page</h1>
        <form role="form">
			<div class="form-group">
				<div class="input-group">
					<input type="text" class="form-control" id="u-login" placeholder="Login">
					<label for="u-login"
				class="input-group-addon glyphicon glyphicon-user"></label>
				</div>
			</div> <!-- /.form-group -->

			<div class="form-group">
				<div class="input-group">
					<input type="password" class="form-control" id="u-password" placeholder="Password">
					<label for="u-password"
				class="input-group-addon glyphicon glyphicon-lock"></label>
				</div> <!-- /.input-group -->
			</div> <!-- /.form-group -->
		</form>
    </jsp:body>
</t:base_template>