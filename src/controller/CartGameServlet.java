package controller;

import exceptions.GameInCartException;
import exceptions.GameNotFoundException;
import model.CartGame;
import model.ShoppingCart;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "CartGameServlet")
public class CartGameServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Get Action Parameter
        String action = request.getParameter("action");

        if (action == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("No Action passed");
            return;
        }

        // Get shopping cart from session
        ShoppingCart userCart;
        try {
            userCart = getSessionCart(request);
        } catch (ClassCastException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Unable to retrieve shopping cart");
            return;
        }

        switch (action) {
            case "add_game": {

                // Get Game id from request
                int gameId;
                try {
                    gameId = Integer.parseInt(request.getParameter("game_id"));
                } catch (NumberFormatException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Incorrect type for game id");
                    return;
                } catch (NullPointerException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("No game Id found");
                    return;
                }

                HttpSession session = request.getSession(false);

                // Get shopping cart from session
                try {

                    // If no shopping cart created for user, create one and save in session
                    if (userCart == null) {
                        try {
                            userCart = ShoppingCart.createCart((Integer) session.getAttribute("u_id"));
                            session.setAttribute("shopping_cart", userCart);
                        } catch (SQLException e1) {
                            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            response.getWriter().write("Unable to create shopping cart");
                            return;
                        }
                    }

                } catch (ClassCastException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Unable to retrieve shopping cart");
                    return;
                }

                try {

                    // Insert Game in Shopping Cart
                    CartGame newGame = CartGame.insertGame(gameId, userCart, 1);
                    userCart.getGames().add(newGame);

                    response.setStatus(HttpServletResponse.SC_ACCEPTED);

                    response.setContentType("application/json");

                    JSONObject sendObj = new JSONObject();

                    sendObj.put("message", "Added item to cart successfully");
                    sendObj.put("cart_size", userCart.getNoItems());
                    response.getWriter().print(sendObj);

                } catch (GameNotFoundException | SQLException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write(e.getMessage());
                } catch (GameInCartException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Game is already in shopping cart. " +
                            "To add more of the same game, proceed to checkout!");
                }

                break;
            }
            case "update_game":{

                // Get request parameters
                Integer cartGameId, qty;
                try {
                    cartGameId = Integer.parseInt(request.getParameter("cart_game_id"));
                    qty = Integer.parseInt(request.getParameter("game_qty"));

                    if (cartGameId == null || qty == null){
                        throw new NullPointerException();
                    }

                } catch (NumberFormatException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Incorrect parameters");
                    return;
                } catch (NullPointerException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("No parameters found");
                    return;
                }

                HttpSession session = request.getSession(false);

                try {
                    CartGame cartGame = userCart.findCartGame(cartGameId);

                    if (cartGame == null){
                        throw new GameInCartException();
                    }

                    cartGame.updateQty(qty);
                    userCart.updateGame(cartGame);

                } catch (GameInCartException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Invalid Game in Cart");
                    return;
                } catch (SQLException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Unable to update game quantity");
                    return;
                }

                response.setStatus(HttpServletResponse.SC_ACCEPTED);

                response.setContentType("application/json");

                JSONObject sendObj = new JSONObject();

                int cartNoItems = userCart.getNoItems();
                sendObj.put("message", "Item updated successully. Cart Size: " + cartNoItems);
                sendObj.put("cart_size", cartNoItems);
                response.getWriter().print(sendObj);

                break;
            }
            case "remove_game":{

                // Get request parameters
                Integer cartGameId;
                try {
                    cartGameId = Integer.parseInt(request.getParameter("cart_game_id"));

                    if (cartGameId == null){
                        throw new NullPointerException();
                    }

                } catch (NumberFormatException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Incorrect game id");
                    return;
                } catch (NullPointerException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("No game id found");
                    return;
                }

                try {

                    userCart.removeGame(cartGameId);

                    response.setStatus(HttpServletResponse.SC_ACCEPTED);

                    response.setContentType("application/json");

                    JSONObject sendObj = new JSONObject();

                    int cartNoItems = userCart.getNoItems();
                    sendObj.put("message", "Item updated successully. Cart Size: " + cartNoItems);
                    sendObj.put("cart_size", cartNoItems);
                    response.getWriter().print(sendObj);

                    break;

                } catch (SQLException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Error while removing game");
                    return;
                }

            }
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if not already logged in
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("u_id") != null) {
            request.getRequestDispatcher("/WEB-INF/jsp/ShoppingCartManage.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/");
        }
    }

    public ShoppingCart getSessionCart(HttpServletRequest request){
        return (ShoppingCart) request.getSession(false).getAttribute("shopping_cart");
    }

}
