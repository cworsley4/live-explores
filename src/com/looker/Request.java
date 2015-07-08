package com.looker;

import com.looker.dispatcher.Dispatcher;
import com.looker.events.LookMlSaveEvent;
import javax.websocket.OnOpen;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/live")
public class Request {
    
    Dispatcher d;
    SessionStore ss;
    
    public Request() {
        this.d = new Dispatcher();
        this.ss = SessionStore.getInstance();
        System.out.println("New connection");
    }
    
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("User with ID of " + session.getId() + " joined.");
        this.d.register("lookml:changed", LookMlSaveEvent.class);
        Room room = this.ss.setRoom(Room.getRoomId(session), session);
        System.out.println(room.sessions.toString());
    }

    @OnMessage
    public void onMessage(String event, Session session) {
        System.out.println("Publishing " + event);
        String roomId = Room.getRoomId(session);
        System.out.println("Getting sessions");
        Session[] sessions = this.ss.getSessionsByRoomId(roomId);
        
        this.d.publish(event, sessions, "");
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("User with ID of " + session.getId() + " left.");
        this.ss.removeSessionFromRoomById(Room.getRoomId(session), session.getId());
    }

}
