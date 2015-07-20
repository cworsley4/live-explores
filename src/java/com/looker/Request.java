package com.looker;

import com.github.cworsley4.dispatcher.Dispatcher;
import com.looker.events.explore.QueryChangeEvent;
import com.looker.events.lookml.SaveEvent;
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
