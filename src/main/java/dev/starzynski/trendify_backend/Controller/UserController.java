package dev.starzynski.trendify_backend.Controller;

import dev.starzynski.trendify_backend.Model.User;
import dev.starzynski.trendify_backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/public/user/create")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
}
