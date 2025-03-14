package org.example.app;

import org.example.infrastructure.annotation.*;

import java.util.ArrayList;
import java.util.List;

public class UserInDb implements UserRepository{
    private List<User> users = new ArrayList<>();

    @Env("database_url")
    private String databaseUrl;

    @Env
    private String databaseUsername;

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
