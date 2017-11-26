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
import java.util.HashMap;

@WebServlet(name = "PurchaseHistoryServlet")
public class PurchaseHistoryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // If logged in go back to main page
        HttpSession session = request.getSession(false);
        if(session!= null && session.getAttribute("u_id") != null && ((User) session.getAttribute("user")).isAdmin()){
            try {
                request.setAttribute("users", User.getAllUsers());
                request.setAttribute("orders",  Order.getAllOrders());

                request.getRequestDispatcher("/WEB-INF/jsp/PurchaseHistory.jsp").forward(request, response);
            } catch (SQLException e) {
                request.getRequestDispatcher("/WEB-INF/jsp/Error.jsp").forward(request, response);
            }
        }else{
            response.sendRedirect(request.getContextPath() + "/");
        }
    }
}
