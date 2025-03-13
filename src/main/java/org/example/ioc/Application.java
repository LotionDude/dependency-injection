package org.example.ioc;

import org.example.ioc.application.User;
import org.example.ioc.application.UserService;
import org.example.ioc.library.container.Container;
import org.example.ioc.library.container.ContainerApplication;

public class Application {
    public static void main(String[] args) {
        Container container = ContainerApplication.start(Application.class); // Does not support two beans of the same type!
        UserService userService = container.getBean(UserService.class);

        System.out.println(userService.getUserCsv(new User("lotion", "dude", 21)));
    }
}