package model;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dbconn.DBConnection;
import exceptions.GameInCartException;
import exceptions.GameNotFoundException;
import exceptions.OutOfStockException;

import java.sql.*;

public class CartGame {
    private int id;
    private Game game;
    private int quantity;

    public CartGame() {
        this.id = -1;
        this.game = null;
        this.quantity = 0;
    }

    public CartGame(Game game, int quantity) {
        this.game = game;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static CartGame insertGame(int gameId, ShoppingCart cart, int quantity)
            throws GameNotFoundException, SQLException, GameInCartException, OutOfStockException {

        Game gameToAdd;
        try {
            gameToAdd = Game.find(gameId);
        } catch (Exception e) {
            throw new GameNotFoundException();
        }

        if (gameToAdd == null) {
            throw new GameNotFoundException();
        }else if(gameToAdd.getQtyInStock() < quantity){
            throw new OutOfStockException(gameToAdd.getName());
        }

        try (Connection conn = DBConnection.createConnection()) {

            final String insertStatementQuery = "INSERT INTO cart_game (game_id, cart_id, quantity)" +
                    "VALUES (?, ?, ?);";

            CartGame newCardGame = new CartGame(gameToAdd, quantity);

            assert conn != null;
            PreparedStatement insertStatement = conn.prepareStatement(insertStatementQuery, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, newCardGame.getGame().getId());
            insertStatement.setInt(2, cart.getId());
            insertStatement.setInt(3, newCardGame.getQuantity());

            int addedRow = insertStatement.executeUpdate();

            if (addedRow == 0) {
                throw new SQLException("Error while creating Cart Item. No rows affected.");
            }

            try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newCardGame.setId(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Unable to get ID of new Cart Item");
                }
            }

            return newCardGame;

        } catch (MySQLIntegrityConstraintViolationException e){
            throw new GameInCartException();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    public void updateQty(Integer qty) throws GameInCartException, SQLException {

        try (Connection conn = DBConnection.createConnection()) {

            final String updateStatementQuery = "UPDATE cart_game SET quantity=? WHERE id=?";

            assert conn != null;
            PreparedStatement updateStatement = conn.prepareStatement(updateStatementQuery, Statement.RETURN_GENERATED_KEYS);
            updateStatement.setInt(1, qty);
            updateStatement.setInt(2, this.getId());

            int addedRow = updateStatement.executeUpdate();

            if (addedRow == 0) {
                throw new SQLException("Error while creating Cart Item. No rows affected.");
            }else{
                this.setQuantity(qty);
            }

        } catch (MySQLIntegrityConstraintViolationException e){
            throw new GameInCartException();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    public static void removeGame(Integer cartGameId) throws SQLException {

        try (Connection conn = DBConnection.createConnection()) {

            final String deleteStatementQuery = "DELETE FROM cart_game WHERE id=?";

            assert conn != null;
            PreparedStatement deleteStatement = conn.prepareStatement(deleteStatementQuery, Statement.RETURN_GENERATED_KEYS);
            deleteStatement.setInt(1, cartGameId);

            deleteStatement.executeUpdate();

        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    public static void removeAllCartGame(int cartId) throws SQLException {

        try (Connection conn = DBConnection.createConnection()) {

            final String deleteStatementQuery = "DELETE FROM cart_game WHERE cart_id=?";

            assert conn != null;
            PreparedStatement deleteStatement = conn.prepareStatement(deleteStatementQuery, Statement.RETURN_GENERATED_KEYS);
            deleteStatement.setInt(1, cartId);

            deleteStatement.executeUpdate();

        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    public static CartGame load(ResultSet rs) {

        CartGame shoppingCart = new CartGame();
        try {
            shoppingCart.setId(rs.getInt("id"));
            shoppingCart.setQuantity(rs.getInt("quantity"));
            shoppingCart.setGame(Game.find(rs.getInt("game_id")));
        } catch (Exception ignored) {
        }
        return shoppingCart;

    }

}
