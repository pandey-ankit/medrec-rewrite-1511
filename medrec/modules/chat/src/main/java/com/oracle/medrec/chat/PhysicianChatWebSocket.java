package com.oracle.medrec.chat;

import jakarta.inject.Inject;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

/**
 * Web socket server end point of physician of MedRec chat room based on
 * {@link ChatService}.
 *
 * @author Xiaojun Wu. <br>
 *         Copyright (c) 2007, 2019, Oracle and/or its
 *         affiliates. All rights reserved.
 */
@ServerEndpoint("/physician/{id}/{name}")
public class PhysicianChatWebSocket {

    @Inject
    private ChatService service;

    @OnOpen
    public void onOpen(Session session, EndpointConfig conf,
                       @PathParam("id") String id, @PathParam("name") String name) {
        session.getUserProperties().put("id", id);
        session.getUserProperties().put("name", name);
        service.physicianLogin(session, id, name);
        session.setMaxIdleTimeout(600000);
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        if (message == null || message.equals("")) {
            return;
        }
        String id = (String) session.getUserProperties().get("id");
        String name = (String) session.getUserProperties().get("name");
        service.physicianChat(id, name, message);
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        service.physicianLogout(session);
    }

    @OnError
    public void error(Session session, Throwable t) {
        t.printStackTrace();
    }

}
