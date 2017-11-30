package controller;

import invoicewriter.InvoiceWriter;
import mailing.Mailing;
import model.Game;
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

@WebServlet(name = "SpecialsManagementServlet")
public class SpecialsManagementServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Get Action Parameter
        String action = request.getParameter("action");

        if (action == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("No Action passed");
            return;
        }

        String[] passedGameIds = request.getParameterValues("games[]");

        if (passedGameIds.length == 0){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("No Games Selected");
            return;
        }

        int[] gameIds = new int[passedGameIds.length];
        try{
            for (int i=0; i<gameIds.length; i++){
                gameIds[i] = Integer.parseInt(passedGameIds[i]);
            }
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
            return;
        }

        switch(action){
            case "show_only_register":{

                try {

                    for (int gameId : gameIds) {
                        Game game = Game.find(gameId);
                        assert game != null;
                        game.updateShowOnlyRegister(true);
                    }
                    response.setStatus(HttpServletResponse.SC_ACCEPTED);
                    response.getWriter().write("Games updated successfully");

                }catch (Exception e){
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write(e.getMessage());
                    return;
                }

                break;
            }
            case "show_non_register":{

                try {

                    for (int gameId : gameIds) {
                        Game game = Game.find(gameId);
                        assert game != null;
                        game.updateShowOnlyRegister(false);
                    }
                    response.setStatus(HttpServletResponse.SC_ACCEPTED);
                    response.getWriter().write("Games updated successfully");

                }catch (Exception e){
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write(e.getMessage());
                    return;
                }

                break;

            }
            case "remove_specials":{

                try {

                    for (int gameId : gameIds) {
                        Game game = Game.find(gameId);
                        assert game != null;
                        game.setDiscount(0.0);
                        game.updateGame();
                    }
                    response.setStatus(HttpServletResponse.SC_ACCEPTED);
                    response.getWriter().write("Games updated successfully");

                }catch (Exception e){
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write(e.getMessage());
                    return;
                }

                break;

            }
            case "send_email":{

                ArrayList<Game> gamesToSend = new ArrayList<>();

                for(int gameId: gameIds){
                    try {
                        gamesToSend.add(Game.find(gameId));
                    } catch (Exception e) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        response.getWriter().write("No Game found for id " + gameId);
                        return;
                    }
                }


                // Send Invoice by email to registered user
                ArrayList<User> allUsers;
                try {
                    allUsers = User.getAllUsers();
                } catch (SQLException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Unable to retrieve all registered users");
                    return;
                }

                try{
                    for(User user : allUsers){
                        if (user.getId() >= 49 ){
                            // Generate Email
                            request.setAttribute("user", user);
                            request.setAttribute("games", gamesToSend);
                            InvoiceWriter customResponse  = new InvoiceWriter(response);
                            request.getRequestDispatcher("/WEB-INF/jsp/SpecialsEmail.jsp").forward(request, customResponse);

                            Mailing.getInstance().sendEmail(user.getEmail(),"SPECIALS GAMES - GO CHECK" ,
                                    customResponse.getOutput(), "text/html");
                        }
                    }

                    response.setStatus(HttpServletResponse.SC_ACCEPTED);
                    response.getWriter().write("Emails Sent");

                }catch (Exception e){
                    e.printStackTrace();
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Unable to send all emails");
                }

            }
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // If logged in go back to main page
        HttpSession session = request.getSession(false);
        if(session!= null && session.getAttribute("u_id") != null && ((User) session.getAttribute("user")).isAdmin()){
            try {
                Game[] games = Game.findAllGameWithDisc();
                request.setAttribute("games", games);
                request.getRequestDispatcher("/WEB-INF/jsp/SpecialsManagement.jsp").forward(request, response);
            } catch (Exception e) {
                request.getRequestDispatcher("/WEB-INF/jsp/Error.jsp").forward(request, response);
            }
        }else{
            response.sendRedirect(request.getContextPath() + "/");
        }
    }
}
