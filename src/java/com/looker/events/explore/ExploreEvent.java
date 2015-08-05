package com.looker.events.explore;

import com.github.cworsley4.dispatcher.AbstractEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;

/**
 *
 * @author Cecil <cecilworsley4@gmail.com>
 */
public class ExploreEvent extends AbstractEvent {
    
    @Override
    public String getFullyQualifiedEventTitle() {
        return this.getTopic() + ":" + this.getEvent();
    }
    
    @Override
    public String getEvent() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getTopic() {
        return "explore";
    }

    @Override
    public void execute(Session s) {
        try {
            System.out.println(super.payloadData.toString());
            super.emit(s, this.getFullyQualifiedEventTitle(), super.payloadData);
        } catch (IOException ex) {
            Logger.getLogger(ExploreEvent.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
}
