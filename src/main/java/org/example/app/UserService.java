package org.example.app;


import org.example.infrastructure.annotation.Component;
import org.example.infrastructure.annotation.Inject;
import org.example.infrastructure.annotation.Log;
import org.example.infrastructure.annotation.Property;

import java.util.List;

@Component
public class UserService {
    @Inject
    private UserRepository userRepository;
    @Property("emailService")
    private String emailService;
    public void save_user () {
        System.out.println("New service was made");
        System.out.println("New email was set " + emailService);
    };
}

