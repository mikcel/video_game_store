<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:base_template>
    <jsp:attribute name="extra_head">
        <script type="text/javascript" src="${pageContext.request.contextPath}/res/common_static/datatables/datatables.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/res/script/inventoryPage.js"></script>

        <link href="${pageContext.request.contextPath}/res/common_static/datatables/datatables.min.css" rel="stylesheet"/>
        <link href="${pageContext.request.contextPath}/res/style/inventoryPage.css" rel="stylesheet"/>
    </jsp:attribute>
    <jsp:body>
        <%--@elvariable id="games" type="controller.InventoryManagementServlet"--%>
        <h2 class="main-header">Inventory Management</h2>

        <c:choose>
            <c:when test="${fn:length(games) == 0}">
                <div class="div-no-results container">
                    <h4 class="main-header">No Games Found</h4>
                </div>
            </c:when>
            <c:otherwise>
                <h4 class="main-header">Total # of games in stock: ${total_game}</h4>
                <table id="tbl_inventory" class="table table-hover">
                    <thead>
                    <tr>
                        <th>Game ID</th>
                        <th>Game Name</th>
                        <th>Price</th>
                        <th>Discounted Price</th>
                        <th># items in stock</th>
                        <th data-sortable="false"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${games}" var="game" varStatus="status">
                        <tr id="tr_game_${game.getId()}">
                            <td>${game.getId()}</td>
                            <td>${game.getName()}</td>
                            <td>$ <fmt:formatNumber type="number" minFractionDigits="2"
                                                    minIntegerDigits="1"
                                                    maxFractionDigits="2" value="${game.price}"/></td>
                            <td>$ <fmt:formatNumber type="number" minFractionDigits="2"
                                                    minIntegerDigits="1"
                                                    maxFractionDigits="2" value="${game.price - game.discount}"/></td>
                            <td>
                                <input type="number" class="ipt-game-qty form-control" value="${game.qtyInStock}" min="0" />
                            </td>
                            <td>
                                <button class="btn btn-subaction" onclick="update_stock(${game.getId()})">
                                    Update Stock
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
