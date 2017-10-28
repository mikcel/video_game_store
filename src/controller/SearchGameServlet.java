package controller;

import model.Game;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "SearchGameServlet")
public class SearchGameServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Set all attributes to populate dropdowns
            request.setAttribute("developers", Game.getAllDevelopers());
            request.setAttribute("consoles", Game.getAllConsoleType());
            request.setAttribute("publishers", Game.getAllPublishers());
            request.setAttribute("genres", Game.getAllGenre());
            request.getRequestDispatcher("/WEB-INF/jsp/SearchPage.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to fetch category data!");
        }
    }
}
