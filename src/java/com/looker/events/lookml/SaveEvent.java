/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.looker.events.lookml;

import com.github.cworsley4.dispatcher.AbstractEvent;
import java.io.IOException;
import java.util.logging.Level;
import javax.websocket.Session;
import java.util.logging.Logger;

/**
 *
 * @author cecil
 */
public class SaveEvent extends AbstractEvent {
    
    public SaveEvent() {
        
    }
    
    @Override
    public String getFullyQualifiedEventTitle() {
        return this.getTopic() + ":" + this.getEvent();
    }
    
    @Override
    public String getTopic() {
        return "lookml";
    }
    
    @Override
    public String getEvent() {
        return "save";
    }
    
    @Override
    public void execute(Session s) {
        try {
            System.out.println(super.payloadData.toString());
            super.emit(s, this.getFullyQualifiedEventTitle(), super.payloadData);
        } catch (IOException ex) {
            Logger.getLogger(SaveEvent.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
        
}
