<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="ISO-8859-1" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:base_template>
    <jsp:attribute name="extra_head">
        <link href="${pageContext.request.contextPath}/res/style/login.css" rel="stylesheet" type="text/css"/>
        <script type="text/javascript" src="${pageContext.request.contextPath}/res/script/login.js"></script>
    </jsp:attribute>
    <jsp:body>
        <h1>Login Page</h1>
        <form id="form-login" role="form" method="post" action="">
            <div class="form-group">
                <div class="input-group">
                    <label for="u-login" class="input-group-addon">
                        <i class="fa fa-envelope-o"></i>
                    </label>
                    <input type="text" class="form-control" id="u-login" placeholder="Login">
                </div>
            </div>

            <div class="form-group">
                <div class="input-group">
                    <label for="u-login" class="input-group-addon">
                        <i class="fa fa-lock"></i>
                    </label>
                    <input type="password" class="form-control" id="u-password" placeholder="Password">
                </div>
            </div>

            <button type="submit" id="btn-submit" class="btn btn-primary">Login</button>
            <button type="reset" id="btn-reset" class="btn btn-default">Clear</button>

        </form>
    </jsp:body>
</t:base_template>