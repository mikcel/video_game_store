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
                    <label for="user_email" class="input-group-addon">
                        <i class="fa fa-envelope-o"></i>
                    </label>
                    <input type="text" class="form-control" id="user_email" placeholder="Login" name="user_email">
                </div>
            </div>

            <div class="form-group">
                <div class="input-group">
                    <label for="user_email" class="input-group-addon">
                        <i class="fa fa-lock"></i>
                    </label>
                    <input type="password" class="form-control" id="user_password" placeholder="Password" name="user_password">
                </div>
            </div>

            <button type="submit" id="btn-submit" class="btn btn-primary">Login</button>
            <button type="reset" id="btn-reset" class="btn btn-default">Clear</button>

        </form>

        <div id="msg-modal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 id="msg-title" class="modal-title"></h4>
                    </div>
                    <div class="modal-body">
                        <p id="msg-body"></p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">OK</button>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:base_template>