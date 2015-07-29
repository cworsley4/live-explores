package com.looker;

import com.looker.dispatcher.Dispatcher;
import com.looker.events.LookMlSaveEvent;
import java.io.IOException;

import javax.websocket.OnOpen;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/live")
public class Request {
    
    Dispatcher d;
    
    public Request() {
        this.d = new Dispatcher();
        System.out.println(RoomManager.getInstance());
    }
    
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("User with ID of " + session.getId() + " joined.");
        this.d.register("lookml:changed", LookMlSaveEvent.class);
        Room room = RoomManager.getInstance().setRoom(Room.getRoomId(session), session);
    }

    @OnMessage
    public void onMessage(String rawPayload, Session session) throws IOException {
        Session[] sessions;
        String roomId = Room.getRoomId(session);
        String sessionId = session.getId();
        
        Message msg = new Message(rawPayload);
        
        switch(msg.scope) {
            case "roommates":
                sessions = RoomManager.getInstance().getAllOtherSessionsByRoomId(roomId, sessionId);
                break;
            default:
                sessions = RoomManager.getInstance().getSessionsByRoomId(roomId);
                break;
        }
        
        this.d.broadcast(msg.event, sessions, "");
    }

    @OnClose
    public void onClose(Session session) {
        RoomManager.getInstance().removeSessionFromRoomById(Room.getRoomId(session), session.getId());
    }

}
