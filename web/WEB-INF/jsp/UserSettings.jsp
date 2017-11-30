<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:base_template>
	<jsp:attribute name="extra_head">
        <link href="${pageContext.request.contextPath}/res/style/userPage.css" rel="stylesheet"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/res/script/userPage.js"></script>
	</jsp:attribute>
    <jsp:body>
        <div class="form-wrapper">
            <h2 class="main-header">User Settings Page</h2>

            <div id="div-btns">
                <a type="button" class="btn btn-subaction btn-lg" href="${pageContext.request.contextPath}/u_profile_settings/">
                    <i class="fa fa-pencil-square-o fa-4x" aria-hidden="true"></i>
                    <br>
                    Change Info
                </a>
                <a type="button" class="btn btn-subaction btn-lg" data-toggle="modal" data-target="#reset-password-modal">
                    <i class="fa fa-key fa-4x" aria-hidden="true"></i>
                    <br>
                    Reset Password
                </a>
                <a type="button" class="btn btn-subaction btn-lg" href="${pageContext.request.contextPath}/u_purchase_history/">
                    <i class="fa fa-history fa-4x" aria-hidden="true"></i>
                    <br>
                    My Purchase History
                </a>
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

        <div id="reset-password-modal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="reset-pass-heading">Reset Password</h4>
                    </div>
                    <div class="modal-body">
                        <form>
                            <div class="form-group">
                                <label for="current-pass" class="control-label">Current Password:</label>
                                <input type="password" class="form-control" id="current-pass">
                            </div>
                            <div class="form-group">
                                <label for="new-password" class="control-label">New Password:</label>
                                <input type="password" class="form-control" id="new-password"/>
                            </div>
                            <div class="form-group">
                                <label for="confirm-password" class="control-label">Confirm Password:</label>
                                <input type="password" class="form-control" id="confirm-password"/>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                        <button type="button" class="btn btn-action" onclick="update_password()">Change Password</button>
                    </div>
                </div>
            </div>
        </div>

    </jsp:body>
</t:base_template>
