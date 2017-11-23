package model;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dbconn.DBConnection;
import exceptions.GameOrderedProcessedException;

import java.sql.*;

public class OrderGame {
    private int id;
    private Game game;
    private int quantity;

    public OrderGame(Game game, int quantity) {
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

    public static OrderGame insertGame(Game game, Order order, int quantity) throws SQLException, GameOrderedProcessedException {

        try (Connection conn = DBConnection.createConnection()) {

            final String insertStatementQuery = "INSERT INTO orders_games (order_id, game_id, quantity)" +
                    "VALUES (?, ?, ?);";

            OrderGame newOrderGame = new OrderGame(game, quantity);

            assert conn != null;
            PreparedStatement insertStatement = conn.prepareStatement(insertStatementQuery, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, order.getId());
            insertStatement.setInt(2, newOrderGame.getGame().getId());
            insertStatement.setInt(3, newOrderGame.getQuantity());

            int addedRow = insertStatement.executeUpdate();

            if (addedRow == 0) {
                throw new SQLException("Error while creating Cart Item. No rows affected.");
            }

            try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newOrderGame.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Unable to get ID of new Cart Item");
                }
            }

            return newOrderGame;

        } catch (MySQLIntegrityConstraintViolationException e) {
            throw new GameOrderedProcessedException();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

}
