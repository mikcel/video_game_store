package controller;

import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "UserManagementServlet")
public class UserManagementServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Get Action Parameter
        String action = request.getParameter("action");

        if (action == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("No Action passed");
            return;
        }


        int userId;
        try{
            userId = Integer.parseInt(request.getParameter("user_id"));
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("User Account");
            return;
        }

        User userRetrieved;
        try {
            userRetrieved = User.find(userId);
        }catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("User not Found");
            return;
        }

        switch (action) {
            case "unlock_lock": {

                try {

                    response.setStatus(HttpServletResponse.SC_ACCEPTED);
                    if (userRetrieved.getLocked()){
                        userRetrieved.unlockAccount();
                        response.getWriter().write("Account Unlocked!");
                    }else{
                        userRetrieved.lockAccount();
                        response.getWriter().write("Account Locked!");
                    }

                } catch (Exception e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Unable to perform operation");
                    return;
                }

                break;
            }
            case "set_admin": {


                try{
                    userRetrieved.setAsAdmin();
                    response.setStatus(HttpServletResponse.SC_ACCEPTED);
                    response.getWriter().write("User is now admin!");

                } catch (Exception e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Unable to set as admin");
                    return;
                }

                break;
            }
            default:{
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Action not defined");
            }
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // If logged in go back to main page
        HttpSession session = request.getSession(false);
        if(session!= null && session.getAttribute("u_id") != null){
            try {
                request.setAttribute("users", User.getAllUsers());
                request.getRequestDispatcher("/WEB-INF/jsp/UserManagement.jsp").forward(request, response);
            } catch (SQLException e) {
                request.getRequestDispatcher("/WEB-INF/jsp/Error.jsp").forward(request, response);
            }
        }else{
            response.sendRedirect(request.getContextPath() + "/");
        }
    }
}
