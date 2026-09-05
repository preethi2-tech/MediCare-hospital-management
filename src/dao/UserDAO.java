package dao;

import model.User;
import util.DBConnection;
import util.PasswordUtil;
import java.sql.*;

public class UserDAO {
    public User authenticate(String username, String password) throws SQLException {
        String sql="SELECT * FROM users WHERE username=? AND is_active=1";
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)){ p.setString(1, username); try(ResultSet r=p.executeQuery()){ if(!r.next()) return null; User u=map(r); return PasswordUtil.verifyPassword(password,u.getPasswordHash(),u.getSalt())?u:null; } }
    }
    private User map(ResultSet r)throws SQLException{ User u=new User(); u.setUserId(r.getInt("user_id")); u.setUsername(r.getString("username")); u.setPasswordHash(r.getString("password_hash")); u.setSalt(r.getString("salt")); u.setRole(r.getString("role")); u.setFullName(r.getString("full_name")); u.setEmail(r.getString("email")); u.setPhone(r.getString("phone")); u.setActive(r.getBoolean("is_active")); return u; }
}
