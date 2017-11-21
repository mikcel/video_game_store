package model;

import java.util.ArrayList;

public class Order {
  private Long id;
  private Long user_id;
  private ArrayList<OrderGame> games;
  private Double total;
  private java.sql.Timestamp order_date;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUser_id() {
    return user_id;
  }

  public void setUser_id(Long user_id) {
    this.user_id = user_id;
  }

  public Double getTotal() {
    return total;
  }

  public void setTotal(Double total) {
    this.total = total;
  }

  public java.sql.Timestamp getOrder_date() {
    return order_date;
  }

  public void setOrder_date(java.sql.Timestamp order_date) {
    this.order_date = order_date;
  }

  public ArrayList<OrderGame> getGames() {
    return games;
  }

  public void setGames(ArrayList<OrderGame> games) {
    this.games = games;
  }
}
