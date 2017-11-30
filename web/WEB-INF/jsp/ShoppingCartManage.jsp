<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:base_template>
    <jsp:attribute name="extra_head">
        <script type="text/javascript" src="${pageContext.request.contextPath}/res/script/cartManage.js"></script>
        <link href="${pageContext.request.contextPath}/res/style/cartManage.css" rel="stylesheet"/>
    </jsp:attribute>
    <jsp:body>
        <%--@elvariable id="gamesFound" type="controller.SearchResultsServlet"--%>
        <div class="main-container container-fluid">

            <h2 class="main-header">Shopping Cart Management</h2>

            <c:choose>
                <c:when test="${sessionScope.shopping_cart == null}">
                    <div class="div-empty-cart container">
                        <h4 class="main-header">Empty Shopping Cart</h4>
                    </div>
                </c:when>
                <c:otherwise>

                    <h3 class="main-header">Games in Cart</h3>

                    <table id="tbl-cart-game" class="table game-details-box table-hover">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Price</th>
                            <th>Discounted Price</th>
                            <th class="col-md-2">Qty</th>
                            <th>Total Price</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${sessionScope.shopping_cart.getGames()}" var="cart_game" varStatus="status">
                            <tr id="tr_game_${cart_game.id}">
                                <th class="th-game-name">
                                    <a href="${pageContext.request.contextPath}/game?game=${cart_game.game.id}"
                                       target="_blank">
                                        <span class="game-title">${cart_game.game.name}</span>
                                    </a>
                                </th>
                                <td class="td-game-price">
                                    $
                                    <span class="game-price">
                                        <fmt:formatNumber type="number" minFractionDigits="2"
                                                          minIntegerDigits="1"
                                                          maxFractionDigits="2"
                                                          value="${cart_game.game.price}"/>
                                    </span>
                                </td>
                                <td class="td-discounted-price">
                                    $
                                    <span class="spn-discounted-price">
                                          <fmt:formatNumber type="number"
                                                            minFractionDigits="2"
                                                            minIntegerDigits="1"
                                                            maxFractionDigits="2"
                                                            value="${cart_game.game.price - cart_game.game.discount}"/>
                                    </span>
                                </td>
                                <td class="game-qty col-md-2">
                                    <input type="number" class="form-control ipt-game-qty" value="${cart_game.quantity}"
                                           min="0"
                                           onchange="update_price(${cart_game.id})"/>
                                </td>
                                <td class="game-total-price">
                                    $
                                    <span class="total-price">
                                         <fmt:formatNumber type="number" minFractionDigits="2"
                                                           minIntegerDigits="1"
                                                           maxFractionDigits="2"
                                                           value="${(cart_game.game.price - cart_game.game.discount) * cart_game.quantity}"/>
                                    </span>
                                </td>
                                <td>
                                    <button class="btn btn-save-game btn-subaction" onclick="save_game(${cart_game.id})">
                                        <i class="fa fa-floppy-o" aria-hidden="true"></i>
                                    </button>
                                    <button class="btn btn-save-game btn-subaction" onclick="remove_game(${cart_game.id})">
                                        <i class="fa fa-minus" aria-hidden="true"></i>
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>

                    </table>

                    <div id="cart-info" class="text-center">
                        <button class="btn btn-save-game btn-action" onclick="empty_cart()">
                            Empty Cart
                        </button>
                        <a id="checkout-link" class="btn btn-action" href="${pageContext.request.contextPath}/checkout/">
                            Checkout
                        </a>
                    </div>
                </c:otherwise>
            </c:choose>

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
