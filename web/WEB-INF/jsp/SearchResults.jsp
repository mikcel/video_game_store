<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<t:base_template>
    <jsp:attribute name="extra_head">
        <link href="${pageContext.request.contextPath}/res/style/searchResults.css" rel="stylesheet"/>
    </jsp:attribute>
    <jsp:body>
        <%--@elvariable id="gamesFound" type="controller.SearchResultsServlet"--%>
        <div class="main-container container-fluid">


            <c:choose>
                <c:when test="${fn:length(gamesFound) == 0}">
                    No Results Found
                </c:when>
                <c:otherwise>

                    <c:forEach items="${gamesFound}" var="game" varStatus="status">
                        <div class="col-lg-6 game-details-box">

                            <div class="game_details row">

                                <div class="game-div-img col-sm-4">
                                    <c:choose>
                                        <c:when test="${game.frontBoxArt != ''}">
                                            <a href="${pageContext.request.contextPath}/game?game_id=${game.id}" target="_blank">
                                                <img src="${pageContext.request.contextPath}/res/img/games/${game.frontBoxArt}"
                                                     class="img-thumbnail game-front-img"/>
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <img src="${pageContext.request.contextPath}/res/img/games/not_available.jpg"
                                                 class="img-thumbnail game-front-img"/>
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                                <div class="col-sm-8">
                                    <div class="game-desc">
                                        <a href="${pageContext.request.contextPath}/game?game_id=${game.id}" target="_blank">
                                            <h5 class="game-title">${game.name}</h5>
                                        </a>
                                        <!-- TODO: Add rating -->
                                        <div class="game-price">
                                            $ <fmt:formatNumber type="number" minFractionDigits="2" minIntegerDigits="1"
                                                                value="${game.price}"/>
                                            <!-- TODO: if discount cu price -->
                                        </div>
                                        <div class="game-console-wrapper">
                                            <span class="font-weight-bold">Console:</span>
                                            <span class="game-console">${game.console}</span>
                                        </div>
                                        <div class="game-nplayers-wrapper">
                                            <span class="font-weight-bold"># Players:</span>
                                            <span class="game-nplayers">${game.numPlayers}</span>
                                        </div>
                                        <div class="game-coop-wrapper">
                                            <span class="font-weight-bold">Co op:</span>
                                            <span class="game-coop">${game.coop}</span>
                                        </div>
                                        <div class="game-short-desc">${game.description}</div>
                                    </div>
                                </div>

                            </div>

                        </div>
                    </c:forEach>

                </c:otherwise>
            </c:choose>

            <a class="form-control btn btn-primary" id="btn-search" href="${pageContext.request.contextPath}/searchPage/">
                <i class="fa fa-search"></i>&emsp;Search Again
            </a>

        </div>
    </jsp:body>
</t:base_template>