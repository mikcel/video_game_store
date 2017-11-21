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
                ShoppingCart userCart;
                try {
                    userCart = (ShoppingCart) session.getAttribute("shopping_cart");

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
                    sendObj.put("cart_size", userCart.getGames().size());
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
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TODO: decide on post/get
    }
}
