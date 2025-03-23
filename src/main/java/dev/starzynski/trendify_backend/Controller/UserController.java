package dev.starzynski.trendify_backend.Controller;

import dev.starzynski.trendify_backend.Model.User;
import dev.starzynski.trendify_backend.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
@Tag(name = "Users", description = "User managment API")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/public/user/create")
    @Operation(summary = "Create user", description = "Returns JWT")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @Operation(summary = "Login user", description = "Returns JWT")
    @PostMapping("/public/user/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        return userService.loginUser(user);
    }
}
