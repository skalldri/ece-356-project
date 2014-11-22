package ece356;

public class UserData {

    String username;
    String password;
    String userType;
    String userVariant;

    public String getUsername() {
        return username;
    }

    public void setUsername(String value) {
        username = value;
    }

    public void setPassword(String value) {
        password = value;
    }
    
    public void setUserVariant(String value) {
        userVariant = value;
    }

    public String getPassword() {
        return password;
    }
    
    public void setUserType(String value) {
        userType = value;
    }

    public String getUserType() {
        return userType;
    }
    
    public String getUserVariant() {
        return userVariant;
    }
}