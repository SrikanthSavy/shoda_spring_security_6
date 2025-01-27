package com.easybytes.config;

import com.easybytes.exceptionhandling.CustomAccessDeniedHandler;
import com.easybytes.exceptionhandling.CustomBasicAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("!prod")
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        //http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
        //http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());
        //http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll());
        http.sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.invalidSessionUrl("/invalidSession").maximumSessions(1).maxSessionsPreventsLogin(true).expiredUrl("/expiredSessionUrl")); // we will move control to an MVC path to handle invalid sessions
        http.requiresChannel(requestContextFilterObj->requestContextFilterObj.anyRequest().requiresInsecure());
        http.csrf(csrfConfigurer->csrfConfigurer.disable()).authorizeHttpRequests((requests) -> requests.requestMatchers("/myAccount","/myBalance","/myLoans","/myCards").authenticated()
                .requestMatchers("/contact","/notices","/error","/register","/invalidSession","/expiredSessionUrl").permitAll());

        http.formLogin(withDefaults());
        //http.formLogin((formLoginConfigurer) -> formLoginConfigurer.disable());
        //http.httpBasic(withDefaults());
        // Defining custom authentication entry point
        http.httpBasic(hbc-> hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
        //To set CustomAuthenticationEntryPoint at Global level for all 401 errors
        //http.exceptionHandling(ehc->ehc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));

        //It is required to set AccessDeniedHandler at Global level
        http.exceptionHandling(ehc->ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));
        // The above accessDeniedHandler() -> support for REST base response
        //You need to use another method() to handle UI -base error for you frontend app
        //Somthing like this
        ////////http.exceptionHandling(ehc->ehc.accessDeniedHandler(new CustomAccessDeniedHandler()).accessDeniedPage("/deniedPagePath"));

       // http.httpBasic(httpBasicConfigurer->httpBasicConfigurer.disable());
        return http.build();
    }

    /*@Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {

        return new JdbcUserDetailsManager(dataSource);

    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }
}
