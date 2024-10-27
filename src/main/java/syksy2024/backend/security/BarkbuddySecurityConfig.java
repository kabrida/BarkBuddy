package syksy2024.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;
import syksy2024.backend.web.UserDetailServiceImpl;


@Configuration
@EnableMethodSecurity(securedEnabled = true)
@EnableWebSecurity
public class BarkbuddySecurityConfig  {


    @Autowired
    private UserDetailServiceImpl userDetailsService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
			.authorizeHttpRequests(authorize -> authorize
            .requestMatchers(antMatcher("/")).permitAll()
            .requestMatchers(antMatcher("/css/**")).permitAll()
            .requestMatchers(antMatcher("/signup")).permitAll()
            .requestMatchers(antMatcher("/saveowner")).permitAll()
            .requestMatchers(antMatcher("/breeds")).permitAll()
            .requestMatchers(antMatcher("/dogs")).permitAll()
				.anyRequest().authenticated()
			)
            .headers(headers -> headers
            .frameOptions(frameoptions -> 
            frameoptions.disable() //for h2 console			
            )
            )           
            .formLogin(formlogin -> formlogin
				.defaultSuccessUrl("/home", true).permitAll()
			)
            .logout(logout -> logout
            .logoutSuccessUrl("/index")
            .permitAll()
        );
		return http.build();
	}

    	@Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		    auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

}
