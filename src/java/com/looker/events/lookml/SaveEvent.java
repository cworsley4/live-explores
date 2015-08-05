package com.looker.events.lookml;

/**
 *
 * @author cecil
 */
public class SaveEvent extends LookMlEvent {
    
    public SaveEvent() {
        
    }
    
    @Override
    public String getEvent() {
        return "save";
    }
        
}
