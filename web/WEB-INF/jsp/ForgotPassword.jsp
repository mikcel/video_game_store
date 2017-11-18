<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="ISO-8859-1" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:base_template>
    <jsp:attribute name="extra_head">
        <link href="${pageContext.request.contextPath}/res/style/forgotPassword.css" rel="stylesheet" type="text/css"/>
        <script type="text/javascript" src="${pageContext.request.contextPath}/res/script/forgotPassword.js"></script>
    </jsp:attribute>
    <jsp:body>
        <div class="form-wrapper center-block">
            <div class="form-block">
                <h2>Forgot Password Page</h2>
                <form id="form-forgot-pass" role="form" method="post" action="">

                    <div class="form-group">
                        <div class="input-group">
                            <label for="user_login" class="input-group-addon">
                                <i class="fa fa-user-circle-o"></i>
                            </label>
                            <input type="text" class="form-control" id="user_login" placeholder="Login Name" name="user_login">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="input-group">
                            <label for="user_email" class="input-group-addon">
                                <i class="fa fa-envelope-o"></i>
                            </label>
                            <input type="text" class="form-control" id="user_email" placeholder="Email" name="user_email">
                        </div>
                    </div>

                    <button type="submit" id="btn-submit" class="btn btn-primary">Send Email</button>
                    <button type="reset" id="btn-reset" class="btn btn-default">Clear</button>

                </form>
            </div>
        </div>

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