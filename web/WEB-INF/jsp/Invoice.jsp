<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <style>

    </style>
</head>
<body>
<div>Dear ${user.firstName} ${user.lastName},</div>

<h1>Thanks For Your Business</h1>

<div>
    <h2>Your Order</h2>
    <small>
        <fmt:formatDate type="date"  pattern="yyyy-MM-dd HH:mm:ss" value = "${order.getOrder_date()}"/>
    </small>

    <table>
        <c:forEach items="${order.getGames()}" var="orderGame" varStatus="status">
            <tr>
                <td>
                        ${orderGame.game.name}
                </td>
                <td>
                        ${orderGame.quantity}
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
            <td>
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
            <td>
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
            <td>
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

    <div>
        <h4>Billing/Shipping Address</h4>
        <p>
            ${user.address1} ${user.address2}<br>
            ${user.city} ${user.state}<br>
            ${user.zip} ${user.country}
        </p>
    </div>
</div>
</body>
</html>
