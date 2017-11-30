package controller;

import exceptions.IncorrectPasswordException;
import exceptions.TempPassExpiredExecption;
import exceptions.UserNotExistsException;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ResetPasswordServlet")
public class ResetPasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Get Parameters
        String u_login = request.getParameter("user_login");
        String u_email = request.getParameter("user_email");
        String u_tmp_pass = request.getParameter("user_tmp_pass");
        String u_new_pass = request.getParameter("user_new_pass");

        response.setContentType("text/plain");
        if (u_login.length() == 0 || u_new_pass.length() == 0 || u_tmp_pass.length() == 0 || u_email.length() == 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Necessary data to login was not entered!");
        } else {

            try {

                // Check the user login info
                User.reset_password(u_login, u_tmp_pass, u_new_pass, u_email);

                response.setStatus(HttpServletResponse.SC_ACCEPTED);
                response.getWriter().write("Password Reset successfully");

            } catch (UserNotExistsException | IncorrectPasswordException | TempPassExpiredExecption e) {

                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(e.getMessage());

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("An error occurred while verifying user!");
            }

        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Check if not already logged in
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("u_id") != null) {
            response.sendRedirect(request.getContextPath() + "/");
        } else {
            request.getRequestDispatcher("/WEB-INF/jsp/ResetPassword.jsp").forward(request, response);
        }

    }
}
