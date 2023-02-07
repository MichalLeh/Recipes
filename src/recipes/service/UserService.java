package recipes.service;

import org.springframework.stereotype.Service;
import recipes.model.User;
import recipes.persistence.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public void add(User user) {
        userRepository.save(user);
    }
    public Boolean existsByEmailIgnoreCase(String email){
        return userRepository.existsByEmailIgnoreCase(email);
    }
    public User findByEmailIgnoreCase(String email){
        return userRepository.findByEmailIgnoreCase(email);
    }
}
