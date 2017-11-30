package model;

import dbconn.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FavoritesTDG {

    public static Integer[] findUserFav(int userId) throws SQLException {

        try (Connection conn = DBConnection.createConnection()) {

            final String findQuery = "SELECT * FROM favorites WHERE user_id=?;";

            assert conn != null;
            PreparedStatement statement = conn.prepareStatement(findQuery);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            return constructList(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    public static void addToFav(int userId, int gameId) throws SQLException {

        try (Connection conn = DBConnection.createConnection()) {

            final String addQuery = "INSERT INTO favorites VALUES(?,?)";

            assert conn != null;
            PreparedStatement statement = conn.prepareStatement(addQuery);
            statement.setInt(1, userId);
            statement.setInt(2, gameId);

            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }


    public static void deleteFromFav(int userId, int gameId) throws SQLException {

        try (Connection conn = DBConnection.createConnection()) {

            final String deleteQuery = "DELETE FROM favorites WHERE user_id=? AND game_id=?";

            assert conn != null;
            PreparedStatement statement = conn.prepareStatement(deleteQuery);
            statement.setInt(1, userId);
            statement.setInt(2, gameId);

            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    private static Integer[] constructList(ResultSet resultSet) throws SQLException {

        ArrayList<Integer> gameIds = new ArrayList<Integer>(10);
        while (resultSet.next()) {
            gameIds.add(resultSet.getInt("game_id"));
        }
        gameIds.trimToSize();
        return gameIds.toArray(new Integer[gameIds.size()]);

    }

}
