package com.looker.events.lookml;

import com.github.cworsley4.dispatcher.AbstractEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;

/**
 *
 * @author cecil
 */
public class LookMlEvent extends AbstractEvent {

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
        return "lookml";
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
