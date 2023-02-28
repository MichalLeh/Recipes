package recipes.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    
    public WebSecurity(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    /**
    * Configure the {@link AuthenticationManagerBuilder} to specify which UserDetailsService and
    * {@link PasswordEncoder} to use.
    *
    * @param auth The {@link AuthenticationManagerBuilder} to use
    * @throws Exception If an error occurs
    */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(getEncoder());
    }
   /**
    * Configure the security of the web application by restricting access based on the HttpServletRequest.
    *
    * @param http the {@link HttpSecurity} to modify
    * @throws Exception if an error occurs
    */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //provided for testing purposes only
                .mvcMatchers(HttpMethod.POST, "/actuator/**").permitAll()
                //anyone can access the database log in page
                .mvcMatchers("/h2/**").permitAll()
                //anyone can register
                .mvcMatchers("/api/register").permitAll()
                //to add or edit a recipe you need authentication
                .mvcMatchers("/api/recipe/**").authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf().disable()
                .headers().frameOptions().disable();
    }
    /**
     * Encrypt the password of the user using the BCryptPasswordEncoder.
     *
     * @return the {@link PasswordEncoder} to use
     */
    @Bean
    PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
