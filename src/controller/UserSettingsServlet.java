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

@WebServlet(name = "UserSettingsServlet")
public class UserSettingsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String oldPassword = request.getParameter("old_pass");
        String newPassword = request.getParameter("new_pass");

        HttpSession currSession = request.getSession(false);

        try{
            User userLoggedIn = (User) currSession.getAttribute("user");

            if (!userLoggedIn.getPassword().equals(oldPassword)){
                throw new Exception("Invalid current password");
            }

            userLoggedIn.updatePass(newPassword);

            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            response.setContentType("text/plain");
            response.getWriter().write("Password updated!");

        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if not already logged in
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("u_id") != null) {
            request.getRequestDispatcher("/WEB-INF/jsp/UserSettings.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/");
        }
    }
}
