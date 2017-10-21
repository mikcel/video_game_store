package controller;

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
        HttpSession session = request.getSession();
        if(session!= null && session.getAttribute("u_id") != null){
            response.sendRedirect(request.getContextPath() + "/");
        }else{
            request.getRequestDispatcher("/jsp/Login.jsp").forward(request, response);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String u_email = request.getParameter("user_email");
        String u_pass = request.getParameter("user_password");

        response.setContentType("text/plain");
        if (u_email.length() == 0 || u_pass.length() == 0) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Necessary data to login was not entered!");
        } else {

            try {

                User user_logged_id = User.check_login(u_email, u_pass);
                if (user_logged_id != null) {

                    HttpSession session = request.getSession();
                    session.setAttribute("u_first_name", user_logged_id.getFirstName());
                    session.setAttribute("u_last_name", user_logged_id.getLastName());
                    session.setAttribute("u_email", user_logged_id.getEmail());
                    session.setAttribute("u_id", user_logged_id.getId());

                    response.setStatus(HttpServletResponse.SC_ACCEPTED);
                    response.getWriter().write("Logged In Successfully!");

                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Invalid Login. Try Again!");
                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                response.getWriter().write("An error occurred while verifying user!");
            }

        }

    }

}
