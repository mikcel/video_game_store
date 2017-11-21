package model;

import dbconn.DBConnection;
import exceptions.GameNotFoundException;

import java.sql.*;
import java.util.ArrayList;

public class ShoppingCart {
    private int id;
    private java.sql.Timestamp last_updated;
    private ArrayList<CartGame> games;

    public ShoppingCart(int id, Timestamp last_updated, ArrayList<CartGame> games) {
        this.id = id;
        this.last_updated = last_updated;
        this.games = games;
    }

    public ShoppingCart(Timestamp last_updated, ArrayList<CartGame> games) {
        this.last_updated = last_updated;
        this.games = games;
    }

    public ShoppingCart() {
        this.id = -1;
        this.last_updated = null;
        this.games = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public java.sql.Timestamp getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(java.sql.Timestamp last_updated) {
        this.last_updated = last_updated;
    }

    public ArrayList<CartGame> getGames() {
        return games;
    }

    public void setGames(ArrayList<CartGame> games) {
        this.games = games;
    }

    public static ShoppingCart find(int userId) throws SQLException {

        try (Connection conn = DBConnection.createConnection()) {

            final String findStatementQuery = "SELECT * FROM shopping_cart WHERE user_id = ?;";

            assert conn != null;
            PreparedStatement findStatement = conn.prepareCall(findStatementQuery);
            findStatement.setInt(1, userId);

            return checkLoadCart(findStatement.executeQuery());

        } catch (Exception e) {

            e.printStackTrace();
            throw e;

        }

    }

    public static ShoppingCart createCart(int userId) throws SQLException {

        try (Connection conn = DBConnection.createConnection()) {

            final String createStatementQuery = "INSERT INTO shopping_cart (user_id, last_updated) " +
                    "VALUES (?, ?);";

            ShoppingCart newCart = new ShoppingCart(new Timestamp(new java.util.Date().getTime()), new ArrayList<CartGame>(0));

            assert conn != null;
            PreparedStatement createStatement = conn.prepareStatement(createStatementQuery, Statement.RETURN_GENERATED_KEYS);
            createStatement.setInt(1, userId);
            createStatement.setTimestamp(2, newCart.getLast_updated());

            int addedRow = createStatement.executeUpdate();

            if (addedRow == 0) {
                throw new SQLException("Shopping Cart creation failed");
            }

            try (ResultSet generatedKeys = createStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newCart.setId(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("No ID obtained for new Shopping cart");
                }
            }

            return newCart;

        } catch (Exception e) {

            e.printStackTrace();
            throw e;

        }

    }

    private static ShoppingCart checkLoadCart(ResultSet resultSet) throws SQLException {
        boolean recordExists = resultSet.next();
        if (recordExists) {
            return ShoppingCart.load(resultSet);
        } else {
            return null;
        }
    }

    private static ShoppingCart load(ResultSet rs) {

        ShoppingCart shoppingCart = new ShoppingCart();
        try {
            shoppingCart.setId(rs.getInt("id"));
            shoppingCart.setLast_updated(rs.getTimestamp("last_updated"));

            shoppingCart.games = loadCartGame(shoppingCart.getId());

        } catch (SQLException ignored) {
        }
        return shoppingCart;

    }

    public static ArrayList<CartGame> loadCartGame(int shoppingCartId) throws SQLException {

        ArrayList<CartGame> returnedItems = new ArrayList<>();

        try (Connection conn = DBConnection.createConnection()) {

            final String findStatementQuery = "SELECT * FROM cart_game WHERE cart_id = ?;";

            assert conn != null;
            PreparedStatement findStatement = conn.prepareCall(findStatementQuery);
            findStatement.setInt(1, shoppingCartId);

            ResultSet rs = findStatement.executeQuery();
            while (rs.next()) {
                returnedItems.add(CartGame.load(rs));
            }

            return returnedItems;

        } catch (Exception e) {

            e.printStackTrace();
            throw e;

        }

    }

}
