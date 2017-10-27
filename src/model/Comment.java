package model;

import dbconn.DBConnection;

import java.sql.*;
import java.util.ArrayList;

public class Comment {

    private int id;
    private User user;
    private Game game;
    private java.sql.Date commentDate;
    private String commentDetails;
    private int ratings;

    public Comment(int id, User user, Game game, Date commentDate, String commentDetails, int ratings) {
        this.id = id;
        this.user = user;
        this.game = game;
        this.commentDate = commentDate;
        this.commentDetails = commentDetails;
        this.ratings = ratings;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Game getGame() {
        return game;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public String getCommentDetails() {
        return commentDetails;
    }

    public int getRatings() {
        return ratings;
    }

    public static Comment findById(int commentId) throws Exception {

        final String findQuery = "SELECT * FROM comments WHERE comment_id = ?";

        try (Connection conn = DBConnection.createConnection()){

            assert conn != null;
            PreparedStatement findByIdStatement = conn.prepareStatement(findQuery);
            findByIdStatement.setInt(1, commentId);

            ResultSet resultSet = findByIdStatement.executeQuery();
            boolean game_exists = resultSet.next();

            if (game_exists) {
                return Comment.load(resultSet, true);
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    public static Comment[] findByGameId(int gameId) throws Exception {

        final String findQuery = "SELECT * FROM comments WHERE game_id = ?";

        try (Connection conn = DBConnection.createConnection()){

            assert conn != null;
            PreparedStatement findStatement = conn.prepareStatement(findQuery);
            findStatement.setInt(1, gameId);

            ResultSet resultSet = findStatement.executeQuery();
            return constructCommentList(resultSet, false);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    private static Comment load(ResultSet rs, boolean getGame) throws Exception {

        User comment_user = User.find(rs.getInt("user_id"));
        Game comment_game = null;
        if (getGame){
            comment_game = Game.find(rs.getInt("game_id"));
        }

        return new Comment(
            rs.getInt("comment_id"),
            comment_user,
            comment_game,
            rs.getDate("comment_date"),
            rs.getString("comment_details"),
            rs.getInt("ratings")
        );

    }

    private static Comment[] constructCommentList(ResultSet resultSet, boolean getGame) throws Exception {

        ArrayList<Comment> commentList = new ArrayList<Comment>(10);
        while (resultSet.next()) {
            commentList.add(load(resultSet, getGame));
        }
        commentList.trimToSize();
        return commentList.toArray(new Comment[commentList.size()]);

    }

}
