package academy.devdojo.springbootessentialsrr.config;

import academy.devdojo.springbootessentialsrr.service.DevRodUserDetailsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Log4j2
@EnableMethodSecurity
public class SecurityConfig {

    private final DevRodUserDetailsService devRodUserDetailsService;

    public SecurityConfig(DevRodUserDetailsService devRodUserDetailsService) {
        this.devRodUserDetailsService = devRodUserDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        UserDetails user = User.withUsername("user")
//                .password(encoder.encode("user"))
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(encoder.encode("admin"))
//                .roles("USER", "ADMIN")
//                .build();
//        // log.info("Password encoded: {}", encoder.encode("user"));
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        log.info("Password Encoded {}", encoder.encode("user1"));

        auth.inMemoryAuthentication().withUser("user")
                .password(encoder.encode("user"))
                //.roles("USER", "ADMIN");
                        .authorities("ROLE_USER");

        auth.userDetailsService(devRodUserDetailsService).passwordEncoder(encoder);
    }


//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
