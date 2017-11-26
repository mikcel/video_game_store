<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:base_template>
    <jsp:attribute name="extra_head">
        <script type="text/javascript" src="${pageContext.request.contextPath}/res/common_static/datatables/datatables.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/res/script/userManagement.js"></script>

        <link href="${pageContext.request.contextPath}/res/common_static/datatables/datatables.min.css" rel="stylesheet"/>
        <link href="${pageContext.request.contextPath}/res/style/userManagement.css" rel="stylesheet"/>
    </jsp:attribute>
    <jsp:body>

        <%--@elvariable id="users" type="controller.UserManagementServlet"--%>
        <h2>User Management</h2>

        <c:choose>
            <c:when test="${users.size() == 0}">
                <div class="div-no-results container">
                    <h4>No Users Found</h4>
                </div>
            </c:when>
            <c:otherwise>
                <h4>
                    # of users to date: ${users.size()}
                </h4>

                <table id="tbl_users" class="table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Login Name</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Email</th>
                        <th>Last Login</th>
                        <th>Is Admin</th>
                        <th>Login Attempts</th>
                        <th>Locked Account</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${users}" var="user" varStatus="status">
                        <tr>
                            <td>
                                    ${user.getId()}
                            </td>
                            <td>
                                    ${user.getLogin_name()}
                            </td>
                            <td>
                                    ${user.getFirstName()}
                            </td>
                            <td>
                                    ${user.getLastName()}
                            </td>
                            <td>
                                    ${user.getEmail()}
                            </td>
                            <td>
                                <fmt:formatDate type="date"  pattern="dd-MM-yyyy HH:mm:ss" value = "${user.getLastLogin()}"/>
                            </td>
                            <td>
                                    ${user.isAdmin()}
                            </td>
                            <td>
                                    ${user.getLogin_attempts()}
                            </td>
                            <td>
                                    ${user.getLocked()}
                            </td>
                            <td>
                                <button  class="btn btn-primary" onclick="unlock_lock_account(${user.getId()})">
                                    Unlock/Lock
                                </button>
                                <button  class="btn btn-primary" onclick="set_admin(${user.getId()})">
                                    Set Admin
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>

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