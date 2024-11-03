package com.redhun.spring_security_demo.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity


public class SecurityConfig {

    @Autowired
  private   UserDetailsService userDetailsService;

    @Bean
    public   AuthenticationProvider authProvider() {

     DaoAuthenticationProvider provider= new DaoAuthenticationProvider();
     provider.setUserDetailsService(userDetailsService);
     provider.setPasswordEncoder(new BCryptPasswordEncoder());
     return provider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(customizer -> customizer.disable());
        http.authorizeHttpRequests(request -> request.anyRequest().authenticated());
//        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {

//        UserDetails user = User
//                .withDefaultPasswordEncoder()
//                .username("kichu").
//                password("a.b.c.def")
//                .roles("USER")
//                .build();
//
//        UserDetails user1 = User
//                .withDefaultPasswordEncoder()
//                .username("admin").
//                password("admin@123")
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user,user1);


//    }


}
