<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:base_template>
    <jsp:attribute name="extra_head">
        <script type="text/javascript" src="${pageContext.request.contextPath}/res/script/favorites.js"></script>

        <link href="${pageContext.request.contextPath}/res/style/favorites.css" rel="stylesheet"/>
    </jsp:attribute>
    <jsp:body>
        <%--@elvariable id="favorites" type="controller.FavoritesServlet"--%>
        <h2>Favorites</h2>

        <c:choose>
            <c:when test="${favorites.size() == 0}">
                <div class="div-no-results container">
                    <h4>No Favorites Found</h4>
                </div>
            </c:when>
            <c:otherwise>
                <table id="tbl_favorites" class="table">
                    <thead>
                    <tr>
                        <th>Game Name</th>
                        <th>Price</th>
                        <th>Discounted Price</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${favorites}" var="game" varStatus="status">
                        <tr id="tr_game_${game.id}">
                            <td>
                                <a href="${pageContext.request.contextPath}/game?game=${game.id}"
                                   target="_blank">
                                    <span class="game-title">${game.name}</span>
                                </a>
                            </td>
                            <td>
                                $
                                <span class="game-price">
                                        <fmt:formatNumber type="number" minFractionDigits="2"
                                                          minIntegerDigits="1"
                                                          maxFractionDigits="2"
                                                          value="${game.price}"/>
                                    </span>
                            </td>
                            <td>
                                $
                                <span class="spn-discounted-price">
                                          <fmt:formatNumber type="number"
                                                            minFractionDigits="2"
                                                            minIntegerDigits="1"
                                                            maxFractionDigits="2"
                                                            value="${game.price - game.discount}"/>
                                    </span>
                            </td>
                            <td>
                                <button  class="btn btn-primary" onclick="remove_favorite(${game.getId()})">
                                    Remove Favorite
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
