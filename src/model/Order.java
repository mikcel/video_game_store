package model;

import dbconn.DBConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class Order {
    private int id;
    private int user_id;
    private ArrayList<OrderGame> games;
    private Float total;
    private java.util.Date order_date;

    public Order(int user_id, ArrayList<OrderGame> games, Float total, Date order_date) {
        this.user_id = user_id;
        this.games = games;
        this.total = total;
        this.order_date = order_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public ArrayList<OrderGame> getGames() {
        return games;
    }

    public void setGames(ArrayList<OrderGame> games) {
        this.games = games;
    }

    public static Order processOrder(User buyer, Float orderTotal) throws SQLException {

        try (Connection conn = DBConnection.createConnection()) {

            final String createStatementQuery = "INSERT INTO orders (user_id, total, order_date) VALUES (?, ?, ?);";

            Order newOrder = new Order(buyer.getId(), new ArrayList<OrderGame>(), orderTotal, new Date());

            assert conn != null;
            PreparedStatement createStatement = conn.prepareStatement(createStatementQuery, Statement.RETURN_GENERATED_KEYS);
            createStatement.setInt(1, newOrder.getUser_id());
            createStatement.setFloat(2, newOrder.getTotal());
            createStatement.setTimestamp(3, new java.sql.Timestamp(newOrder.getOrder_date().getTime()));

            int addedRow = createStatement.executeUpdate();

            if (addedRow == 0) {
                throw new SQLException("Order creation failed");
            }

            try (ResultSet generatedKeys = createStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newOrder.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("No ID obtained for new Shopping cart");
                }
            }

            return newOrder;

        } catch (Exception e) {

            e.printStackTrace();
            throw e;

        }

    }
}
