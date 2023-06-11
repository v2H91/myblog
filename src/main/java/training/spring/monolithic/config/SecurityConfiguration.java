package training.spring.monolithic.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    public final JwtRequestFilter jwtRequestFilter;

    private final JwtAuthenticationEntryPoint authEntryPointJwt;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        auth.eraseCredentials(false);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable().authorizeRequests()
                .antMatchers("/auth/authenticate", "/user/register").permitAll()
                .antMatchers(HttpMethod.GET, "/users/**", "/user/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/users/**", "/user/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/users/**", "/user/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/users/**", "/user/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().logout().logoutUrl("/auth/logout")
                .and().exceptionHandling().authenticationEntryPoint(authEntryPointJwt)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
