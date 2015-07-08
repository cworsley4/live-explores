/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    
    public static String getRoomId(Session session) {
        return session.getRequestParameterMap().get("userId").toString();
    }
    
}
