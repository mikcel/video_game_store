package model;

import dbconn.DBConnection;

import java.sql.*;
import java.util.ArrayList;

public class ShoppingCart {
    private int id;
    private ArrayList<CartGame> games;

    public ShoppingCart(int id, ArrayList<CartGame> games) {
        this.id = id;
        this.games = games;
    }

    public ShoppingCart(ArrayList<CartGame> games) {
        this.games = games;
    }

    public ShoppingCart() {
        this.id = -1;
        this.games = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<CartGame> getGames() {
        return games;
    }

    public void setGames(ArrayList<CartGame> games) {
        this.games = games;
    }

    public int getNoItems() {
        int total = 0;
        for (CartGame game : games) {
            total += game.getQuantity();
        }
        return total;
    }

    public CartGame findCartGame(Integer cartGameId) {
        for (CartGame game : games) {
            if (game.getId() == cartGameId) {
                return game;
            }
        }
        return null;
    }

    public void updateGame(CartGame cartGame) {
        games.set(games.indexOf(cartGame), cartGame);
    }

    public void removeGame(Integer cartGameId) throws SQLException {
        CartGame.removeGame(cartGameId);
        games.remove(this.findCartGame(cartGameId));
    }

    public void emptyCart() throws SQLException {

        CartGame.removeAllCartGame(this.id);
        games.clear();

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

            final String createStatementQuery = "INSERT INTO shopping_cart (user_id) " +
                    "VALUES (?);";

            ShoppingCart newCart = new ShoppingCart(new ArrayList<CartGame>(0));

            assert conn != null;
            PreparedStatement createStatement = conn.prepareStatement(createStatementQuery, Statement.RETURN_GENERATED_KEYS);
            createStatement.setInt(1, userId);

            int addedRow = createStatement.executeUpdate();

            if (addedRow == 0) {
                throw new SQLException("Shopping Cart creation failed");
            }

            try (ResultSet generatedKeys = createStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newCart.setId(generatedKeys.getInt(1));
                } else {
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
