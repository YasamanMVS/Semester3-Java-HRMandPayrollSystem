package Model;

import java.util.HashMap;
import java.util.Map;

import Model.User.UserRole;

public class AuthenticationManager {
    private Map<String, User> registeredUser = new HashMap<>();

    public AuthenticationManager() {
        this.registeredUser = new HashMap<>();

        // Pre-populate the map with some sample users
        registeredUser.put("Admin", new User("Admin", "admin", User.UserRole.HR));
        registeredUser.put("Manager", new User("Manager", "manager", User.UserRole.MANAGER));
    }
    public boolean authenticate(String username, String password) {
        User user = registeredUser.get(username);
        if (user != null && user.getPassword().equals(password)) {
            // Authentication successful
            return true;
        }
        // Authentication failed
        return false;
    }
    public void registerUser(String username, String password, UserRole role) {
        // Register a new user
        registeredUser.put(username, new User(username, password, role));
    }

    public UserRole getUserRole(String username) {
        User user = registeredUser.get(username);
        if (user != null) {
            return user.getRole();
        }
        return null;
    }
}
