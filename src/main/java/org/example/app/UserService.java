package org.example.app;


import org.example.infrastructure.annotation.*;

import java.util.List;

@Component
public class UserService {
    @Inject
    @Qualifier(UserInMemoryRepository.class)
    private UserRepository userRepository;

    @Property("emailService")
    private String emailService;
    public void save_user () {
        System.out.println("New service was made");
        System.out.println("New email was set " + emailService);
    };
}

