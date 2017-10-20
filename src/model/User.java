package model;

import dbconn.DBConnection;
import exceptions.UserExistsException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class User {

	private int id;
	private String password;
	private String first_name;
	private String last_name;
	private String email;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zip;
	private String country;
	private String credit_card_type;
	private long credit_card_number;
	private int credit_card_cvv;
	private Date credit_card_expiry;
	private Date last_login;

	public User(String password, String first_name, String last_name, String email) {
		this.password = password;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
	}
	
	public User(int id, String password, String first_name, String last_name, String email) {
		this.id = id;
		this.password = password;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getLast_login() {
		return last_login;
	}

	public void setLast_login(Date last_login) {
		this.last_login = last_login;
	}

	public void registerUser() throws Exception {

		final String insertStatementQuery = "INSERT INTO user (id, password, first_name, last_name, email) VALUES (?, ?, ?, ?, ?)";

		User exists_user = User.find(email);

		if (exists_user != null){
			throw new UserExistsException();
		}

		try (Connection conn = DBConnection.createConnection()){

			assert conn != null;
			PreparedStatement insertStatementString = conn.prepareStatement(insertStatementQuery);
			
			this.id = findNextUserId();
			insertStatementString.setInt(1, this.id);
			insertStatementString.setString(2, this.password);
			insertStatementString.setString(3, this.first_name);
			insertStatementString.setString(4, this.last_name);
			insertStatementString.setString(5, this.email);
			insertStatementString.execute();

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	public static User find(String email) throws Exception {

		try (Connection conn = DBConnection.createConnection()){

			final String findStatementQuery = "SELECT id, first_name, last_name, email, password "
					+ "FROM user WHERE email LIKE ?;";

			assert conn != null;
			PreparedStatement findStatement = conn.prepareCall(findStatementQuery);
			findStatement.setString(1, email);
			
			ResultSet resultSet = findStatement.executeQuery();
			boolean recordExists = resultSet.next();
			
			if(recordExists) {
				return User.load(resultSet);
			}else {
				return null;
			}

		} catch (Exception e) {

			e.printStackTrace();
			throw e;

		}

	}
	
	private static User load(ResultSet rs) throws SQLException {
		
		int id = rs.getInt("id");
		String firstName = rs.getString("first_name");
		String lastName = rs.getString("last_name");
		String email = rs.getString("email");
		String password = rs.getString("password");

		return new User(id, password, firstName, lastName, email);
		
	}

	private static int findNextUserId() {

		try (Connection conn = DBConnection.createConnection()){

			final String lastIdQuery = "SELECT id FROM user ORDER BY id DESC LIMIT 1";

			assert conn != null;
			PreparedStatement lastIdStatement = conn.prepareCall(lastIdQuery);
			ResultSet resultSet = lastIdStatement.executeQuery();
			boolean recordExists = resultSet.next();
			if(recordExists) {
				return resultSet.getInt("id") + 1;
			}else {
				return 1;
			}

		} catch (Exception e) {

			e.printStackTrace();
			return -1;

		}

	}

}
