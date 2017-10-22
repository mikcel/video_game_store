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

@WebServlet(name = "SearchResultsServlet")
public class SearchResultsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Enumeration<?> enumeration = request.getParameterNames();

        while(enumeration.hasMoreElements()){
            String param = (String) enumeration.nextElement();
            System.out.println(param);
            System.out.println(request.getParameter(param));
        }


        // TODO: process request

//        try {
//
//
//            request.setAttribute("gamesFound", gamesFound);
            request.getRequestDispatcher("/WEB-INF/jsp/SearchResults.jsp").forward(request, response);
//        }catch (SQLException e){
//            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
//        }

    }
}
