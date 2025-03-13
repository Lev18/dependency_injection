package org.example.app;

import org.example.infrastructure.annotation.CacheKey;
import org.example.infrastructure.annotation.Cacheable;

import java.util.List;

public interface UserRepository {

    void save(User user);
    @Cacheable
    User getUser(@CacheKey String username);

    List<User> getAll();
}
