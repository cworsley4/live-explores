/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.looker;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import javax.websocket.Session;

/**
 *
 * @author cecil
 */
public class SessionStore {
    
    private static SessionStore ss = null;
    private static Map<String, Room> roomMap = new HashMap<>();
    
    private SessionStore() {
        
    }
    
    public static SessionStore getInstance() {
        if(ss == null) {
            ss = new SessionStore();
        }
        
        return ss;
    }
    
    public Room setRoom(String roomId, Session session) {
        Room r = null;
        r = roomMap.get(roomId);
        
        if(r == null) {
            System.out.println("Setting up new Room");
            r = new Room(roomId, session);
            System.out.println("Hey there");
        }
        
        roomMap.put(roomId, r);
        
        return r;
    }
    
    public Room getRoom(String roomId) {
        return roomMap.get(roomId);
    }
    
    public boolean removeSessionFromRoomById(String roomId, String sessionId) {
        Room room = roomMap.get(roomId);
        for(Integer i = 0; i < room.sessions.size(); i++) {
            if(room.sessions.get(i).getId() == sessionId) {
                return room.sessions.remove(i);
            }
        }
        
        return false;
    }
    
    public Session[] getSessionsByRoomId(String roomId) {
        Integer sessionSize =  roomMap.get(roomId).sessions.size();
        return roomMap.get(roomId).sessions.toArray(new Session[sessionSize]); 
    }
    
}
