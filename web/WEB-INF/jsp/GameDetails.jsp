<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:base_template>
    <jsp:attribute name="extra_head">
        <link href="${pageContext.request.contextPath}/res/style/gameDetails.css" rel="stylesheet"/>
    </jsp:attribute>
    <jsp:body>

        <%--@elvariable id="game" type="controller.GameDetailsServlet"--%>
        <div class="main-container container-fluid">
            <div class="game-header">
                <c:if test="${game.logo != ''}">
                    <img src="${pageContext.request.contextPath}/res/img/games/${game.frontBoxArt}"
                         class="img-circle game-logo"/>
                </c:if>

                <h2 class="game-title">#${game.id}&emsp;${game.name}</h2>
            </div>
            <br>
            <div class="game-details-wrapper">
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
                        <div class="col-sm-6">
                            <div class="game-price-wrapper row">
                                <span class="spn-label col-sm-6">Price:</span>
                                <span class="col-sm-6">
                                    $&nbsp;<span
                                        class="game-price <c:if test="${game.discount != 0}">discounted-game</c:if>">
                                         <fmt:formatNumber type="number" minFractionDigits="2" minIntegerDigits="1"
                                                           value="${game.price}"/>
                                    </span>
                                    <c:if test="${game.discount != 0}">
                                        <span class="spn-discount">
                                            &nbsp;
                                            <fmt:formatNumber type="number" minFractionDigits="2"
                                                              minIntegerDigits="1"
                                                              maxFractionDigits="2"
                                                              value="${game.price - game.discount}"/>
                                        </span>
                                    </c:if>
                                </span>
                            </div>
                            <div class="game-console-wrapper row">
                                <span class="spn-label col-sm-6">Console:</span>
                                <span class="game-console col-sm-6">${game.console}</span>
                            </div>
                            <div class="game-nplayers-wrapper row">
                                <span class="spn-label col-sm-6"># Players:</span>
                                <span class="game-nplayers col-sm-6">${game.numPlayers}</span>
                            </div>
                            <div class="game-coop-wrapper row">
                                <span class="spn-label col-sm-6">Co op:</span>
                                <span class="game-coop col-sm-6">${game.coop}</span>
                            </div>
                            <div class="game-genre-wrapper row">
                                <span class="spn-label col-sm-6">Genre:</span>
                                <span class="game-genre col-sm-6">${game.genre}</span>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="game-release-date-wrapper row">
                                <span class="spn-label col-sm-6">Release Date:</span>
                                <span class="game-release-date col-sm-6">${game.releaseDate}</span>
                            </div>
                            <div class="game-publisher-wrapper row">
                                <span class="spn-label col-sm-6">Publisher:</span>
                                <span class="game-publisher col-sm-6">${game.publisher}</span>
                            </div>
                            <div class="game-developer-wrapper">
                                <span class="spn-label">Developer:</span>
                                <div class="div-lbl-logo">
                                    <span class="game-developer">${game.developer}</span>
                                    <c:choose>
                                        <c:when test="${game.developerLogo != ''}">
                                            <img src="${pageContext.request.contextPath}/res/img/${game.developerLogo}"
                                                 onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/res/img/games/not_available.jpg';"
                                                 class="img-thumbnail game-developer-logo"/>
                                        </c:when>
                                        <c:otherwise>
                                            <img src="${pageContext.request.contextPath}/res/img/games/not_available.jpg"
                                                 class="img-thumbnail game-developer-logo"/>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                        <div class="game-desc-wrapper col-sm-12">
                            <span class="spn-label">Description:</span>
                            <p class="game-description">${game.description}</p>
                        </div>
                        <div class="game-comment-wrapper col-sm-12">
                            <h6>Comments:</h6>
                            <c:forEach items="${game.comments}" var="comment">
                                <div id="comment-${comment.id}" class="div-comment col-sm-6">
                                    <div class="div-comment-user">
                                        <span class="spn-label">${comment.user.firstName}&nbsp;${comment.user.lastName}</span>
                                        &nbsp;
                                        <span class="spn-rating-cmt">
                                            <c:forEach var="i" begin="1" end="${comment.ratings}">
                                                <i class="fa fa-star" aria-hidden="true"></i>
                                            </c:forEach>
                                            <c:forEach var="i" begin="${comment.ratings + 1}" end="5">
                                                <i class="fa fa-star-o" aria-hidden="true"></i>
                                            </c:forEach>
                                        </span>
                                    </div>
                                    <div class="div-comment-date">
                                        <fmt:formatDate value="${comment.commentDate}" type="both" dateStyle="medium"
                                                        timeStyle="medium"/>
                                    </div>
                                    <div class="div-comment-details">
                                            ${comment.commentDetails}
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:base_template>
