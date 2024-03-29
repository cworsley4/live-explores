package com.looker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.websocket.Session;

/**
 *
 * @author cecil
 */
public class RoomManager {
    
    private Map<String, Room> roomMap = new HashMap<>();
    private static RoomManager ss = new RoomManager();
    
    private RoomManager() {
        
    }
    
    public static RoomManager getInstance() {
        if(ss == null) {
            System.out.println("Creating new Manager");
            ss = new RoomManager();
        }
        
        return ss;
    }
    
    public Room setRoom(String roomId, Session session) {
        Room r = this.roomMap.get(roomId);
        
        if(r == null) {
            System.out.println(this.roomMap.keySet().toString());
            System.out.println("Setting up new Room");
            r = new Room(roomId, session);
        } else {
            r = r.addToRoom(session);
        }
        
        this.roomMap.put(roomId, r);
        
        return r;
    }
    
    public Room getRoom(String roomId) {
        return this.roomMap.get(roomId);
    }
    
    public boolean removeSessionFromRoomById(String roomId, String sessionId) {
        Room room = this.roomMap.get(roomId);
        for(int i = 0; i < room.sessions.size(); i++) {
            Session session = room.sessions.get(i);
            if(session.getId().equals(sessionId)) {
                room.sessions.remove(i);
                if(room.sessions.size() < 1) {
                    this.roomMap.remove(roomId);
                }
                return true;
            }
        }
        
        return false;
    }
    
    public Session[] getAllOtherSessionsByRoomId(String roomId, String sessionId) {
        Session[] sessions = this.getSessionsByRoomId(roomId);
        ArrayList<Session> applicableSessions = new ArrayList<>();     
        for (Session session : sessions) {
            if (!session.getId().equals(sessionId)) {
                applicableSessions.add(session);
            }
        }
        
        return applicableSessions.toArray(new Session[applicableSessions.size()]);
    }
    
    public Session[] getSessionsByRoomId(String roomId) {
        Integer sessionSize = this.roomMap.get(roomId).sessions.size();
        return this.roomMap.get(roomId).sessions.toArray(new Session[sessionSize]); 
    }
    
    public Session[] getJustMe(Session s) {
        Session[] sessions = new Session[1];
        sessions[0] = s;
        return sessions;
    }
    
    public Map<String, Room> getRoomMap() {
        return this.roomMap;
    }
    
}
