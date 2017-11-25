<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:base_template>
        <jsp:attribute name="extra_head">
        <link href="${pageContext.request.contextPath}/res/style/purchaseHistory.css" rel="stylesheet"/>
    </jsp:attribute>
    <jsp:body>
        <%--@elvariable id="orders" type="controller.UserPurchaseHistoryServlet"--%>
        <h2>Your Purchase History</h2>

        <c:choose>
            <c:when test="${orders.size() == 0}">
                <div class="div-no-results container">
                    <h4>No Results Found</h4>
                </div>
            </c:when>
            <c:otherwise>
                <h4>
                    # of orders to date: ${orders.size()}
                </h4>

                <c:forEach items="${orders}" var="order">
                    <table id="tbl_order_${order.getId()}" class="table">
                        <caption>
                            Order Date:
                            <fmt:formatDate type="date"  pattern="dd-MM-yyyy HH:mm:ss" value = "${order.order_date}"/>
                        </caption>
                        <c:forEach items="${order.getGames()}" var="orderGame" varStatus="status">
                            <tr>
                                <td>
                                    <a href="${pageContext.request.contextPath}/game?game=${orderGame.game.id}"
                                       target="_blank">
                                        ${orderGame.game.name}
                                    </a>
                                </td>
                                <td>
                                    x ${orderGame.quantity}
                                </td>
                                <td>
                                    $
                                    <span class="total-price">
                                 <fmt:formatNumber type="number" minFractionDigits="2"
                                                   minIntegerDigits="1"
                                                   maxFractionDigits="2"
                                                   value="${(orderGame.game.price - orderGame.game.discount) * orderGame.quantity}"/>
                            </span>
                                </td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td colspan="2">
                                Subtotal
                            </td>
                            <td>
                                $
                                <span class="subtotal-price">
                             <fmt:formatNumber type="number" minFractionDigits="2"
                                               minIntegerDigits="1"
                                               maxFractionDigits="2"
                                               value="${(order.total / 1.15)}"/>
                        </span>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                Tax
                            </td>
                            <td>
                                $
                                <span class="subtotal-price">
                             <fmt:formatNumber type="number" minFractionDigits="2"
                                               minIntegerDigits="1"
                                               maxFractionDigits="2"
                                               value="${order.total - (order.total / 1.15)}"/>
                        </span>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                Total
                            </td>
                            <td>
                                $
                                <span class="subtotal-price">
                             <fmt:formatNumber type="number" minFractionDigits="2"
                                               minIntegerDigits="1"
                                               maxFractionDigits="2"
                                               value="${order.total}"/>
                        </span>
                            </td>
                        </tr>
                    </table>
                </c:forEach>
            </c:otherwise>
        </c:choose>

    </jsp:body>
</t:base_template>