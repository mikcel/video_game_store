<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:base_template>
    <jsp:attribute name="extra_head">
        <script type="text/javascript" src="${pageContext.request.contextPath}/res/script/specialsManage.js"></script>

        <link href="${pageContext.request.contextPath}/res/style/specialsManage.css" rel="stylesheet"/>
    </jsp:attribute>
    <jsp:body>
        <div id="loading-icon"></div>

        <%--@elvariable id="games" type="controller.SpecialsManagementServlet"--%>
        <h2 class="main-header">Specials Game Management</h2>

        <c:choose>
            <c:when test="${fn:length(games) == 0}">
                <div class="div-no-results container">
                    <h4 class="main-header">No Games Found</h4>
                </div>
            </c:when>
            <c:otherwise>
                <h3 class="main-header">Total # of specials game: ${fn:length(games)}</h3>
                <table id="tbl_specials" class="table table-hover">
                    <thead>
                    <tr>
                        <th></th>
                        <th>Game ID</th>
                        <th>Game Name</th>
                        <th>Price</th>
                        <th>Discounted Price</th>
                        <th>Shown to non-registered</th>
                        <th>Shown only to registered</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${games}" var="game" varStatus="status">
                        <tr id="tr_game_${game.getId()}" class="tr-specials">
                            <td>
                                <input type="checkbox" class="form-control ipt-game-checked"/>
                            </td>
                            <td>${game.getId()}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/game?game=${game.id}"
                                   target="_blank">
                                        ${game.getName()}
                                </a>
                            </td>
                            <td>$ <fmt:formatNumber type="number" minFractionDigits="2"
                                                    minIntegerDigits="1"
                                                    maxFractionDigits="2" value="${game.price}"/></td>
                            <td>$ <fmt:formatNumber type="number" minFractionDigits="2"
                                                    minIntegerDigits="1"
                                                    maxFractionDigits="2" value="${game.price - game.discount}"/></td>
                            <td>
                                ${!game.showOnlyRegister}
                            </td>
                            <td>
                                ${game.showOnlyRegister}
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <div id="div-btns">
                    <button id="btn-send-email" class="btn btn-action" onclick="send_request('send_email')">
                        Send Emails to Registered User
                    </button>
                    <button id="btn-show-only-register" class="btn btn-action" onclick="send_request('show_only_register')">
                        Show only to Registered Users
                    </button>
                    <button id="btn-show-to-nonregister" class="btn btn-action" onclick="send_request('show_non_register')">
                        Show to non Registered Users
                    </button>
                    <button id="btn-remove-special" class="btn btn-action" onclick="send_request('remove_specials')">
                        Remove from specials (Clear Discount)
                    </button>
                </div>

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
