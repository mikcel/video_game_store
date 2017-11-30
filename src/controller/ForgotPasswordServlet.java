package controller;

import exceptions.UserNotExistsException;
import mailing.Mailing;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ForgotPasswordServlet")
public class ForgotPasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Get Parameters
        String u_login = request.getParameter("user_login");
        String u_email = request.getParameter("user_email");

        response.setContentType("text/plain");
        if (u_login.length() == 0 || u_email.length() == 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Necessary data to login was not entered!");
        } else {

            try {

                User userExists = User.find(u_email, u_login);

                if (userExists.getLocked()){
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("You account is locked. Contact Admin!");
                    return;
                }

                userExists.createTmpPass();

                String emailSubject = "Games Dungeon: 24-hr Temporary Password";

                String emailBodyBuilder = "Dear " + userExists.getFirstName() + " " + userExists.getLastName() + ",\n\n" +
                        "You told us that you forgot your password. If you really did, use the following" +
                        " temporary password within 24-hour to reset your password.\n\n" +
                        "Temporary Password: " + userExists.getTemp_password() + "\n\n" +
                        "If you didn't mean to reset your password, then just ignore this email; your " +
                        "password will not change." +
                        "\n\n" +
                        "Games Dungeon Team";

                boolean emailSent = Mailing.getInstance().sendEmail(userExists.getEmail(), emailSubject, emailBodyBuilder, "text/plain");

                response.setStatus(HttpServletResponse.SC_ACCEPTED);
                response.getWriter().write("Email sent successfully");

            }catch (UserNotExistsException e){
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("User does not exist. Please check login name and email!");
            }catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("An error occurred while sending email!");
            }

        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("u_id") != null) {
            response.sendRedirect(request.getContextPath() + "/");
        } else {
            request.getRequestDispatcher("/WEB-INF/jsp/ForgotPassword.jsp").forward(request, response);
        }
    }
}
