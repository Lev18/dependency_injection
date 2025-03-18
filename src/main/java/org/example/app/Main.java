package org.example.app;

import org.example.infrastructure.Application;
import org.example.infrastructure.ApplicationContext;

public class Main {
    public static void main(String[] args) {

        ApplicationContext context = Application.run("org.example");
        UserService userService = context.getObject(UserService.class);

//        UserRegistrationService registrationService = context.getObject(UserRegistrationService.class);
//        UserRegistrationService registrationService1 = context.getObject(UserRegistrationService.class);
//        UserService userService = context.getObject(UserService.class);
//        userService.save_user();

//        registrationService.register(
//                new User(
//                        "Gurgen",
//                        "gurgen@inconceptlabs.com",
//                        "password123"
//                )
//        );
//       System.out.println(registrationService.getUserRepository().getUser("Gurgen"));
   }
}
