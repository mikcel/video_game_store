package controller;

import model.Game;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(name = "SearchResultsServlet")
public class SearchResultsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Enumeration<?> enumeration = request.getParameterNames();
        HashMap<String, Object> params = new HashMap<>();

        while(enumeration.hasMoreElements()){
            String param = (String) enumeration.nextElement();
            params.put(param, request.getParameter(param));
        }

        if(params.containsKey("game_id") && ((String)params.get("game_id")).length() > 0 ){
            try {
                Game[] gameFound = {Game.find(Integer.parseInt((String) params.get("game_id")))};
                request.setAttribute("gamesFound", gameFound);
                request.getRequestDispatcher("/WEB-INF/jsp/SearchResults.jsp").forward(request, response);
            }catch (NumberFormatException e){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID can only be an integer!");
            }catch (Exception e) {
                response.sendError(HttpServletResponse.SC_CONFLICT, "Error while processing request. Contact Admin!");
            }
        }else{

            boolean correctFields = true;
            if (params.containsKey("num_players")){
                try{
                    params.replace("num_players", Integer.parseInt((String) params.get("num_players")));
                }catch (NumberFormatException e){
                    correctFields = false;
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No. of players can only be an integer!");
                }
            }

            if (params.containsKey("num_players_null") && params.get("num_players_null").equals("true")){
                params.replace("num_players", null);
            }

            if (params.containsKey("price")){
                try{
                    params.replace("price", Double.parseDouble((String) params.get("price")));
                }catch (NumberFormatException e){
                    correctFields = false;
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Price can only be a double!");
                }
            }

            if (params.containsKey("price_null") && params.get("price_null").equals("true")){
                params.replace("price", null);
            }

            if (params.containsKey("game_coop")){
                String coop_val = ((String)params.get("game_coop")).toLowerCase();
                if (!coop_val.equals("yes") && !coop_val.equals("no") && !coop_val.equals("both")){
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Co-op value can only be yes or no");
                    correctFields = false;
                }else if(coop_val.equals("both")){
                    params.replace("game_coop", null);
                }
            }

            if (params.containsKey("release_date") && !params.get("release_date").equals("")){
                try{
                    SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
                    java.util.Date date = sdf1.parse((String) params.get("release_date"));
                    params.replace("release_date", new java.sql.Date(date.getTime()));
                }catch (Exception e){
                    correctFields = false;
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Release date is not in a correct format: dd-MM-yyyy");
                }
            }else{
                params.put("release_date", null);
            }

            if (correctFields){

                boolean forwardRequest = true;
                Game templateGame = null;
                try{
                     templateGame = new Game((String)params.get("game_name"), (String)params.get("console"), (Integer)params.get("num_players"),
                            (String)params.get("game_coop"), (String)params.get("genre"), (java.sql.Date)params.get("release_date"), (String)params.get("developer"),
                            (String)params.get("publisher"), (Double)params.get("price"));
                }catch (Exception e){
                    forwardRequest = false;
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error passing your info!");
                }

                if (forwardRequest){
                    try {
                        Game[] gamesFound = Game.findbyGameFilter(templateGame);
                        request.setAttribute("gamesFound", gamesFound);
                        request.setAttribute("specials", true);
                        request.getRequestDispatcher("/WEB-INF/jsp/SearchResults.jsp").forward(request, response);
                    } catch (Exception e) {
                        response.sendError(HttpServletResponse.SC_CONFLICT, "Error while processing request. Contact Admin!");
                    }
                }

            }

        }
    }
}
