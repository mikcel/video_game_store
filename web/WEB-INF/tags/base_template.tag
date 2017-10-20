<%@tag description="Wrapper for template" pageEncoding="UTF-8" %>
<%@attribute name="extra_head" fragment="true" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Game Dungeon</title>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
    <link href="${pageContext.request.contextPath}/res/common_static/bootstrap/bootstrap-readable-theme.min.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/res/common_static/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>

    <script type="text/javascript" src="${pageContext.request.contextPath}/res/common_static/jquery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/res/common_static/bootstrap/bootstrap.min.js"></script>

    <jsp:invoke fragment="extra_head"/>

</head>

<body>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <a class="navbar-brand" href="../../">Games Dungeon</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
                <!--  <li class="dropdown"><a class="dropdown-toggle"
                    data-toggle="dropdown-menu" role="button" aria-haspopup="true"
                    aria-expanded="false">Dropdown <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">Action</a></li>
                        <li><a href="#">Another action</a></li>
                        <li><a href="#">Something else here</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#">Separated link</a></li>
                    </ul></li>-->
                <li><a href="${pageContext.request.contextPath}">Home</a></li>
                <li><a href="#">Sign in</a></li>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container-fluid --> </nav>

<jsp:doBody/>

</body>
</html>