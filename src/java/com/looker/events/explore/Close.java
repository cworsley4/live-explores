package com.looker.events.explore;

/**
 *
 * @author Cecil <cecilworsley4@gmail.com>
 */
public class Close extends ExploreEvent {
    
    @Override
    public String getEvent() {
        return "close";
    }
    
}
