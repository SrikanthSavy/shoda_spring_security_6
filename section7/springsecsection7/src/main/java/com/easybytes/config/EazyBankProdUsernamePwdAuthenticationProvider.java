package com.easybytes.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
@RequiredArgsConstructor
public class EazyBankProdUsernamePwdAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService; // To fetch User Details from DB using our custom UserDetailsService: EasyBankUserDetailsService
    private  final PasswordEncoder passwordEncoder;      // To check the Password entered vs above User's Password from Db

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // Fetch User Details from DB
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Check the Password entered vs above User's Password from Db
        if(passwordEncoder.matches(password,userDetails.getPassword()))
        {
            return new UsernamePasswordAuthenticationToken(username,password,userDetails.getAuthorities());
        }else {
            throw new BadCredentialsException("Invalid Crendential");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
