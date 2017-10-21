package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exceptions.UserExistsException;
import model.User;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session!= null && session.getAttribute("u_id") != null){
            response.sendRedirect(request.getContextPath() + "/");
        }else{
            request.getRequestDispatcher("/WEB-INF/jsp/Register.jsp").forward(request, response);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");

        response.setContentType("text/plain");

        if(email!=null && !email.equals("")) {

            String first_name = request.getParameter("first_name");
            String last_name = request.getParameter("last_name");
            String password = request.getParameter("password");

            User new_user = new User(password, first_name, last_name, email);

            try {
                new_user.registerUser();
                response.getWriter().write("User registered!");
            } catch (UserExistsException user_exists) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, user_exists.getMessage());
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_CONFLICT, "Error while processing request. Contact Admin!");
            }

        }
        else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No email passed!");
        }

    }

}
