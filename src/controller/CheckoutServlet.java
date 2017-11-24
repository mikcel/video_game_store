package controller;

import creditcardvalidator.CreditCardValidator;
import exceptions.GameOrderedProcessedException;
import exceptions.OutOfStockException;
import invoicewriter.InvoiceWriter;
import mailing.Mailing;
import model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User loggedInUser = (User) request.getSession().getAttribute("user");
        ShoppingCart userCart = (ShoppingCart) request.getSession().getAttribute("shopping_cart");

        // Validate Credit Card
        try {
            CreditCardValidator.validateCreditCard(loggedInUser.getCredit_card_number(),
                    loggedInUser.getCredit_card_cvv(), loggedInUser.getCredit_card_expiry());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
            return;
        }

        // Generate Total for game and create new hashmap
        float orderTotal = 0F;
        HashMap<Game, Integer> gamesQty = new HashMap<>();
        for (CartGame cartGame: userCart.getGames()){

            Game gameFromCart = cartGame.getGame();

            // Check if requested quantity is not too much
            if (gameFromCart.getQtyInStock() < cartGame.getQuantity()){

                try {
                    throw new OutOfStockException(gameFromCart.getName());
                } catch (OutOfStockException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write(e.getMessage());
                    return;
                }
            }

            orderTotal += (gameFromCart.getPrice() - gameFromCart.getDiscount()) * cartGame.getQuantity();
            gamesQty.put(gameFromCart, cartGame.getQuantity());
        }
        orderTotal = (float) (Math.round(orderTotal * 1.15 * 100) / 100.0);

        try {

            // Create the order
            Order processedOrder = Order.processOrder(loggedInUser, orderTotal);

            // Create and save the order game
            ArrayList<OrderGame> orderedGames = processedOrder.getGames();
            for (Map.Entry<Game, Integer> mapEntry : gamesQty.entrySet()) {
                OrderGame insertedGame = OrderGame.insertGame(mapEntry.getKey(), processedOrder, mapEntry.getValue());
                orderedGames.add(insertedGame);
                mapEntry.getKey().updateQty(mapEntry.getValue());
            }

            // Empty the shopping cart
            userCart.emptyCart();

            // Generate Invoice
            request.setAttribute("user", loggedInUser);
            request.setAttribute("order", processedOrder);
            InvoiceWriter customResponse  = new InvoiceWriter(response);
            request.getRequestDispatcher("/WEB-INF/jsp/Invoice.jsp").forward(request, customResponse);

            // Send Invoice by email
            Mailing.getInstance().sendEmail(loggedInUser.getEmail(),"Invoice Order" , customResponse.getOutput(), "text/html");

            // Send Invoice back
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            response.setContentType("text/html");
            response.getWriter().write(customResponse.getOutput());

        } catch (SQLException | GameOrderedProcessedException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
            return;
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if not already logged in
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("u_id") != null) {
            request.getRequestDispatcher("/WEB-INF/jsp/Checkout.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/");
        }
    }

}
