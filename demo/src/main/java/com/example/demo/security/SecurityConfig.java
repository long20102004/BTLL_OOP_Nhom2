package com.example.demo.security;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private JwtTokenFilter jwtTokenFilter;
    private UserService userService;
    private JwtToUserConvertor convertor;
    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    public SecurityConfig(JwtTokenFilter jwtTokenFilter, UserService userService, JwtToUserConvertor convertor) {
        this.jwtTokenFilter = jwtTokenFilter;
        this.userService = userService;
        this.convertor = convertor;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.exceptionHandling(exceptionHandling -> {
            exceptionHandling.authenticationEntryPoint((request, response, authException) -> {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
            });
        });
//        httpSecurity.authorizeHttpRequests(request ->
//                        request.requestMatchers("/", "oauth2/**", "/login/**", "/register", "/courses/**", "/valid", "/course/**", "/lessons/**").permitAll()
//                        .requestMatchers("/edit/**").hasRole("ADMIN")
//                        .anyRequest().authenticated()).
//                sessionManagement(sessionManagement ->
//                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authenticationProvider(authenticationProvider())
//                .addFilterBefore(
//                        jwtTokenFilter, UsernamePasswordAuthenticationFilter.class
//                ).oauth2Login((oauth2Login) -> {
//                    oauth2Login.successHandler(successHandler())
//                            .failureUrl("/fail")
//                    ;
//                }).cors(Customizer.withDefaults());
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new SimpleUrlAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
                String id = oauth2User.getAttribute("id");
                if (id == null) id = oauth2User.getAttribute("email");
                User user = (User) userService.loadUserByUsername(id);
                if (user == null) {
                    user = new User();
                    user.setUsername(id);
                    userService.saveUser(user);
                }
                String token = jwtUtility.generateToken(user);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"token\":\"" + token + "\"}");
                getRedirectStrategy().sendRedirect(request, response, "http://localhost:3000/login?token=" + token);
            }

            @Override
            protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                // Do nothing to prevent redirect
            }
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}