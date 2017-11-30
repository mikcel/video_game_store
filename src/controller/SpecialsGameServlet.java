package controller;

import model.Game;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "SpecialsGameServlet")
public class SpecialsGameServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            Game[] specialGames = Game.findAllGameWithDisc();

            if (request.getSession(false).getAttribute("user") == null){
                ArrayList<Game> gamesToShow = new ArrayList<>();
                for (Game game: specialGames){
                    if (!game.isShowOnlyRegister()){
                        gamesToShow.add(game);
                    }
                }
                specialGames = gamesToShow.toArray(new Game[gamesToShow.size()]);
            }

            request.setAttribute("gamesFound", specialGames);
            request.setAttribute("specials", true);
            request.getRequestDispatcher("/WEB-INF/jsp/SearchResults.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_CONFLICT, "Error while processing request. Contact Admin!");
        }


    }
}
