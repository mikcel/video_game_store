<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <style>
        table{
            border-collapse: collapse;
            border-spacing: 0;
            width: 45%;
        }
        .table > thead > tr > th, .table > tbody > tr > th,
        .table > tfoot > tr > th, .table > thead > tr > td,
        .table > tbody > tr > td, .table > tfoot > tr > td {
            line-height: 1.42857143;
            vertical-align: top;
            border-top: 1px solid #dddddd;
            padding-top: 2%;
        }
        .td-total{
            font-weight: bold;
        }

        .sum-total-price{
            font-weight: bold;
            text-decoration: underline double black;
        }
        .td-sum-label{
            font-weight: bold;
            text-align: right;
            padding-right: 5%;
        }
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
            <td colspan="2" class="td-sum-label">
                Subtotal
            </td>
            <td class="td-total">
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
            <td colspan="2" class="td-sum-label">
                Tax
            </td>
            <td class="td-total">
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
            <td colspan="2" class="td-sum-label">
                Total
            </td>
            <td class="td-total">
                $
                <span class="sum-total-price">
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
