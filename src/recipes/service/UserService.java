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
    /**
     * Registration of a new user.
     *
     * @param user New user to be registered
     */
    public void add(User user) {
        userRepository.save(user);
    }
    /**
     * Check an existence of the email in database
     *
     * @param email Email to be checked
     * @return True if the email already exist, false otherwise
     */
    public Boolean existsByEmailIgnoreCase(String email){
        return userRepository.existsByEmailIgnoreCase(email);
    }
    /**
     * Find user by the email
     *
     * @param email The email to be used to retrieve the user
     * @return Return the user
     */
    public User findByEmailIgnoreCase(String email){
        return userRepository.findByEmailIgnoreCase(email);
    }
}
