package controller;

import model.FavoritesTDG;
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

@WebServlet(name = "FavoritesServlet")
public class FavoritesServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Integer gameId;
        try {
            gameId = Integer.parseInt(request.getParameter("game_id"));
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("No Game ID passed!");
            return;
        }

        HttpSession session = request.getSession(false);
        if (session == null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Please log in first!");
            return;
        }

        User loggedInUser;
        try {
            loggedInUser = (User) session.getAttribute("user");
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("User not found for session");
            return;
        }

        try {

            FavoritesTDG.deleteFromFav(loggedInUser.getId(), gameId);

            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            response.getWriter().write("Game Removed from Favorites successfully");

        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Error while removing from favorites!");
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Check if not already logged in
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("u_id") != null) {

            try {
                Integer[] favoriteIds = FavoritesTDG.findUserFav((Integer) session.getAttribute("u_id"));

                ArrayList<Game> favorites = new ArrayList<Game>();
                for (Integer gameId: favoriteIds){
                    favorites.add(Game.find(gameId));
                }
                request.setAttribute("favorites", favorites);

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error fetching favorites!");
            }

            request.getRequestDispatcher("/WEB-INF/jsp/Favorites.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/");
        }

    }
}
