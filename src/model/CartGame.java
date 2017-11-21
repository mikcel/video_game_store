package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CartGame {
  private int id;
  private Game game;
  private int quantity;

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
