package pl.irc.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log
public class UsersService {

    private final Map<String, String> sessionIdToUser = new HashMap<>();

    public void saveUserInfo(String jsessionId, String username) {
        log.info("Assigning sessionId " + jsessionId + " to user " + username);
        sessionIdToUser.put(jsessionId, username);
    }

    public String getUserInfo(String jsessionId) {
        log.info("Searching for user with sessionId = " + jsessionId);
        return sessionIdToUser.get(jsessionId);
    }
}
