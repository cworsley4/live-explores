package system.events;

import com.github.cworsley4.dispatcher.AbstractEvent;
import javax.websocket.Session;

/**
 *
 * @author cecil
 */
public class SystemEvent extends AbstractEvent {

    @Override
    public String getEvent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String getFullyQualifiedEventTitle() {
        return this.getTopic() + ":" + this.getEvent();
    }

    @Override
    public String getTopic() {
        return "system";
    }
    
    @Override
    public void execute(Session s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
