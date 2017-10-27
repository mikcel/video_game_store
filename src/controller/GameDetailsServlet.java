package controller;

import model.Game;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;

@WebServlet(name = "GameDetailsServlet")
public class GameDetailsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Enumeration<?> parameterNames = request.getParameterNames();

        if (parameterNames.hasMoreElements()){

            boolean id_found =false;
            int game_id = 0;
            try{
                game_id = Integer.parseInt(request.getParameter((String)parameterNames.nextElement()));
                id_found = true;
            }catch (NumberFormatException|ClassCastException e){
                request.setAttribute("error", "Unable to pass game ID");
                request.setAttribute("exception", e.toString());
                request.getRequestDispatcher("/WEB-INF/jsp/Error.jsp").forward(request, response);
            }

            if (id_found){
                try {
                    Game game_searched = Game.find(game_id);
                    request.setAttribute("game", game_searched);
                } catch (Exception e) {
                    request.setAttribute("error", "No corresponding game found!");
                }
                request.getRequestDispatcher("/WEB-INF/jsp/GameDetails.jsp").forward(request, response);
            }

        }else{
            request.setAttribute("error", "No game ID found");
            request.getRequestDispatcher("/WEB-INF/jsp/Error.jsp").forward(request, response);
        }

    }
}
