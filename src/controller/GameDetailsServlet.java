package controller;

import com.sun.xml.internal.ws.util.StringUtils;
import model.Game;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;

@WebServlet(name = "GameDetailsServlet")
public class GameDetailsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Enumeration<?> enumeration = request.getParameterNames();
        HashMap<String, Object> params = new HashMap<>();

        // Get all parameters and store with corresponding values in a hash map
        while (enumeration.hasMoreElements()) {
            String param = (String) enumeration.nextElement();
            params.put(param, request.getParameter(param));
        }

        if (!params.containsKey("game_id")){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Unable to get game id");
            return;
        }

        // Validate the values of the different parameters if present
        try {
            params.replace("num_players", Integer.parseInt((String) params.get("num_players")));
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("No. of players can only be an integer!");
            return;
        }

        try {
            params.replace("price", Double.parseDouble((String) params.get("price")));
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Price can only be a double!");
            return;
        }

        try {
            params.replace("discount", Double.parseDouble((String) params.get("discount")));
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Discount can only be a double!");
            return;
        }

        try{
            String coop_val = ((String) params.get("game_coop")).toLowerCase();
            if (!coop_val.equals("yes") && !coop_val.equals("no")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Co-op value can only be yes or no");
                return;
            }
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Wrong value for co-op!");
            return;
        }


        try {
            String show_only_register = (String) params.get("show_only_register");
            switch (show_only_register) {
                case "on":
                    params.replace("show_only_register", true);
                    break;
                case "off":
                    params.replace("show_only_register", false);
                    break;
                default:
                    throw new Exception();
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid Show only to register");
            return;
        }

        // Format date if present
        if (params.containsKey("release_date")) {
            try {
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = sdf1.parse((String) params.get("release_date"));
                params.replace("release_date", new java.sql.Date(date.getTime()));
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Release date is not in a correct format: dd-MM-yyyy");
                return;
            }
        }

        if (params.containsKey("qty")) {
            try {
                params.replace("qty", Integer.parseInt((String) params.get("qty")));
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Qty in stock can only be an integer!");
                return;
            }
        }

        try {
            Game requestedGame = Game.find(Integer.parseInt((String) params.get("game_id")));

            assert requestedGame != null;
            requestedGame.setName((String) params.get("game_name"));
            requestedGame.setConsole((String) params.get("console"));
            requestedGame.setNumPlayers((Integer) params.get("num_players"));
            requestedGame.setGenre((String) params.get("genre"));
            requestedGame.setCoop(StringUtils.capitalize((String) params.get("game_coop")));
            requestedGame.setReleaseDate((Date) params.get("release_date"));
            requestedGame.setPrice((Double) params.get("price"));
            requestedGame.setDiscount((Double) params.get("discount"));
            requestedGame.setQtyInStock((Integer) params.get("qty"));
            requestedGame.setDeveloper((String) params.get("developer"));
            requestedGame.setPublisher((String) params.get("publisher"));
            requestedGame.setDescription((String) params.get("description"));
            requestedGame.setShowOnlyRegister((Boolean) params.get("show_only_register"));

            requestedGame.updateGame();

            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            response.getWriter().write("Game Updated successfully");

        }catch (SQLException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Error while updating game!");
        } catch (ClassCastException e){
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Unable to use passed parameters!");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Unable to fetch game to update!");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Get Game ID being queried
        Enumeration<?> parameterNames = request.getParameterNames();

        if (parameterNames.hasMoreElements()) {

            boolean id_found = false;
            int game_id = 0;
            try {
                // Convert game id found in url
                game_id = Integer.parseInt(request.getParameter((String) parameterNames.nextElement()));
                id_found = true;
            } catch (NumberFormatException | ClassCastException e) {
                request.setAttribute("error", "Unable to pass game ID");
                request.setAttribute("exception", e.toString());
                request.getRequestDispatcher("/WEB-INF/jsp/Error.jsp").forward(request, response);
            }

            if (id_found) {
                try {
                    // Fetch games details
                    Game game_searched = Game.find(game_id);
                    request.setAttribute("game", game_searched);
                } catch (Exception e) {
                    request.setAttribute("error", "No corresponding game found!");
                }

                request.getRequestDispatcher("/WEB-INF/jsp/GameDetails.jsp").forward(request, response);
            }

        } else {
            request.setAttribute("error", "No game ID found");
            request.getRequestDispatcher("/WEB-INF/jsp/Error.jsp").forward(request, response);
        }

    }
}
