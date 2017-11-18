package controller;

import exceptions.UserExistsException;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.Objects;

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
        // If logged in go back to main page
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
            Long ccNo = 0L;
            Integer ccCVV = 0;

            if (!request.getParameter("cc_no").equals("")){
                try{
                    ccNo = Long.parseLong(request.getParameter("cc_no"));
                }catch (NumberFormatException e){
                    response.setStatus(HttpServletResponse.SC_CONFLICT);
                    response.getWriter().write("Error. Check Credit card Number");
                    return;
                }
            }

            if (!request.getParameter("cc_cvv").equals("")) {
                try {
                    ccCVV = Integer.parseInt(request.getParameter("cc_cvv"));
                } catch (NumberFormatException e) {
                    response.setStatus(HttpServletResponse.SC_CONFLICT);
                    response.getWriter().write("Error. Check Credit card CVV");
                    return;
                }
            }

            Date ccExpiry = null;
            if (!request.getParameter("cc_expiry").equals("")){
                try{
                    ccExpiry = Date.valueOf(request.getParameter("cc_expiry"));
                }catch (IllegalArgumentException e){
                    response.setStatus(HttpServletResponse.SC_CONFLICT);
                    response.getWriter().write("Error. Check Credit card Expiry Date");
                    return;
                }
            }

            // Get all required parameters and create User
            User new_user = new User(request.getParameter("password"),
                    request.getParameter("first_name"),
                    request.getParameter("last_name"),
                    email, request.getParameter("address1"), request.getParameter("address2"),
                    request.getParameter("city"), request.getParameter("state"), request.getParameter("zip_code"),
                    request.getParameter("country"), request.getParameter("cc_type"), ccNo, ccCVV,
                    ccExpiry,
                    request.getParameter("login_name"));

            try {
                // Register user
                new_user.registerUser();
                response.getWriter().write("User registered!");
            } catch (UserExistsException user_exists) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(user_exists.getMessage());
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                response.getWriter().write("Error while processing request. Contact Admin!");
            }
        }
        else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("No email passed!");
        }

    }

}
