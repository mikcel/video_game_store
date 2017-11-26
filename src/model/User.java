package model;

import dbconn.DBConnection;
import exceptions.*;
import stringrandomizer.StringRandomizer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    private String temp_password;
    private Date tmp_pass_ttl;
    private boolean admin;
    private int login_attempts;
    private boolean locked;
    private String login_name;
    private ShoppingCart cart;

    public User() {
        this.first_name = "";
        this.last_name = "";
        this.email = "";
    }

    public User(User other) {
        this.id = other.id;
        this.password = other.password;
        this.first_name = other.first_name;
        this.last_name = other.last_name;
        this.email = other.email;
        this.address1 = other.address1;
        this.address2 = other.address2;
        this.city = other.city;
        this.state = other.state;
        this.zip = other.zip;
        this.country = other.country;
        this.credit_card_type = other.credit_card_type;
        this.credit_card_number = other.credit_card_number;
        this.credit_card_cvv = other.credit_card_cvv;
        this.credit_card_expiry = other.credit_card_expiry;
        this.last_login = other.last_login;
        this.temp_password = other.temp_password;
        this.tmp_pass_ttl = other.tmp_pass_ttl;
        this.admin = other.admin;
        this.login_attempts = other.login_attempts;
        this.locked = other.locked;
        this.login_name = other.login_name;
        this.cart = other.cart;
    }

    public User(String password, String email) {
        this.password = password;
        this.email = email;
    }

    public User(String password, String first_name, String last_name, String email, String address1, String address2,
                String city, String state, String zip, String country, String credit_card_type, long credit_card_number,
                int credit_card_cvv, Date credit_card_expiry, String login_name) {
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
        this.credit_card_type = credit_card_type;
        this.credit_card_number = credit_card_number;
        this.credit_card_cvv = credit_card_cvv;
        this.credit_card_expiry = credit_card_expiry;
        this.login_name = login_name;
    }

    public User(String password, String first_name, String last_name, String email, String address1, String address2) {
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

    public String getCredit_card_type() {
        return credit_card_type;
    }

    public void setCredit_card_type(String credit_card_type) {
        this.credit_card_type = credit_card_type;
    }

    public long getCredit_card_number() {
        return credit_card_number;
    }

    public void setCredit_card_number(long credit_card_number) {
        this.credit_card_number = credit_card_number;
    }

    public int getCredit_card_cvv() {
        return credit_card_cvv;
    }

    public void setCredit_card_cvv(int credit_card_cvv) {
        this.credit_card_cvv = credit_card_cvv;
    }

    public Date getCredit_card_expiry() {
        return credit_card_expiry;
    }

    public void setCredit_card_expiry(Date credit_card_expiry) {
        this.credit_card_expiry = credit_card_expiry;
    }

    public Date getLast_login() {
        return last_login;
    }

    public void setLast_login(Date last_login) {
        this.last_login = last_login;
    }

    public String getTemp_password() {
        return temp_password;
    }

    public void setTemp_password(String temp_password) {
        this.temp_password = temp_password;
    }

    public Date getTmp_pass_ttl() {
        return tmp_pass_ttl;
    }

    public void setTmp_pass_ttl(Date tmp_pass_ttl) {
        this.tmp_pass_ttl = tmp_pass_ttl;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public int getLogin_attempts() {
        return login_attempts;
    }

    public void setLogin_attempts(int login_attempts) {
        this.login_attempts = login_attempts;
    }

    public boolean getLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }

    public void createTmpPass(){

        final String createTmpPassQuery = "UPDATE user SET temp_password=? , tmp_pass_ttl=? WHERE id=?";

        try (Connection conn = DBConnection.createConnection()){

            assert conn != null;
            PreparedStatement tmpPassStatement = conn.prepareStatement(createTmpPassQuery);

            java.sql.Timestamp tmpPassTTL = new java.sql.Timestamp(new Date().getTime());
            String tmpPass = StringRandomizer.generateRandomString();

            tmpPassStatement.setString(1, tmpPass);
            tmpPassStatement.setTimestamp(2, tmpPassTTL);
            tmpPassStatement.setInt(3, this.getId());

            tmpPassStatement.executeUpdate();

            this.setTmp_pass_ttl(tmpPassTTL);
            this.setTemp_password(tmpPass);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void registerUser() throws Exception {

        final String insertStatementQuery = "INSERT INTO user (id, password, first_name, last_name, email, address1, address2," +
                "city, state, zip, country, credit_card_type, credit_card_number, credit_card_cvv, credit_card_expiry, login_name) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            User exists_user = User.find(email, login_name);
            if (exists_user != null) {
                throw new UserExistsException();
            }
        }catch (UserNotExistsException ignored){

        }

        try (Connection conn = DBConnection.createConnection()) {

            assert conn != null;
            PreparedStatement insertStatementString = conn.prepareStatement(insertStatementQuery);

            this.id = findNextUserId();
            insertStatementString.setInt(1, this.id);
            insertStatementString.setString(2, this.password);
            insertStatementString.setString(3, this.first_name);
            insertStatementString.setString(4, this.last_name);
            insertStatementString.setString(5, this.email);
            insertStatementString.setString(6, this.address1);
            insertStatementString.setString(7, this.address2);
            insertStatementString.setString(8, this.city);
            insertStatementString.setString(9, this.state);
            insertStatementString.setString(10, this.zip);
            insertStatementString.setString(11, this.country);
            insertStatementString.setString(12, this.credit_card_type);
            insertStatementString.setLong(13, this.credit_card_number);
            insertStatementString.setInt(14, this.credit_card_cvv);
            insertStatementString.setDate(15, (java.sql.Date) this.credit_card_expiry);
            insertStatementString.setString(16, this.login_name);

            insertStatementString.execute();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    private void updateLoginAttempt() throws SQLException, AccountLockedException {

        try (Connection conn = DBConnection.createConnection()) {

            final String updateQuery = "UPDATE user SET login_attempts=? WHERE id=?;";

            assert conn != null;
            PreparedStatement updateStatement = conn.prepareCall(updateQuery);

            int updatedLoginAttempts = this.getLogin_attempts() + 1;
            updateStatement.setInt(1, updatedLoginAttempts);
            updateStatement.setInt(2, id);
            updateStatement.executeUpdate();

            if (updatedLoginAttempts >= 3){

                final String updateLockedQuery = "UPDATE user SET locked=TRUE WHERE id=?;";

                PreparedStatement updateLockedStatement = conn.prepareCall(updateLockedQuery);

                updateLockedStatement.setInt(1, id);
                updateLockedStatement.executeUpdate();

                throw new AccountLockedException();

            }

        } catch (Exception e) {

            e.printStackTrace();
            throw e;

        }

    }

    public void updatePass(String newPassword) throws SQLException {

        try (Connection conn = DBConnection.createConnection()) {

            final String updateQuery = "UPDATE user SET password=? WHERE id=?;";

            assert conn != null;
            PreparedStatement updateStatement = conn.prepareCall(updateQuery);
            updateStatement.setString(1, newPassword);
            updateStatement.setInt(2, id);
            updateStatement.executeUpdate();

            this.password = newPassword;

        } catch (Exception e) {

            e.printStackTrace();
            throw e;

        }

    }

    public void updateUser() throws SQLException {

        try (Connection conn = DBConnection.createConnection()) {

            final String updateQuery = "UPDATE user SET " +
                    "first_name=?, " +
                    "last_name=?," +
                    "email=?," +
                    "address1=?," +
                    "address2=?," +
                    "city=?," +
                    "state=?," +
                    "zip=?," +
                    "country=?," +
                    "credit_card_type=?," +
                    "credit_card_number=?," +
                    "credit_card_cvv=?," +
                    "credit_card_expiry=? WHERE id=?;";

            assert conn != null;
            PreparedStatement updateStatement = conn.prepareCall(updateQuery);
            updateStatement.setString(1, this.first_name);
            updateStatement.setString(2, this.last_name);
            updateStatement.setString(3, this.email);
            updateStatement.setString(4, this.address1);
            updateStatement.setString(5, this.address2);
            updateStatement.setString(6, this.city);
            updateStatement.setString(7, this.state);
            updateStatement.setString(8, this.zip);
            updateStatement.setString(9, this.country);
            updateStatement.setString(10, this.credit_card_type);
            updateStatement.setLong(11, this.credit_card_number);
            updateStatement.setInt(12, this.credit_card_cvv);
            updateStatement.setDate(13, (java.sql.Date) this.credit_card_expiry);
            updateStatement.setInt(14, this.id);
            updateStatement.executeUpdate();


        } catch (Exception e) {

            e.printStackTrace();
            throw e;

        }


    }

    public boolean checkDuplicateEmail() throws Exception {

        try (Connection conn = DBConnection.createConnection()) {

            final String findStatementQuery = "SELECT * FROM user WHERE email LIKE ? AND id <> ?";

            assert conn != null;
            PreparedStatement findStatement = conn.prepareCall(findStatementQuery);
            findStatement.setString(1, this.email);
            findStatement.setInt(2, this.id);

            return findStatement.executeQuery().next();

        } catch (Exception e) {

            e.printStackTrace();
            throw e;

        }

    }

    public void unlockAccount() throws SQLException {

        try (Connection conn = DBConnection.createConnection()) {

            final String findStatementQuery = "UPDATE user " +
                    "SET locked=?, login_attempts=0, temp_password='', tmp_pass_ttl=NULL WHERE id=?";

            assert conn != null;
            PreparedStatement findStatement = conn.prepareCall(findStatementQuery);
            findStatement.setBoolean(1, false);
            findStatement.setInt(2, this.id);

            findStatement.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
            throw e;

        }

    }

    public void lockAccount() throws SQLException {

        try (Connection conn = DBConnection.createConnection()) {

            final String findStatementQuery = "UPDATE user SET locked=? WHERE id=?";

            assert conn != null;
            PreparedStatement findStatement = conn.prepareCall(findStatementQuery);
            findStatement.setBoolean(1, true);
            findStatement.setInt(2, this.id);

            findStatement.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
            throw e;

        }

    }

    public void setAsAdmin() throws SQLException {

        try (Connection conn = DBConnection.createConnection()) {

            final String findStatementQuery = "UPDATE user SET admin=? WHERE id=?";

            assert conn != null;
            PreparedStatement findStatement = conn.prepareCall(findStatementQuery);
            findStatement.setBoolean(1, true);
            findStatement.setInt(2, this.id);

            findStatement.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
            throw e;

        }

    }

    public static ArrayList<User> getAllUsers() throws SQLException {

        try (Connection conn = DBConnection.createConnection()) {

            final String allUsersQuery = "SELECT * FROM user;";

            assert conn != null;
            PreparedStatement allUsersStatement = conn.prepareCall(allUsersQuery);
            ResultSet resultSet = allUsersStatement.executeQuery();

            ArrayList<User> allUsers = new ArrayList<>();
            while (resultSet.next()){
                allUsers.add(load(resultSet));
            }
            return allUsers;

        } catch (Exception e) {

            e.printStackTrace();
            throw e;

        }

    }

    public static User find(String email, String login_name) throws Exception {

        try (Connection conn = DBConnection.createConnection()) {

            final String findStatementQuery = "SELECT * FROM user WHERE email LIKE ? OR login_name LIKE ?;";

            assert conn != null;
            PreparedStatement findStatement = conn.prepareCall(findStatementQuery);
            findStatement.setString(1, email);
            findStatement.setString(2, login_name);

            return checkLoadUser(findStatement.executeQuery());

        } catch (Exception e) {

            e.printStackTrace();
            throw e;

        }

    }

    public static User find(int id) throws Exception {

        try (Connection conn = DBConnection.createConnection()) {

            final String findStatementQuery = "SELECT * FROM user WHERE id = ?;";

            assert conn != null;
            PreparedStatement findStatement = conn.prepareCall(findStatementQuery);
            findStatement.setInt(1, id);

            return checkLoadUser(findStatement.executeQuery());

        } catch (Exception e) {

            e.printStackTrace();
            throw e;

        }

    }

    public static User check_login(String login_name, String password) throws Exception {

        try (Connection conn = DBConnection.createConnection()) {

            final String findUserQuery = "SELECT * FROM user WHERE login_name=? AND locked=FALSE;";

            assert conn != null;
            PreparedStatement findQuery = conn.prepareCall(findUserQuery);
            findQuery.setString(1, login_name);

            User user_logged_in = checkLoadUser(findQuery.executeQuery());

            if (user_logged_in.password.equals(password)) {
                updateLastLoginUser(user_logged_in);
            } else {
                user_logged_in.updateLoginAttempt();
                throw new IncorrectPasswordException();
            }
            return user_logged_in;

        } catch (UserNotExistsException e) {
            throw e;

        } catch (Exception e) {
            e.printStackTrace();
            throw e;

        }

    }

    public static User reset_password(String loginName, String tmpPassword, String newPassword, String email) throws Exception {

        try (Connection conn  = DBConnection.createConnection()){

            User userExists = User.find(email, loginName);

            if (userExists != null){

                if (userExists.temp_password == null){
                    throw new IncorrectPasswordException("No temporary password requested.");
                }

                if (!userExists.temp_password.equals(tmpPassword)){
                    throw new IncorrectPasswordException();
                }

                long checkTimeTTL = userExists.tmp_pass_ttl.getTime() - new Date().getTime();
                if (checkTimeTTL / (60 * 60 * 1000) % 24 > 24){
                    throw new TempPassExpiredExecption();
                }

                final String setNewPasswordQuery = "UPDATE user SET password=?, temp_password=NULL, " +
                        "tmp_pass_ttl=NULL, login_attempts=0, locked=0 WHERE id=?;";

                assert conn != null;
                PreparedStatement updateQuery = conn.prepareCall(setNewPasswordQuery);
                updateQuery.setString(1, newPassword);
                updateQuery.setInt(2, userExists.getId());

                updateQuery.executeUpdate();

                return userExists;

            }else{
                throw new UserNotExistsException();
            }

        }catch (TempPassExpiredExecption | UserNotExistsException | IncorrectPasswordException e){
            throw e;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }

    }

    private static void updateLastLoginUser(User user) throws SQLException {

        try (Connection conn = DBConnection.createConnection()) {

            final String findUserQuery = "UPDATE user SET last_login=?, login_attempts=0 WHERE id=?;";

            assert conn != null;
            PreparedStatement findQuery = conn.prepareCall(findUserQuery);
            java.sql.Timestamp curr_timestamp = new java.sql.Timestamp(new Date().getTime());
            findQuery.setTimestamp(1, curr_timestamp);
            findQuery.setInt(2, user.getId());

            findQuery.executeUpdate();

        }

    }

    private static User load(ResultSet rs) {

        User loaded_user = new User();
        try {
            loaded_user.setId(rs.getInt("id"));
            loaded_user.setFirstName(rs.getString("first_name"));
            loaded_user.setLastName(rs.getString("last_name"));
            loaded_user.setEmail(rs.getString("email"));
            loaded_user.setPassword(rs.getString("password"));
            loaded_user.setAddress1(rs.getString("address1"));
            loaded_user.setAddress2(rs.getString("address2"));
            loaded_user.setCity(rs.getString("city"));
            loaded_user.setState(rs.getString("state"));
            loaded_user.setZip(rs.getString("zip"));
            loaded_user.setCountry(rs.getString("country"));
            loaded_user.setCredit_card_type(rs.getString("credit_card_type"));
            loaded_user.setCredit_card_number(rs.getLong("credit_card_number"));
            loaded_user.setCredit_card_cvv(rs.getInt("credit_card_cvv"));
            loaded_user.setCredit_card_expiry(rs.getDate("credit_card_expiry"));
            loaded_user.setLastLogin(rs.getTimestamp("last_login"));
            loaded_user.setTemp_password(rs.getString("temp_password"));
            loaded_user.setTmp_pass_ttl(rs.getDate("tmp_pass_ttl"));
            loaded_user.setAdmin(rs.getBoolean("admin"));
            loaded_user.setLogin_attempts(rs.getInt("login_attempts"));
            loaded_user.setLocked(rs.getBoolean("locked"));
            loaded_user.setLogin_name(rs.getString("login_name"));

            loaded_user.setCart(ShoppingCart.find(loaded_user.id));

        } catch (SQLException ignored) {
        }
        return loaded_user;

    }

    private static User checkLoadUser(ResultSet resultSet) throws SQLException, UserNotExistsException {
        boolean recordExists = resultSet.next();
        if (recordExists) {
            return User.load(resultSet);
        } else {
            throw new UserNotExistsException();
        }
    }

    private static int findNextUserId() {

        try (Connection conn = DBConnection.createConnection()) {

            final String lastIdQuery = "SELECT id FROM user ORDER BY id DESC LIMIT 1";

            assert conn != null;
            PreparedStatement lastIdStatement = conn.prepareCall(lastIdQuery);
            ResultSet resultSet = lastIdStatement.executeQuery();
            boolean recordExists = resultSet.next();
            if (recordExists) {
                return resultSet.getInt("id") + 1;
            } else {
                return 1;
            }

        } catch (Exception e) {

            e.printStackTrace();
            return -1;

        }

    }

}
