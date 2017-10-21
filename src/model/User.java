package model;

import dbconn.DBConnection;
import exceptions.UserExistsException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
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

    public User(String password, String email) {
        this.password = password;
        this.email = email;
    }

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

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
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

    public Date getLastLogin() {
        return last_login;
    }

    public void setLastLogin(Date last_login) {
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

            return check_load_user(findStatement.executeQuery());

        } catch (Exception e) {

            e.printStackTrace();
            throw e;

        }

    }

    public static User find(int id) throws Exception {

        try (Connection conn = DBConnection.createConnection()){

            final String findStatementQuery = "SELECT id, first_name, last_name, email, password "
                    + "FROM user WHERE id = ?;";

            assert conn != null;
            PreparedStatement findStatement = conn.prepareCall(findStatementQuery);
            findStatement.setInt(1, id);

            return check_load_user(findStatement.executeQuery());

        } catch (Exception e) {

            e.printStackTrace();
            throw e;

        }

    }

    public static User check_login(String email, String password) throws Exception{

        try (Connection conn = DBConnection.createConnection()){

            final String findUserQuery = "SELECT id, first_name, last_name, email FROM user WHERE email=? and password=?";

            assert conn != null;
            PreparedStatement findQuery = conn.prepareCall(findUserQuery);
            findQuery.setString(1, email);
            findQuery.setString(2, password);

            User user_logged_in = check_load_user(findQuery.executeQuery());
            if (user_logged_in != null){
                update_last_login_user(user_logged_in);
            }
            return user_logged_in;

        }catch (Exception e){

            e.printStackTrace();
            throw e;

        }

    }

    private static void update_last_login_user(User user) throws SQLException {

        try (Connection conn = DBConnection.createConnection()){

            final String findUserQuery = "UPDATE user SET last_login=? WHERE id=?;";

            assert conn != null;
            PreparedStatement findQuery = conn.prepareCall(findUserQuery);
            java.sql.Timestamp curr_timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
            findQuery.setTimestamp(1, curr_timestamp);
            findQuery.setInt(2, user.getId());

            findQuery.executeUpdate();

        }

    }

    private static User load(ResultSet rs) throws SQLException {

        int id = 0;
        String firstName = null, lastName = null, email = null, password = null;
        try{
            id = rs.getInt("id");
            firstName = rs.getString("first_name");
            lastName = rs.getString("last_name");
            email = rs.getString("email");
            password = rs.getString("password");
        }catch (SQLException ignored){
        }

        return new User(id, password, firstName, lastName, email);

    }

    private static User check_load_user(ResultSet resultSet) throws SQLException {
        boolean recordExists = resultSet.next();
        if(recordExists) {
            return User.load(resultSet);
        }else {
            return null;
        }
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
