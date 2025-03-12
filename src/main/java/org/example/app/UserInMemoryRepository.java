package org.example.app;

import org.example.infrastructure.annotation.Component;
import org.example.infrastructure.annotation.Log;

import java.util.ArrayList;
import java.util.List;


// this class serves as imaginary db for user registration
@Component
@Log
public class UserInMemoryRepository implements UserRepository {

    private List<User> users = new ArrayList<>();

    public UserInMemoryRepository() {
        System.out.println("UserInMemoryRepository constructor call");
    }

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        return null;
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users);
    }
}
