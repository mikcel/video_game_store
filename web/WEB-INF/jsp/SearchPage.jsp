<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:base_template>
    <jsp:attribute name="extra_head">
        <link href="${pageContext.request.contextPath}/res/style/searchPage.css" type="text/css" rel="stylesheet"/>
    </jsp:attribute>
    <jsp:body>
        <h1>Search Game Page</h1>

        <div class="panel panel-default">
            <div class="panel-body">
                <form id="frm-search" action="${pageContext.request.contextPath}/search" method="GET" class="form-horizontal">
                    <fieldset>
                        <legend id="frm-legend-id">Search by ID</legend>
                        <div class="form-group">
                            <label for="ipt-game-id" class="control-label col-lg-2">Game ID:</label>
                            <div class="col-lg-10">
                                <input type="text" name="game_id" id="ipt-game-id" placeholder="ID" class="form-control"/>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <legend>Search by:</legend>
                        <div class="form-group">
                            <label for="ipt-game-name" class="control-label col-lg-2">Game Name:</label>
                            <div class="col-lg-10">
                                <input type="text" name="game_name" id="ipt-game-name" placeholder="Name" class="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="slct-console" class="control-label col-lg-2">Console:</label>
                            <div class="col-lg-10">
                                <select class="form-control" id="slct-console" name="console">
                                    <option value="" disabled selected>Console</option>
                                    <%--@elvariable id="consoles" type="controller.SearchGameServlet"--%>
                                    <c:forEach items="${consoles}" var="cnsl">
                                        <option value="${cnsl}">${cnsl}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="ipt-game-nplayers" class="control-label col-lg-2">No. of Players:</label>
                            <div class="col-lg-5">
                                <input type="number" name="num_players" id="ipt-game-nplayers" value="1" class="form-control" min="0"/>
                            </div>
                            <div class="col-lg-5">
                                <input type="checkbox" id="ipt-nplayers-null" value="true" name="num_players_null"/>
                                <label for="ipt-nplayers-null">Not matter</label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="slct-genre" class="control-label col-lg-2">Genre:</label>
                            <div class="col-lg-10">
                                <select class="form-control" id="slct-genre" name="genre">
                                    <option value="" disabled selected>Genre</option>
                                        <%--@elvariable id="genres" type="controller.SearchGameServlet"--%>
                                    <c:forEach items="${genres}" var="genre">
                                        <option value="${genre}">${genre}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">Co op:</label>
                            <div class="col-lg-10">
                                <div class="radio">
                                    <label>
                                        <input name="game_coop" id="rd-game-coop-true" value="yes" type="radio">
                                        Yes
                                    </label>
                                    &emsp;
                                    <label>
                                        <input name="game_coop" id="rd-game-coop-false" value="no" type="radio">
                                        No
                                    </label>
                                    &emsp;
                                    <label>
                                        <input name="game_coop" id="rd-game-coop-both" value="both" type="radio" checked="">
                                        Both
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="ipt-game-reldate" class="control-label col-lg-2">Release Date:</label>
                            <div class="col-lg-10">
                                <input type="date" name="release_date" id="ipt-game-reldate" class="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="slct-developer" class="control-label col-lg-2">Developer:</label>
                            <div class="col-lg-10">
                                <select class="form-control" id="slct-developer" name="developer">
                                    <option value="" disabled selected>Developer</option>
                                    <%--@elvariable id="developers" type="controller.SearchGameServlet"--%>
                                    <c:forEach items="${developers}" var="dev">
                                        <option value="${dev}">${dev}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="slct-publisher" class="control-label col-lg-2">Publisher:</label>
                            <div class="col-lg-10">
                                <select class="form-control" id="slct-publisher" name="publisher">
                                    <option value="" disabled selected>Publisher</option>
                                        <%--@elvariable id="publishers" type="controller.SearchGameServlet"--%>
                                    <c:forEach items="${publishers}" var="pub">
                                        <option value="${pub}">${pub}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="ipt-game-price" class="control-label col-lg-2">Price:</label>
                            <div class="col-lg-5">
                                <input type="number" value="0" step="0.01" name="price" id="ipt-game-price" placeholder="Name" class="form-control"/>
                            </div>
                            &emsp;
                            <div class="col-lg-5">
                                <input type="checkbox" id="ipt-price-null" value="true" name="price_null"/>
                                <label for="ipt-price-null">Not matter</label>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-lg-10 col-lg-offset-2">
                                <button type="reset" class="btn btn-default">Reset</button>
                                <button type="submit" class="btn btn-primary">Search</button>
                            </div>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>

    </jsp:body>
</t:base_template>