package com.looker.events.explore;

import com.github.cworsley4.dispatcher.AbstractEvent;
import javax.websocket.Session;

/**
 *
 * @author cecil
 */
public class QueryChangeEvent extends AbstractEvent {

    @Override
    public String getEvent() {
        return "changed";
    }

    @Override
    public String getTopic() {
        return "query";
    }

    @Override
    public void execute(Session s) {
        
    }
    
}
