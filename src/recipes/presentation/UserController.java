package recipes.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recipes.model.User;
import recipes.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder encoder;
    public UserController(UserService userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }
    // Receives a JSON object with two fields: email (string), and password (string).
    // If a user with a specified email does not exist, the program saves (registers) the user in a database and responds with 200 (Ok).
    // If a user is already in the database, respond with the 400 (Bad Request) status code.
    // Both fields are required and must be valid: email should contain @ and . symbols, password should contain at least 8 characters and shouldn't be blank.
    // If the fields do not meet these restrictions, the service should respond with 400 (Bad Request).
    @PostMapping("/register")
    public ResponseEntity register (@Valid @RequestBody User user) {
        if (!userService.existsByEmailIgnoreCase(user.getEmail())){
            user.setPassword(encoder.encode(user.getPassword()));
            userService.add(user);
            return ResponseEntity.status(200).build();
        }
            return ResponseEntity.status(400).build();
    }
}
