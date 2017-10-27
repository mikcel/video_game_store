package model;

import dbconn.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Game {

    private int id;
    private String name;
    private String description;
    private String console;
    private Integer numPlayers;
    private String coop;
    private String genre;
    private java.sql.Date releaseDate;
    private String developer;
    private String publisher;
    private String frontBoxArt;
    private String backBoxArt;
    private String logo;
    private String developerLogo;
    private Double price;
    private Double discount;
    private Comment[] comments;

    public Game(int id) {
        this.id = id;
    }

    public Game(int id, String name, String description, String console, Integer numPlayers, String coop, String genre,
                java.sql.Date releaseDate, String developer, String publisher, String frontBoxArt, String backBoxArt,
                String logo, String developerLogo, Double price, Double discount, Comment[] comments) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.console = console;
        this.numPlayers = numPlayers;
        this.coop = coop;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.developer = developer;
        this.publisher = publisher;
        this.frontBoxArt = frontBoxArt;
        this.backBoxArt = backBoxArt;
        this.logo = logo;
        this.developerLogo = developerLogo;
        this.price = price;
        this.discount = discount;
        this.comments = comments.clone();
    }

    public Game(String name, String console, Integer numPlayers, String coop, String genre,
                java.sql.Date releaseDate, String developer, String publisher, Double price) {
        this.name = name;
        this.console = console;
        this.numPlayers = numPlayers;
        this.coop = coop;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.developer = developer;
        this.publisher = publisher;
        this.price = price;
        this.comments = null;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getConsole() {
        return console;
    }

    public Integer getNumPlayers() {
        return numPlayers;
    }

    public String getCoop() {
        return coop;
    }

    public String getGenre() {
        return genre;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public String getDeveloper() {
        return developer;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getFrontBoxArt() {
        return frontBoxArt;
    }

    public String getBackBoxArt() {
        return backBoxArt;
    }

    public String getLogo() {
        return logo;
    }

    public String getDeveloperLogo() {
        return developerLogo;
    }

    public Double getPrice() {
        return price;
    }

    public Double getDiscount() {
        return discount;
    }

    public Comment[] getComments() {
        return comments;
    }

    public static Game find(int id) throws Exception {

        try (Connection conn = DBConnection.createConnection()) {

            final String findByIdQuery = "SELECT * FROM game WHERE game_id=?";

            assert conn != null;
            PreparedStatement findByIdStatement = conn.prepareStatement(findByIdQuery);
            findByIdStatement.setInt(1, id);

            ResultSet resultSet = findByIdStatement.executeQuery();
            boolean game_exists = resultSet.next();

            if (game_exists) {
                return Game.load(resultSet, Comment.findByGameId(id));
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    public static Game[] findAll() throws Exception {

        try (Connection conn = DBConnection.createConnection()) {

            final String findByIdQuery = "SELECT * FROM game";

            assert conn != null;
            return constructGameList(conn.prepareStatement(findByIdQuery).executeQuery());

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    public static String[] getAllDevelopers() throws SQLException {

        final String allDevQuery = "SELECT DISTINCT(developer)\n" +
                "FROM game\n" +
                "WHERE\n" +
                "  developer IS NOT NULL\n" +
                "  AND\n" +
                "  developer <> ''\n" +
                "ORDER BY developer;";

        ArrayList<Object> developers = getFieldDistinctValues(allDevQuery);
        return developers.toArray(new String[developers.size()]);

    }

    public static String[] getAllPublishers() throws SQLException {

        final String allPublQuery = "SELECT DISTINCT(publisher)\n" +
                "FROM game\n" +
                "WHERE\n" +
                "  publisher IS NOT NULL\n" +
                "  AND\n" +
                "  publisher <> ''\n" +
                "ORDER BY publisher;";

        ArrayList<Object> publishers = getFieldDistinctValues(allPublQuery);
        return publishers.toArray(new String[publishers.size()]);

    }

    public static String[] getAllConsoleType() throws SQLException {

        final String allConsoleQuery = "SELECT DISTINCT(console)\n" +
                "FROM game\n" +
                "WHERE\n" +
                "  console IS NOT NULL\n" +
                "  AND\n" +
                "  console <> ''\n" +
                "ORDER BY console;";

        ArrayList<Object> consoles = getFieldDistinctValues(allConsoleQuery);
        return consoles.toArray(new String[consoles.size()]);


    }

    public static String[] getAllGenre() throws SQLException {

        final String allGenreQuery = "SELECT DISTINCT(genre)\n" +
                "FROM game\n" +
                "WHERE\n" +
                "  genre IS NOT NULL\n" +
                "  AND\n" +
                "  genre <> ''\n" +
                "ORDER BY genre;";

        ArrayList<Object> genres = getFieldDistinctValues(allGenreQuery);
        return genres.toArray(new String[genres.size()]);

    }

    public static Game[] findAllGameWithDisc() throws Exception {

        try (Connection conn = DBConnection.createConnection()) {

            final String filterQuery = "SELECT * FROM game WHERE " + "discount IS NOT NULL AND discount > 0; ";

            assert conn != null;
            PreparedStatement statement = conn.prepareStatement(filterQuery);
            ResultSet resultSet = statement.executeQuery();
            return constructGameList(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static Game[] findbyGameFilter(Game gameFilter) throws Exception {

        // Construct query
        String searchQuery = "SELECT * FROM game ";
        List<String> clauses = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();

        if (gameFilter.name != null && gameFilter.name.length() > 0) {
            clauses.add("game_name = ?");
            parameters.add(gameFilter.name);
        }
        if (gameFilter.console != null && gameFilter.console.length() > 0) {
            clauses.add("console = ?");
            parameters.add(gameFilter.console);
        }
        if (gameFilter.coop != null && gameFilter.coop.length() > 0) {
            clauses.add("coop = ?");
            parameters.add(gameFilter.coop);
        }
        if (gameFilter.genre != null && gameFilter.genre.length() > 0) {
            clauses.add("genre = ?");
            parameters.add(gameFilter.genre);
        }
        if (gameFilter.releaseDate != null) {
            clauses.add("release_date = ?");
            parameters.add(gameFilter.releaseDate);
        }
        if (gameFilter.developer != null && gameFilter.developer.length() > 0) {
            clauses.add("developer = ?");
            parameters.add(gameFilter.developer);
        }
        if (gameFilter.publisher != null && gameFilter.publisher.length() > 0) {
            clauses.add("publisher = ?");
            parameters.add(gameFilter.publisher);
        }
        if (gameFilter.numPlayers != null) {
            clauses.add("num_players = ?");
            parameters.add(gameFilter.numPlayers);
        }
        if (gameFilter.price != null) {
            clauses.add("price = ?");
            parameters.add(gameFilter.price);
        }

        if (!clauses.isEmpty()) {
            searchQuery += "WHERE " + String.join(" AND ", clauses);
        }

        searchQuery += " ORDER BY game_name;";

        try (Connection conn = DBConnection.createConnection()) {

            assert conn != null;
            PreparedStatement searchStatement = conn.prepareStatement(searchQuery);
            for (int i = 0; i < parameters.size(); i++) {
                searchStatement.setObject(i + 1, parameters.get(i));
            }

            ResultSet resultSet = searchStatement.executeQuery();
            return constructGameList(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    private static ArrayList<Object> getFieldDistinctValues(String query) throws SQLException {

        try (Connection conn = DBConnection.createConnection()) {

            assert conn != null;
            PreparedStatement findStatement = conn.prepareStatement(query);
            ResultSet resultSet = findStatement.executeQuery();
            ArrayList<Object> stringArrayList = new ArrayList<>(45);
            while (resultSet.next()) {
                stringArrayList.add(resultSet.getObject(1));
            }
            stringArrayList.trimToSize();
            return stringArrayList;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

    }

    private static Game load(ResultSet rs, Comment[] comments) throws SQLException {

        return new Game(
                rs.getInt("game_id"),
                rs.getString("game_name"),
                rs.getString("game_description"),
                rs.getString("console"),
                rs.getInt("num_players"),
                rs.getString("coop"),
                rs.getString("genre"),
                rs.getDate("release_date"),
                rs.getString("developer"),
                rs.getString("publisher"),
                rs.getString("front_box_art"),
                rs.getString("back_box_art"),
                rs.getString("logo"),
                rs.getString("developer_logo"),
                rs.getDouble("price"),
                rs.getDouble("discount"),
                comments
        );

    }

    private static Game[] constructGameList(ResultSet resultSet) throws Exception {

        ArrayList<Game> gameList = new ArrayList<Game>(10);
        while (resultSet.next()) {
            gameList.add(load(resultSet, Comment.findByGameId(resultSet.getInt("game_id"))));
        }
        gameList.trimToSize();
        return gameList.toArray(new Game[gameList.size()]);

    }

}
