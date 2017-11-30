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
        .discounted-game{
            text-decoration: line-through;
            color: #ff5656;
        }
        .td-discount{
            font-weight:bold;
        }
    </style>
</head>
<body>
<div>Dear ${user.firstName} ${user.lastName},</div>

<h1>Specials Game &#9786; !!!</h1>

<p>
    Go take a look at these specials mentioned below!!!
</p>

<div>
    <table id="tbl_games">
        <thead>
        <tr>
            <th>Game Name</th>
            <th>Original Price</th>
            <th>Discounted Price</th>
            <th>Qty in stock</th>
        </tr>
        </thead>
        <tbody>
        <%--@elvariable id="games" type="controller.SpecialsManagementServlet"--%>
        <c:forEach items="${games}" var="game" varStatus="status">
            <tr>
                <td>
                    <h5 class="game-title">${game.name}</h5>
                </td>
                <td>
                    $
                    <span class="game-price <c:if test="${game.discount != 0}">discounted-game</c:if>">
                        <fmt:formatNumber type="number" minFractionDigits="2"
                                          minIntegerDigits="1"
                                          maxFractionDigits="2" value="${game.price}"/>
                    </span>
                </td>
                <td class="td-discount">
                    $
                    <span class="spn-discount">
                        &nbsp;
                        <fmt:formatNumber type="number" minFractionDigits="2"
                                          minIntegerDigits="1"
                                          maxFractionDigits="2"
                                          value="${game.price - game.discount}"/>
                    </span>
                </td>
                <td>
                        ${game.qtyInStock}
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
