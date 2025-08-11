package com.springsecurity.SecurityApp.SecurityApp.services;

import com.springsecurity.SecurityApp.SecurityApp.entities.Session;
import com.springsecurity.SecurityApp.SecurityApp.entities.User;
import com.springsecurity.SecurityApp.SecurityApp.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final int SESSION_LIMIT = 2;

    public void generateSession(User user, String refreshToken){

        List<Session> userSessions = sessionRepository.findByUser(user);

        if(userSessions.size() == SESSION_LIMIT){
            userSessions.sort(Comparator.comparing((session) -> session.getLastUsedAt()));
            Session leastRecentlyUsedSession = userSessions.getFirst();
            sessionRepository.delete(leastRecentlyUsedSession);
        }

        Session session = Session.builder()
                .user(user)
                .refreshToken(refreshToken)
                .build();
        sessionRepository.save(session);
    }

    public void validateSession(String refreshToken){
        Session session = sessionRepository.findByRefreshToken(refreshToken).orElseThrow(
                () -> new SessionAuthenticationException("Session not found for refresh token : " + refreshToken)
        );
        session.setLastUsedAt(LocalDateTime.now());
        sessionRepository.save(session);
    }
}
