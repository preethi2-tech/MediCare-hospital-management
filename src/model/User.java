package model;

public class User {
    private int userId; private String username, passwordHash, salt, role, fullName, email, phone; private boolean active = true;
    public User() {}
    public User(String username, String passwordHash, String salt, String role, String fullName, String email, String phone) { this.username=username; this.passwordHash=passwordHash; this.salt=salt; this.role=role; this.fullName=fullName; this.email=email; this.phone=phone; }
    public int getUserId(){return userId;} public void setUserId(int v){userId=v;} public String getUsername(){return username;} public void setUsername(String v){username=v;} public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String v){passwordHash=v;} public String getSalt(){return salt;} public void setSalt(String v){salt=v;} public String getRole(){return role;} public void setRole(String v){role=v;} public String getFullName(){return fullName;} public void setFullName(String v){fullName=v;} public String getEmail(){return email;} public void setEmail(String v){email=v;} public String getPhone(){return phone;} public void setPhone(String v){phone=v;} public boolean isActive(){return active;} public void setActive(boolean v){active=v;}
    public String toString(){return fullName+" ("+role+")";}
}
