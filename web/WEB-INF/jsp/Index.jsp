<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:base_template>
    <jsp:attribute name="extra_head">
        <link href="${pageContext.request.contextPath}/res/style/index.css" rel="stylesheet"/>
    </jsp:attribute>

    <jsp:body>

        <div class="container-fluid">

            <h1 class="text-center">Welcome to Game Dungeon!</h1>

            <div class="links center-block">

                <c:if test="${sessionScope.u_id == null}">
                    <div class="row">
                        <div class="col-sm-6 text-center">
                            <a href="${pageContext.request.contextPath}/login/" class="page-link">
                                <i class="fa fa-unlock-alt fa-5x" aria-hidden="true"></i><br>
                                <span>Login</span>
                            </a>
                        </div>
                        <div class="col-sm-6 text-center">
                            <a href="${pageContext.request.contextPath}/register/" class="page-link">
                                <i class="fa fa-address-card-o fa-5x" aria-hidden="true"></i><br>
                                <span>Register</span>
                            </a>
                        </div>
                    </div>
                </c:if>
                <div class="row">
                    <div class="col-sm-6 text-center">
                        <a href="${pageContext.request.contextPath}/searchPage/" class="page-link">
                            <i class="fa fa-search fa-5x" aria-hidden="true"></i><br>
                            <span>Search Games</span>
                        </a>
                    </div>
                    <div class="col-sm-6 text-center">
                        <a href="${pageContext.request.contextPath}/specials/" class="page-link">
                            <i class="fa fa-tag fa-5x" aria-hidden="true"></i><br>
                            <span>Special Games</span>
                        </a>
                    </div>
                </div>
                <c:if test="${sessionScope.user.isAdmin() == true}">
                    <div class="row">
                        <div class="col-sm-6 text-center">
                            <a href="${pageContext.request.contextPath}/user_management/" class="page-link">
                                <span>User Management</span>
                            </a>
                        </div>
                        <div class="col-sm-6 text-center">
                            <a href="${pageContext.request.contextPath}/purchase_history/" class="page-link">
                                <span>Purchase History</span>
                            </a>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 text-center">
                            <a href="${pageContext.request.contextPath}" class="page-link">
                                <span>Manage Specials Game</span>
                            </a>
                        </div>
                        <div class="col-sm-6 text-center">
                            <a href="${pageContext.request.contextPath}/inventory/" class="page-link">
                                <span>Inventory Management</span>
                            </a>
                        </div>
                    </div>
                </c:if>
            </div>

        </div>

    </jsp:body>
</t:base_template>

