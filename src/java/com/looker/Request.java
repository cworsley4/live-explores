package com.looker;

import system.events.PingEvent;
import com.github.cworsley4.dispatcher.Dispatcher;
import com.looker.events.explore.QueryChangeEvent;
import com.looker.events.lookml.SaveEvent;
import com.looker.events.lookml.GoToEvent;
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
    }
    
    @OnOpen
    public void onOpen(Session session) {
        // System events
        this.d.register("system::ping", PingEvent.class);
        
        // Custom events
        this.d.register("lookml:goto", GoToEvent.class);
        this.d.register("lookml:changed", SaveEvent.class);
        this.d.register("query:changed", QueryChangeEvent.class);
        
        RoomManager.getInstance().setRoom(Room.getRoomId(session), session);
    }

    @OnMessage
    public void onMessage(String rawPayload, Session session) throws IOException {
        // TODO: Acknowledge message reciept
        Session[] sessions;
        String roomId = Room.getRoomId(session);
        String sessionId = session.getId();
        
        Message msg = new Message(rawPayload);
        
        System.out.println("Processing event: " + msg.event);
        System.out.println("Payload: " + rawPayload);
        
        switch(msg.scope) {
            case "roommates":
                sessions = RoomManager.getInstance().getAllOtherSessionsByRoomId(roomId, sessionId);
                break;
            case "mirror":
                sessions = RoomManager.getInstance().getJustMe(session);
                break;
            default:
                sessions = RoomManager.getInstance().getSessionsByRoomId(roomId);
                break;
        }
        
        // Broadcast and acknowledge
        try {
            this.d.broadcast(msg.event, sessions, msg.payload);
        } catch (Exception e) {
            System.out.println("Could not broadcast event: " + msg.event);
            System.out.println(e.fillInStackTrace());
        }
        
        System.out.println("Acknowledging event via async");
        session.getAsyncRemote().sendText(msg.toJsonForAck());
    }

    @OnClose
    public void onClose(Session session) {
        RoomManager.getInstance().removeSessionFromRoomById(Room.getRoomId(session), session.getId());
    }

}
