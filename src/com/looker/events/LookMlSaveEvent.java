/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.looker.events;

import com.looker.dispatcher.AbstractEvent;
import java.io.IOException;
import java.util.logging.Level;
import javax.websocket.Session;
import java.util.logging.Logger;

/**
 *
 * @author cecil
 */
public class LookMlSaveEvent extends AbstractEvent {
    
    public LookMlSaveEvent() {
        
    }
    
    @Override
    public String getFullyQualifiedEventTitle() {
        return this.getTopic() + ":" + this.getEvent();
    }
    
    @Override
    public String getEvent() {
        return "save";
    }

    @Override
    public String getTopic() {
        return "lookml";
    }
    
    @Override
    public void run() {
        
        System.out.println(super.roomSessions.length);
        for(Session s : super.roomSessions) {
            try {
               s.getBasicRemote().sendText("Hiya from server");
               s.getBasicRemote().sendText("Hiya from server");
               s.getBasicRemote().sendText("Hiya from server");
               s.getBasicRemote().sendText("Hiya from server");
            } catch (IOException ex) {
               Logger.getLogger(LookMlSaveEvent.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }
    }
        
}
