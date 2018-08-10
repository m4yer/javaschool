package com.tsystems.websocket;

import javax.ejb.Stateless;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/example")
@Stateless
public class ClientEndpoint {

    private static HashMap<String, Session> sessions = new HashMap<>();

    @OnMessage
    public void receiveMessage(String message, Session session) {
        // This method receives messages from frontend.
        // And can "return" message back if return type is String.
        sessions.put(message, session);
        System.out.println("Added to HashMap<String, Session>:");
        System.out.println("Message [means Station]: " + message);
        System.out.println("Session: " + session);
        System.out.println("HashMap<> size: " + sessions.size());
    }

    public void sendAll(String message) throws IOException {

    }

    @OnOpen
    public void open(Session session) {
        // TODO: Log clients.size pls
        System.out.println("Session was opened!");
    }

    @OnClose
    public void close(Session session, CloseReason c) {
        System.out.println("Java: Session was closed!");
        sessions.values().remove(session);
        System.out.println("Delete session from HashMap<>");
        System.out.println("HashMap<> size: " + sessions.size());
    }

}
