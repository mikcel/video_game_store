<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:base_template>
    <jsp:attribute name="extra_head">
        <link href="${pageContext.request.contextPath}/res/style/gameDetails.css" rel="stylesheet"/>
        <script type="text/javascript" src="${pageContext.request.contextPath}/res/script/gameDetails.js"></script>
    </jsp:attribute>
    <jsp:body>

        <c:if test="${game == null}">
            <h2 class="text-center">No corresponding game found!</h2>
        </c:if>

        <%--@elvariable id="game" type="controller.GameDetailsServlet"--%>
        <div class="main-container container-fluid <c:if test="${game == null}">collapse</c:if>">
            <div class="game-header">
                <c:if test="${game.logo != ''}">
                    <img src="${pageContext.request.contextPath}/res/img/games/${game.frontBoxArt}"
                         class="img-circle game-logo"/>
                </c:if>

                <h2 class="game-title">
                    #${game.id}&emsp;${game.name}
                    <c:if test="${sessionScope.u_id != null}">
                        <button class="btn btn-primary" onclick="add_game_cart(${game.id})">
                            <i class="fa fa-cart-plus" aria-hidden="true"></i>
                        </button>
                    </c:if>
                    <c:if test="${sessionScope.user.isAdmin()}">
                        <button class="btn btn-primary" onclick="edit_game()">
                            <i class="fa fa-edit" aria-hidden="true"></i>
                        </button>
                    </c:if>
                </h2>
            </div>
            <br>
            <div class="game-details-wrapper">
                <div class="game-div-img col-sm-4">
                    <c:choose>
                        <c:when test="${game.frontBoxArt != ''}">
                            <img src="${pageContext.request.contextPath}/res/img/games/${game.frontBoxArt}"
                                 class="img-thumbnail game-front-img"/>
                        </c:when>
                        <c:otherwise>
                            <img src="${pageContext.request.contextPath}/res/img/games/not_available.jpg"
                                 class="img-thumbnail game-front-img"/>
                        </c:otherwise>
                    </c:choose>
                    <c:if test="${game.backBoxArt != ''}">
                        <img src="${pageContext.request.contextPath}/res/img/games/${game.backBoxArt}"
                             class="img-thumbnail game-back-img"/>
                    </c:if>
                </div>
                <div class="game-details col-sm-8">
                    <div class="game-desc">
                        <div class="col-sm-6">
                            <div class="game-price-wrapper row">
                                <span class="spn-label col-sm-6">Price:</span>
                                <span class="col-sm-6">
                                    $&nbsp;<span
                                        class="game-price <c:if test="${game.discount != 0}">discounted-game</c:if>">

                                         <fmt:formatNumber type="number" minFractionDigits="2" minIntegerDigits="1"
                                                           value="${game.price}"/>
                                    </span>
                                    <c:if test="${game.discount != 0}">
                                        <span class="spn-discount">
                                            &nbsp;
                                            <fmt:formatNumber type="number" minFractionDigits="2"
                                                              minIntegerDigits="1"
                                                              maxFractionDigits="2"
                                                              value="${game.price - game.discount}"/>
                                        </span>
                                    </c:if>
                                </span>
                            </div>
                            <div class="game-console-wrapper row">
                                <span class="spn-label col-sm-6">Console:</span>
                                <span class="game-console col-sm-6">${game.console}</span>
                            </div>
                            <div class="game-nplayers-wrapper row">
                                <span class="spn-label col-sm-6"># Players:</span>
                                <span class="game-nplayers col-sm-6">${game.numPlayers}</span>
                            </div>
                            <div class="game-coop-wrapper row">
                                <span class="spn-label col-sm-6">Co op:</span>
                                <span class="game-coop col-sm-6">${game.coop}</span>
                            </div>
                            <div class="game-genre-wrapper row">
                                <span class="spn-label col-sm-6">Genre:</span>
                                <span class="game-genre col-sm-6">${game.genre}</span>
                            </div>
                            <div class="game-genre-wrapper row">
                                <span class="spn-label col-sm-6">Quantity In Stock:</span>
                                <span class="game-genre col-sm-6">${game.getQtyInStock()}</span>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="game-release-date-wrapper row">
                                <span class="spn-label col-sm-6">Release Date:</span>
                                <span class="game-release-date col-sm-6">${game.releaseDate}</span>
                            </div>
                            <div class="game-publisher-wrapper row">
                                <span class="spn-label col-sm-6">Publisher:</span>
                                <span class="game-publisher col-sm-6">${game.publisher}</span>
                            </div>
                            <div class="game-developer-wrapper">
                                <span class="spn-label">Developer:</span>
                                <div class="div-lbl-logo">
                                    <span class="game-developer">${game.developer}</span>
                                    <c:choose>
                                        <c:when test="${game.developerLogo != ''}">
                                            <img src="${pageContext.request.contextPath}/res/img/${game.developerLogo}"
                                                 onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/res/img/games/not_available.jpg';"
                                                 class="img-thumbnail game-developer-logo"/>
                                        </c:when>
                                        <c:otherwise>
                                            <img src="${pageContext.request.contextPath}/res/img/games/not_available.jpg"
                                                 class="img-thumbnail game-developer-logo"/>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <c:if test="${game.showOnlyRegister && sessionScope.u_id != null}">
                                <div class="game-show-only-register">
                                    <span class="spn-label">Only shown to registered user in specials</span>
                                </div>
                            </c:if>
                        </div>
                        <div class="game-desc-wrapper col-sm-12">
                            <span class="spn-label">Description:</span>
                            <p class="game-description">${game.description}</p>
                        </div>
                        <div class="game-comment-wrapper col-sm-12">
                            <h6>Comments:</h6>
                            <c:forEach items="${game.comments}" var="comment">
                                <div id="comment-${comment.id}" class="div-comment col-sm-6">
                                    <div class="div-comment-user">
                                        <span class="spn-label">${comment.user.firstName}&nbsp;${comment.user.lastName}</span>
                                        &nbsp;
                                        <span class="spn-rating-cmt">
                                            <c:forEach var="i" begin="1" end="${comment.ratings}">
                                                <i class="fa fa-star" aria-hidden="true"></i>
                                            </c:forEach>
                                            <c:forEach var="i" begin="${comment.ratings + 1}" end="5">
                                                <i class="fa fa-star-o" aria-hidden="true"></i>
                                            </c:forEach>
                                        </span>
                                    </div>
                                    <div class="div-comment-date">
                                        <fmt:formatDate value="${comment.commentDate}" type="both" dateStyle="medium"
                                                        timeStyle="medium"/>
                                    </div>
                                    <div class="div-comment-details">
                                            ${comment.commentDetails}
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
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

        <div id="edit-game-modal" class="modal fade" role="dialog">
            <div class="modal-dialog">

                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Edit ${game.getName()}</h4>
                    </div>
                    <div class="modal-body">
                        <form id="frm-edit-game" role="form" method="post" action="">
                            <input id="ipt-game-id" type="hidden" name="game_id" value="${game.id}">
                            <div class="row">
                                <div class="form-group col-lg-4">
                                    <label for="ipt-game-name" class="control-label col-lg-5">Game Name:</label>
                                    <div class="col-lg-7">
                                        <input type="text" name="game_name" id="ipt-game-name" value="${game.getName()}"
                                               class="form-control" required/>
                                    </div>
                                </div>
                                <div class="form-group col-lg-4">
                                    <label for="ipt-console" class="control-label col-lg-5">Console:</label>
                                    <div class="col-lg-7">
                                        <input type="text" name="console" id="ipt-console" value="${game.console}"
                                               class="form-control"/>
                                    </div>
                                </div>
                                <div class="form-group col-lg-4">
                                    <label for="ipt-game-nplayers" class="control-label col-lg-5">No. of Players:</label>
                                    <div class="col-lg-7">
                                        <input type="number" name="num_players" id="ipt-game-nplayers" value="${game.numPlayers}"
                                               class="form-control" min="0"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-lg-4">
                                    <label for="ipt-genre" class="control-label col-lg-5">Genre:</label>
                                    <div class="col-lg-7">
                                        <input type="text" name="genre" id="ipt-genre" value="${game.genre}"
                                               class="form-control"/>
                                    </div>
                                </div>
                                <div class="form-group col-lg-4">
                                    <label class="col-lg-5 control-label">Co op:</label>
                                    <div class="col-lg-7">
                                        <div class="radio">
                                            <label>
                                                <input name="game_coop" id="rd-game-coop-true" value="yes" type="radio"
                                                       <c:if test="${game.coop == 'Yes'}">checked</c:if>/>
                                                Yes
                                            </label>
                                            &emsp;
                                            <label>
                                                <input name="game_coop" id="rd-game-coop-false" value="no" type="radio"
                                                       <c:if test="${game.coop == 'No'}">checked</c:if>>
                                                No
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group col-lg-4">
                                    <label for="ipt-game-reldate" class="control-label col-lg-5">Release Date:</label>
                                    <div class="col-lg-7">
                                        <input type="date" name="release_date" id="ipt-game-reldate" class="form-control"
                                               value="${game.releaseDate}"/>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="form-group col-lg-4">
                                    <label for="ipt-game-price" class="control-label col-lg-5">Price:</label>
                                    <div class="col-lg-7">
                                        <input type="number" value="${game.price}" step="0.01" name="price"
                                               id="ipt-game-price" class="form-control" required/>
                                    </div>
                                </div>
                                <div class="form-group col-lg-4">
                                    <label for="ipt-game-discount" class="control-label col-lg-5">Discount:</label>
                                    <div class="col-lg-7">
                                        <input type="number" value="${game.discount}" step="0.01" name="discount"
                                               id="ipt-game-discount" class="form-control" required/>
                                    </div>
                                </div>
                                <div class="form-group col-lg-4">
                                    <label for="ipt-game-qty" class="control-label col-lg-5">Qty In Stock:</label>
                                    <div class="col-lg-7">
                                        <input type="number" value="${game.getQtyInStock()}" step="1" name="qty"
                                               id="ipt-game-qty"  class="form-control" required/>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="form-group col-lg-4">
                                    <label for="ipt-developer" class="control-label col-lg-4">Developer:</label>
                                    <div class="col-lg-8">
                                        <input type="text" name="developer" id="ipt-developer" value="${game.developer}"
                                               class="form-control"/>
                                    </div>
                                </div>
                                <div class="form-group col-lg-4">
                                    <label for="ipt-publisher" class="control-label col-lg-4">Publisher:</label>
                                    <div class="col-lg-8">
                                        <input type="text" name="publisher" id="ipt-publisher" value="${game.publisher}"
                                               class="form-control"/>
                                    </div>
                                </div>
                                <div class="form-group col-lg-4">
                                    <div class="col-lg-1">
                                        <input type="checkbox" name="show_only_register" id="ipt-show-register"
                                                <c:if test="${game.showOnlyRegister}">checked</c:if>
                                               class="form-control"/>
                                    </div>
                                    <label for="ipt-show-register" class="control-label col-lg-10">Show only to registered</label>
                                </div>
                            </div>

                            <div class="row">
                                <div class="form-group col-lg-12">
                                    <label for="ipt-game-desc" class="control-label col-lg-2">Description:</label>
                                    <div class="col-lg-10">
                                        <textarea name="description" id="ipt-game-desc"  class="form-control" rows="4">${game.description}</textarea>
                                    </div>
                                </div>
                            </div>

                            <button type="submit" class="btn btn-primary">Save</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>

                        </form>

                    </div>
                </div>

            </div>
        </div>

    </jsp:body>
</t:base_template>
