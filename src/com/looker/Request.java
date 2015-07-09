package com.looker;

import com.looker.dispatcher.Dispatcher;
import com.looker.events.LookMlSaveEvent;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONTokener;
import org.json.JSONObject;

import javax.websocket.OnOpen;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/live")
public class Request {
    
    Dispatcher d;
    
    public Request() {
        this.d = new Dispatcher();
        System.out.println(RoomManager.getInstance());
    }
    
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("User with ID of " + session.getId() + " joined.");
        this.d.register("lookml:changed", LookMlSaveEvent.class);
        Room room = RoomManager.getInstance().setRoom(Room.getRoomId(session), session);
        try {
            session.getBasicRemote().sendText("You have an ID of " + session.getId());
            session.getBasicRemote().sendText("Using manager with ID of " + RoomManager.getInstance().toString());
            session.getBasicRemote().sendText("Number of people in your room" + room.toString());
        } catch (IOException ex) {
            Logger.getLogger(Request.class.getName()).log(Level.INFO, null, ex);
        }
    }

    @OnMessage
    public void onMessage(String rawPayload, Session session) throws IOException {
        Session[] sessions;
        String roomId = Room.getRoomId(session);
        String sessionId = session.getId();
        
        JSONTokener jsonToken = new JSONTokener(rawPayload);
        JSONObject jsonObject = new JSONObject(jsonToken);
        
        String event = (String) jsonObject.get("event");
        String scope = (String) jsonObject.get("broadcastScope");
        // Object data = jsonObject.get("data");
        
        sessions = RoomManager.getInstance().getSessionsByRoomId(roomId);
        
        Map<String, Room> rM = RoomManager.getInstance().getRoomMap();
        
        session.getBasicRemote().sendText("Your room ID is " + roomId);
        session.getBasicRemote().sendText(Integer.toString(sessions.length) + " with ID of " + sessions[0].getId());
        session.getBasicRemote().sendText("People in your room");
        
        switch(scope) {
            case "roommates":
                session.getBasicRemote().sendText("Using manager with ID of " + RoomManager.getInstance().toString());
                sessions = RoomManager.getInstance().getAllOtherSessionsByRoomId(roomId, sessionId);
                break;
            default:
                sessions = RoomManager.getInstance().getSessionsByRoomId(roomId);
                break;
        }
        
        System.out.print("About to broadcast to people event: " + event + " -> ");
        System.out.println(sessions.length);
        
        this.d.broadcast(event, sessions, "");
    }

    @OnClose
    public void onClose(Session session) {
//        System.out.println("User with ID of " + session.getId() + " left.");
        RoomManager.getInstance().removeSessionFromRoomById(Room.getRoomId(session), session.getId());
//        System.out.println("We have removed a user successfully: " + session.getId());
//        System.out.println(RoomManager.getInstance().getRoom(Room.getRoomId(session)).sessions.size());
    }

}
