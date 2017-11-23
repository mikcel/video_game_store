package controller;

import exceptions.AccountLockedException;
import exceptions.IncorrectPasswordException;
import exceptions.UserNotExistsException;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if not already logged in
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("u_id") != null) {
            response.sendRedirect(request.getContextPath() + "/");
        } else {
            request.getRequestDispatcher("/WEB-INF/jsp/Login.jsp").forward(request, response);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Get Parameters
        String u_login = request.getParameter("user_login");
        String u_pass = request.getParameter("user_password");

        response.setContentType("text/plain");
        if (u_login.length() == 0 || u_pass.length() == 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Necessary data to login was not entered!");
        } else {

            try {

                // Check the user login info
                User user_logged_id = User.check_login(u_login, u_pass);
                // Set session attributes
                HttpSession session = request.getSession();
                session.setAttribute("user", user_logged_id);
                session.setAttribute("u_first_name", user_logged_id.getFirstName());
                session.setAttribute("u_last_name", user_logged_id.getLastName());
                session.setAttribute("u_email", user_logged_id.getEmail());
                session.setAttribute("u_login", user_logged_id.getLogin_name());
                session.setAttribute("u_id", user_logged_id.getId());
                session.setAttribute("shopping_cart", user_logged_id.getCart());

                response.setStatus(HttpServletResponse.SC_ACCEPTED);
                response.getWriter().write("Login successfully");

            } catch (UserNotExistsException | IncorrectPasswordException | AccountLockedException e){

                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(e.getMessage());

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("An error occurred while verifying user!");
            }

        }

    }

}
