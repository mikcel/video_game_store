package controller;

import model.Order;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "UserPurchaseHistoryServlet")
public class UserPurchaseHistoryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if not already logged in
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("u_id") != null) {

            User userLoggedIn = (User) request.getSession(false).getAttribute("user");
            try {
                ArrayList<Order> userOrders = Order.retrievedOrders(userLoggedIn.getId());
                request.setAttribute("orders", userOrders);
                request.getRequestDispatcher("/WEB-INF/jsp/UserPurchaseHistory.jsp").forward(request, response);

            } catch (SQLException e) {
                request.getRequestDispatcher("/WEB-INF/jsp/Error.jsp").forward(request, response);
                return;
            }

        } else {
            response.sendRedirect(request.getContextPath() + "/");
        }
    }
}
