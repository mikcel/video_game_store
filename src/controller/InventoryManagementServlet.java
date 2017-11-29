package controller;

import model.Game;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "InventoryManagementServlet")
public class InventoryManagementServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int game_id;
        try {
            game_id = Integer.parseInt(request.getParameter("game_id"));
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid game id passed!");
            return;
        }

        int new_qty;
        try {
            new_qty = Integer.parseInt(request.getParameter("new_qty"));
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid game id passed!");
            return;
        }

        try {
            Game requestedGame = Game.find(game_id);

            assert requestedGame != null;
            requestedGame.updateQty(new_qty);

            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            response.getWriter().write("Game qty updated successfully");

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Error while updating qty for game");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // If logged in go back to main page
        HttpSession session = request.getSession(false);
        if(session!= null && session.getAttribute("u_id") != null && ((User) session.getAttribute("user")).isAdmin()){
            try {
                Game[] games = Game.findAll();

                int totalQty = 0;
                for (Game game: games){
                    totalQty += game.getQtyInStock();
                }

                request.setAttribute("games", games);
                request.setAttribute("total_game", totalQty);
                request.getRequestDispatcher("/WEB-INF/jsp/Inventory.jsp").forward(request, response);
            } catch (Exception e) {
                request.getRequestDispatcher("/WEB-INF/jsp/Error.jsp").forward(request, response);
            }
        }else{
            response.sendRedirect(request.getContextPath() + "/");
        }
    }
}
