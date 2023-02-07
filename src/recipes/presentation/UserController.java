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
//    private UserRepository repo;
    private final PasswordEncoder encoder;
    public UserController(UserService userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }
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
