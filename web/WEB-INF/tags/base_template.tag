<%@tag description="Wrapper for template" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@attribute name="extra_head" fragment="true" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Game Dungeon</title>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
    <link href="${pageContext.request.contextPath}/res/common_static/bootstrap/bootstrap-readable-theme.min.css"
          rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/res/common_static/font-awesome/css/font-awesome.min.css"
          rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/res/style/baseStyle.css" rel="stylesheet" type="text/css"/>

    <script type="text/javascript"
            src="${pageContext.request.contextPath}/res/common_static/jquery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/res/common_static/bootstrap/bootstrap.min.js"></script>

    <jsp:invoke fragment="extra_head"/>

</head>

<body>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <a class="navbar-brand" href="../../">
                <span>
                    <i class="fa fa-gamepad" aria-hidden="true"></i>&nbsp;
                    Games Dungeon
                </span>
            </a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-right">

                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
                        Links
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="../../">Home</a></li>
                        <c:if test="${sessionScope.u_id == null}">
                            <li><a href="${pageContext.request.contextPath}/register/">Register</a></li>
                        </c:if>
                        <li><a href="${pageContext.request.contextPath}/searchPage/">Search Page</a></li>
                        <li><a href="${pageContext.request.contextPath}/specials/">Specials Page</a></li>
                    </ul>
                </li>

                <c:choose>
                    <c:when test="${sessionScope.u_id != null}">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                               aria-expanded="false">
                                <i class="fa fa-user-circle-o"></i> ${sessionScope.u_first_name} <span class="caret">
                            </span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="${pageContext.request.contextPath}/logout/">Log out</a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/cart_game/">
                                <i class="fa fa-shopping-cart" aria-hidden="true"></i>&nbsp;
                                <span id="cart-items-amt" class="badge badge-pill badge-primary">
                                    <c:choose>
                                        <c:when test="${sessionScope.shopping_cart != null}">
                                            ${sessionScope.shopping_cart.getNoItems()}
                                        </c:when>
                                        <c:when test="${sessionScope.shopping_cart == null}">
                                            0
                                        </c:when>
                                    </c:choose>
                                </span>
                            </a>
                        </li>
                    </c:when>
                    <c:when test="${sessionScope.u_id == null}">
                        <li><a href="${pageContext.request.contextPath}/login/">Sign in</a></li>
                    </c:when>
                </c:choose>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container-fluid -->
</nav>

<jsp:doBody/>

</body>
</html>