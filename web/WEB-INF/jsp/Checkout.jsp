<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:base_template>
    <jsp:attribute name="extra_head">
        <script type="text/javascript" src="${pageContext.request.contextPath}/res/script/checkout.js"></script>
        <link href="${pageContext.request.contextPath}/res/style/checkout.css" rel="stylesheet"/>
    </jsp:attribute>
    <jsp:body>
        <%--@elvariable id="gamesFound" type="controller.SearchResultsServlet"--%>
        <div class="main-container container-fluid">

            <h2>Checkout</h2>

            <c:choose>
                <c:when test="${sessionScope.shopping_cart == null || sessionScope.shopping_cart.getNoItems() == 0}">
                    <div class="div-empty-cart container">
                        <h4>Empty Shopping Cart</h4>
                    </div>
                </c:when>
                <c:otherwise>

                    <h3 class="text-center">Shopping Cart</h3>

                    <table id="tbl-cart-game" class="table game-details-box">
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
                                    <button class="btn btn-save-game" onclick="save_game(${cart_game.id})">
                                        <i class="fa fa-floppy-o" aria-hidden="true"></i>
                                    </button>
                                    <button class="btn btn-save-game" onclick="remove_game(${cart_game.id})">
                                        <i class="fa fa-minus" aria-hidden="true"></i>
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>

                    </table>

                    <table id="tbl-order-summary" class="table">
                        <caption>Order Summary</caption>
                        <tbody>
                        <tr>
                            <th>
                                Sub Total
                                ( <span id="sub-total-items">
                                    -
                                </span> items)
                            </th>
                            <td>
                                $
                                <span id="sub-total">
                                    -
                                </span>
                            </td>
                        </tr>
                        <tr>
                            <th>Tax</th>
                            <td>
                                $
                                <span id="spn-tax">
                                    -
                                </span>
                            </td>
                        </tr>
                        <tr>
                            <th id="td-order-total">
                                Order Total
                            </th>
                            <td>
                                $
                                <span id="order-total">
                                    -
                                </span>
                            </td>
                        </tr>
                        </tbody>
                    </table>

                    <div id="div-info">
                        <table id="user_perso_info" class="table">
                            <caption>Personal/Billing/Shipping Information</caption>
                            <tr>
                                <th>Email Address: </th>
                                <td>${user.email}</td>
                            </tr>
                            <tr>
                                <th>First Name: </th>
                                <td>${user.firstName}</td>
                            </tr>
                            <tr>
                                <th>Last Name: </th>
                                <td>${user.lastName}</td>
                            </tr>
                            <tr class="billing-info">
                                <th>Billing/Shipping Info:</th>
                                <th>
                                    ${user.address1} ${user.address2}<br>
                                    ${user.city} ${user.state}<br>
                                    ${user.zip} ${user.country}
                                </th>
                            </tr>
                            <tr>
                                <th>Credit Card No: </th>
                                <td>
                                    <c:set var="ccNo">${user.credit_card_number}</c:set>
                                    ************${fn:substring(ccNo, -1, 4)}
                                </td>
                            </tr>
                            <tr>
                                <th>Credit Card CVV: </th>
                                <td>
                                    ${user.credit_card_cvv}
                                </td>
                            </tr>
                            <tr>
                                <th>Credit Card Expiry Date: </th>
                                <td>
                                    <fmt:formatDate type="date"  pattern="MM/yy" value = "${user.credit_card_expiry}"/>
                                </td>
                            </tr>
                            <tr>
                                <th>Credit Card Type: </th>
                                <td>
                                        ${user.credit_card_type}
                                </td>
                            </tr>
                        </table>

                        <button id="btn-proceed" class="btn btn-primary" onclick="process_order()">
                            Process Order
                        </button>

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
