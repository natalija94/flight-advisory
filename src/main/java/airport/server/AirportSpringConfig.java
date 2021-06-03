package airport.server;

import airport.server.service.impl.MyUserDetailsService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author natalija
 */
@Configuration
@ComponentScan("airport.server")
@EnableWebSecurity
public class AirportSpringConfig extends WebSecurityConfigurerAdapter {

    private MyUserDetailsService userDetailsService;

    public AirportSpringConfig(MyUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return true;
            }
        });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/administrator", "/administrator/**").permitAll()
                .antMatchers("/reviews").authenticated()
                .antMatchers("/").permitAll()
                .and().formLogin().permitAll();
        //it wasn't possible to handle post request without these lines; swagger always returned: 403
        http.csrf().disable();

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs/**", "/swagger-ui.html", "/swagger-ui/**");
    }
}
