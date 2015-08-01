/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.events;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;

/**
 *
 * @author cecil
 */
public class PingEvent extends SystemEvent {
    
    @Override
    public String getEvent() {
        return "ping";
    }
    
    public void execute(Session s) {
        System.out.println("Handling system::pong event");
        try {
            s.getBasicRemote().sendText("system::pong");
        } catch (IOException ex) {
            Logger.getLogger(PingEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
