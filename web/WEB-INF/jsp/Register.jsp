<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:base_template>
	<jsp:attribute name="extra_head">
        <link href="${pageContext.request.contextPath}/res/style/register.css" rel="stylesheet"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/res/script/registerPage.js"></script>
	</jsp:attribute>
    <jsp:body>
        <div class="form-wrapper">
            <h2>User Registration Form</h2>
            <form id="form-register-user" method="post" action="${pageContext.request.contextPath}/register/">

                <div class="row">
                    <div class="form-group col-md-4">
                        <label for="ipt-first-name">First Name *</label>
                        <input type="text" class="form-control" id="ipt-first-name" name="first_name" placeholder="Type Here" />
                    </div>
                    <div class="form-group col-md-4">
                        <label for="ipt-last-name">Last Name *</label>
                        <input class="form-control" id="ipt-last-name" name="last_name" type="text" placeholder="Type Here" />
                    </div>
                    <div class="form-group col-md-4">
                        <label for="ipt-email">Email *</label>
                        <input type="email" class="form-control" id="ipt-email" name="email" placeholder="email@games.com" />
                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-md-6">
                        <label for="ipt-password">Password *</label>
                        <input type="password" class="form-control" id="ipt-password" name="password" placeholder="password" />
                    </div>
                    <div class="form-group col-md-6">
                        <label for="ipt-conf-pass">Confirm Password *</label>
                        <input type="password" class="form-control" id="ipt-conf-pass" placeholder="password" />
                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-md-4">
                        <label for="ipt-addr1">Address 1</label>
                        <input type="text" class="form-control" id="ipt-addr1" name="address1"/>
                    </div>
                    <div class="form-group col-md-4">
                        <label for="ipt-addr2">Address 2</label>
                        <input type="text" class="form-control" id="ipt-addr2" name="address2"/>
                    </div>
                    <div class="form-group col-md-4">
                        <label for="ipt-city">City</label>
                        <input type="text" class="form-control" id="ipt-city" name="city"/>
                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-md-4">
                        <label for="ipt-state">State</label>
                        <input type="text" class="form-control" id="ipt-state" name="state"/>
                    </div>
                    <div class="form-group col-md-4">
                        <label for="ipt-zip">Zip Code</label>
                        <input type="text" class="form-control" id="ipt-zip" name="zip_code"/>
                    </div>
                    <div class="form-group col-md-4">
                        <label for="ipt-country">Country</label>
                        <input type="text" class="form-control" id="ipt-country" name="country"/>
                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-md-6">
                        <label for="ipt-cc-type">Credit Card Type</label>
                        <input type="text" class="form-control" id="ipt-cc-type" name="cc_type"/>
                    </div>
                    <div class="form-group col-md-6">
                        <label for="ipt-cc-no">Credit Card No.</label>
                        <input type="text" class="form-control" id="ipt-cc-no" name="cc_no"/>
                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-md-6">
                        <label for="ipt-cc-cvv">Credit Card CVV</label>
                        <input type="text" class="form-control" id="ipt-cc-cvv" name="cc_cvv"/>
                    </div>
                    <div class="form-group col-md-6">
                        <label for="ipt-cc-expiry">Credit Card Expiry Date</label>
                        <input type="date" class="form-control" id="ipt-cc-expiry" name="cc_expiry"/>
                    </div>
                </div>

                <small>* Required Fields</small>
                <div class="div-btns">
                    <button id="register_user" class="btn btn-primary" type="submit">Register</button>
                    <button id="reset_form" class="btn btn-default" type="reset">Clear</button>
                </div>

            </form>
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
