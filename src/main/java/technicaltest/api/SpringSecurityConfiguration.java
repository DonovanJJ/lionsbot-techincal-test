package technicaltest.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import technicaltest.api.jwt.JwtAuthenticationFilter;
import technicaltest.api.role.Role;

@Configuration
public class SpringSecurityConfiguration {
    @Autowired
    CustomUserDetailsService userDetailsService;
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(this.userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/admin").hasAnyAuthority(Role.ADMIN, Role.CUSTOMER)
                .requestMatchers(HttpMethod.POST, "/customers").hasAnyAuthority(Role.ADMIN, Role.CUSTOMER)
                .requestMatchers(HttpMethod.GET, "/customers").hasAuthority(Role.ADMIN)
                .requestMatchers(HttpMethod.GET, "/orders/**").hasAnyAuthority(Role.ADMIN, Role.CUSTOMER)
                .requestMatchers(HttpMethod.PUT, "/orders/**").hasAnyAuthority(Role.ADMIN, Role.CUSTOMER)
                .requestMatchers(HttpMethod.GET, "/orders").hasAuthority(Role.ADMIN)
                .requestMatchers(HttpMethod.DELETE, "/orders/**").hasAuthority(Role.ADMIN)
                .requestMatchers(HttpMethod.DELETE, "/customers/**").hasAuthority(Role.ADMIN)
                .anyRequest().authenticated());
        http.authenticationProvider(authenticationProvider());
        http.sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        http.csrf().disable();
        http.cors().disable();

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // jwt filter
        return http.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }
}
