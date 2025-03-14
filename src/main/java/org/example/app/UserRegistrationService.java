package org.example.app;


import lombok.Getter;
import org.example.infrastructure.annotation.*;


@Component
@Scope(ScopeType.SINGLETON)
public class UserRegistrationService {

    @Inject
    @Qualifier(UserInMemoryRepository.class)
    @Getter
    private UserRepository userRepository;

    @Inject
    private EmailSender emailSender;

    public  UserRegistrationService() {
    }
    public void register(User user) {
        User existingUser = userRepository.getUser(user.getUsername());
        if (existingUser != null) {
            throw new UserAlreadyExistsException(
                    "User is already registered. Username: " + user.getUsername()
            );
        }

        userRepository.save(user);

        emailSender.send(
                user.getEmail(),
                "Account confirmation",
                "Please confirm your newly created account"
        );
    }
}
