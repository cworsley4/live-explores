package com.looker;

import java.util.ArrayList;
import javax.websocket.Session;

/**
 *
 * @author cecil
 */
public class Room {
    
    public String roomId;
    public ArrayList<Session> sessions = new ArrayList<>();
        
    public Room(String roomId, Session session) {
        this.roomId = roomId;
        this.sessions.add(session);
    }
    
    public Room addToRoom(Session session) {
        this.sessions.add(session);
        return this;
    }
    
    public static String getRoomId(Session session) {
        return session.getRequestParameterMap().get("userId").toString();
    }
    
}
