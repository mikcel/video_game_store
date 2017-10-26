<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<t:base_template>
    <jsp:attribute name="extra_head">
        <link href="${pageContext.request.contextPath}/res/style/gameDetails.css" rel="stylesheet"/>
    </jsp:attribute>
    <jsp:body>

        <%--@elvariable id="game" type="controller.GameDetailsServlet"--%>
        <div class="main-container container-fluid">
            <h2 class="col-sm-12">${game.name}</h2>
            <c:if test="${game.logo != ''}">
                <img src="${pageContext.request.contextPath}/res/img/games/${game.frontBoxArt}"
                     class="img-thumbnail game-front-img"/>
            </c:if>
            <br>
            <div class="game-div-img col-sm-4">
                <c:choose>
                    <c:when test="${game.frontBoxArt != ''}">
                        <img src="${pageContext.request.contextPath}/res/img/games/${game.frontBoxArt}"
                             class="img-thumbnail game-front-img"/>
                    </c:when>
                    <c:otherwise>
                        <img src="${pageContext.request.contextPath}/res/img/games/not_available.jpg"
                             class="img-thumbnail game-front-img"/>
                    </c:otherwise>
                </c:choose>
                <c:if test="${game.backBoxArt != ''}">
                    <img src="${pageContext.request.contextPath}/res/img/games/${game.backBoxArt}"
                         class="img-thumbnail game-front-img"/>
                </c:if>
            </div>
            <div class="game-details col-sm-8">
                <div class="game-desc">
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
                    <div class="game-genre-wrapper">
                        <span class="font-weight-bold">Genre:</span>
                        <span class="game-genre">${game.genre}</span>
                    </div>
                    <div class="game-release-date-wrapper">
                        <span class="font-weight-bold">Release Date:</span>
                        <span class="game-release-date">${game.releaseDate}</span>
                    </div>
                    <div class="game-developer-wrapper row">
                        <div class="col-sm-6">
                            <span class="font-weight-bold">Developer:</span>
                            <span class="game-developer">${game.developer}</span>
                        </div>
                        <div class="col-sm-6">
                            <c:choose>
                                <c:when test="${game.developerLogo != ''}">
                                    <img src="${pageContext.request.contextPath}/res/img/${game.developerLogo}"
                                         class="img-thumbnail game-front-img"/>
                                </c:when>
                                <c:otherwise>
                                    <img src="${pageContext.request.contextPath}/res/img/games/not_available.jpg"
                                         class="img-thumbnail game-front-img"/>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <div class="game-publisher-wrapper">
                        <span class="font-weight-bold">Publisher:</span>
                        <span class="game-publisher">${game.publisher}</span>
                    </div>
                    <div class="game-short-desc">${game.description}</div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:base_template>
