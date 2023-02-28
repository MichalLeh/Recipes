package recipes.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import recipes.model.User;
import recipes.persistence.UserRepository;
/**
 * This class implements the UserDetailsService interface and is used by Spring Security.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmailIgnoreCase(username);

        if(user==null){
            throw new UsernameNotFoundException("username/email: " + username);
        }
        return new UserDetailsImpl(user);
    }
}
