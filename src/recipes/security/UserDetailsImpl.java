package recipes.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import recipes.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/**
 * This class implements the UserDetails interface and is used by Spring Security.
 */
public class UserDetailsImpl implements UserDetails {
    private final String username;
    private final String password;
    private final List<GrantedAuthority> rolesAndAuthorities;
    public UserDetailsImpl(User user) {
        this.username = user.getEmail();
        this.password = user.getPassword();
        List<String> recipesAuthoredList= new ArrayList<>();
        this.rolesAndAuthorities = List.of(new SimpleGrantedAuthority(recipesAuthoredList.toString()));
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return rolesAndAuthorities;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return username;
    }
    // Remaining methods below are not used in this project and just return true
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
