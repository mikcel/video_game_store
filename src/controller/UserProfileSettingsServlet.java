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

@WebServlet(name = "UserProfileSettingsServlet")
public class UserProfileSettingsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // If logged in go back to main page
        HttpSession session = request.getSession(false);
        if(session!= null && session.getAttribute("u_id") != null){
            request.setAttribute("user", session.getAttribute("user"));
            request.getRequestDispatcher("/WEB-INF/jsp/UserProfileSettings.jsp").forward(request, response);
        }else{
            response.sendRedirect(request.getContextPath() + "/");
        }
    }

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

            // Get all required parameters and get user from session
            HttpSession session = request.getSession(false);
            User userLoggedIn = null;
            System.out.println("test");
            try{
                userLoggedIn = (User) session.getAttribute("user");
            }catch (Exception e){
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                response.getWriter().write("Error while fetching session user!");
            }

            if (userLoggedIn == null){
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                response.getWriter().write("No Session Found!");
                return;
            }

            try {

                User modifyingUser = new User(userLoggedIn);

                modifyingUser.setFirstName(request.getParameter("first_name"));
                modifyingUser.setLastName(request.getParameter("last_name"));
                modifyingUser.setEmail(request.getParameter("email"));
                modifyingUser.setAddress1(request.getParameter("address1"));
                modifyingUser.setAddress2(request.getParameter("address2"));
                modifyingUser.setCity(request.getParameter("city"));
                modifyingUser.setState(request.getParameter("state"));
                modifyingUser.setZip(request.getParameter("zip_code"));
                modifyingUser.setCountry(request.getParameter("country"));
                modifyingUser.setCredit_card_type(request.getParameter("cc_type"));
                modifyingUser.setCredit_card_number(ccNo);
                modifyingUser.setCredit_card_cvv(ccCVV);
                modifyingUser.setCredit_card_expiry(ccExpiry);

                if (modifyingUser.checkDuplicateEmail()){
                    throw new UserExistsException();
                }

                // Update user
                modifyingUser.updateUser();

                userLoggedIn = modifyingUser;
                session.setAttribute("user", userLoggedIn);

                response.setStatus(HttpServletResponse.SC_ACCEPTED);
                response.getWriter().write("User profile updated!");

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
