package com.easybytes.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationEvents {

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent successEvent) {
        log.info("Login Sucessfull for User : {}", successEvent.getAuthentication().getName());
    }

    @EventListener
    public void onFailures(AbstractAuthenticationFailureEvent failureEvent) {
        log.error("Login Failed for User :{} - due to :{}", failureEvent.getAuthentication().getName(),
                failureEvent.getException().getMessage());
    }
}
