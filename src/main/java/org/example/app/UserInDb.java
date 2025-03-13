package org.example.app;

import java.util.ArrayList;
import java.util.List;


public class UserInDb implements UserRepository{
    private List<User> users = new ArrayList<>();

    @Override
    public void save(User user) {

    }

    @Override
    public User getUser(String username) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return users;
    }
}
