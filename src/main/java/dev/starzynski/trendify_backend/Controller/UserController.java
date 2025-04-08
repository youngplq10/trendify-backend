package dev.starzynski.trendify_backend.Controller;

import dev.starzynski.trendify_backend.Model.User;
import dev.starzynski.trendify_backend.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
@Tag(name = "Users", description = "User managment API")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/public/user/create")
    @Operation(summary = "Create user", description = "Returns JWT")
    public ResponseEntity<?> createUser(@RequestPart String username, @RequestPart String email, @RequestPart String password, @RequestPart(value = "profilePicture", required = false) String profilePicture) {
        return userService.createUser(username, email, password, profilePicture);
    }

    @Operation(summary = "Login user", description = "Returns JWT")
    @PostMapping("/public/user/login")
    public ResponseEntity<?> loginUser(@RequestPart String username, @RequestPart String password) {
        return userService.loginUser(username, password);
    }

    @PostMapping("/auth/user/follow/{username}")
    @Operation(summary = "Follow user", description = "Returns message")
    public ResponseEntity<?> followUser(@RequestHeader("Authorization") String authHeader, @PathVariable String username) {
        String jwt = authHeader.replace("Bearer ", "");
        return userService.followUser(jwt, username);
    }

    @PostMapping("/auth/user/unfollow/{username}")
    @Operation(summary = "Unfollow user", description = "Returns message")
    public ResponseEntity<?> unfollowUser(@RequestHeader("Authorization") String authHeader, @PathVariable String username) {
        String jwt = authHeader.replace("Bearer ", "");
        return userService.unfollowUser(jwt, username);
    }

    @GetMapping("/auth/user")
    @Operation(summary = "Get user data by jwt", description = "Returns user object")
    public ResponseEntity<?> getUserData(@RequestHeader("Authorization") String authHeader) {
        String jwt = authHeader.replace("Bearer ", "");
        return userService.getUserData(jwt);
    }

    @GetMapping("/public/user/{username}")
    @Operation(summary = "Get user data by username", description = "Returns user object")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }
}
