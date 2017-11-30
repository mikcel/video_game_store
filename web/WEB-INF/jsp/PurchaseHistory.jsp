<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:base_template>
    <jsp:attribute name="extra_head">
        <script type="text/javascript" src="${pageContext.request.contextPath}/res/common_static/datatables/datatables.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/res/script/purchaseHistory.js"></script>

        <link href="${pageContext.request.contextPath}/res/common_static/datatables/datatables.min.css" rel="stylesheet"/>
        <link href="${pageContext.request.contextPath}/res/style/usersPurchaseHistory.css" rel="stylesheet"/>
    </jsp:attribute>
    <jsp:body>
        <%--@elvariable id="orders" type="controller.PurchaseHistoryServlet"--%>
        <h2 class="main-header">Purchase History</h2>

        <c:choose>
            <c:when test="${orders.size() == 0}">
                <div class="div-no-results container">
                    <h4 class="main-header">No Purchase History Found</h4>
                </div>
            </c:when>
            <c:otherwise>
                <table id="tbl_purchase_history" class="table table-hover">
                    <thead>
                    <tr>
                        <th>Date/Time</th>
                        <th>User Id</th>
                        <th># items</th>
                        <th>Total</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${orders}" var="order" varStatus="status">
                        <tr>
                            <td>
                                <fmt:formatDate type="date"  pattern="dd-MM-yyyy HH:mm:ss" value = "${order.getOrder_date()}"/>
                            </td>
                            <td>${order.getUser_id()}</td>
                            <td>${order.getGames().size()}</td>
                            <td>
                                <fmt:formatNumber type = "number"
                                                  minFractionDigits = "2" value = "${order.getTotal()}" />
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </jsp:body>
</t:base_template>
