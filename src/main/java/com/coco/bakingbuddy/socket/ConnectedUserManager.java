package com.coco.bakingbuddy.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

@Slf4j
@Component
public class ConnectedUserManager {

    private final Set<String> connectedUsername = new ConcurrentSkipListSet<>();

    public void addUser(String username) {
        connectedUsername.add(username);
        log.info(">> ConnectedUserManager addUser {}", connectedUsername.size());

    }

    public void removeUser(String username) {
        connectedUsername.remove(username);
        log.info(">> ConnectedUserManager removeUser {}", connectedUsername.size());
    }

    public int getConnectedCount() {
        return connectedUsername.size();
    }

    public boolean isUserConnected(String username) {
        return connectedUsername.contains(username);
    }
}
