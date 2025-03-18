package org.example.app;

import org.example.infrastructure.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserInDb implements UserRepository{
    private List<User> users = new ArrayList<>();
    public UserInDb() {
        System.out.println("UserInDb constructor call");
    }

    @Env("database_url")
    private String databaseUrl;

    @Env
    private String databaseUsername;

    @Override
    public void save(User user) {

    }

    @Override
    @Cacheable
    public User getUser(String username) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return users;
    }
}
