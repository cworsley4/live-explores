package com.looker;

import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author cecil
 */
public final class Message {
    public String id;
    public String event;
    public String scope;
    public Object payload;
    
    public Message(String raw) {
        JSONObject jsonObj = this.fromJson(raw);
        this.id = Long.toString((long) jsonObj.get("id"));
        this.event = (String) jsonObj.get("event");
        this.scope = (String) jsonObj.get("scope");
        this.payload = jsonObj.get("data");
        
        System.out.println(this.payload.toString());
    }
    
    public Message(String id, String event, String scope, Object payload) {
        this.id = event;
        this.event = event;
        this.scope = scope;
        this.payload = payload;
    }
    
    public JSONObject fromJson(String rawJson) {
        JSONTokener jsonToken = new JSONTokener(rawJson);
        return new JSONObject(jsonToken);
    }
    
    public String toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.id);
        obj.put("event", this.event);
        obj.put("scope", this.scope);
        obj.put("payload", this.payload);
        return obj.toString();
    }
    
    public String toJsonForAck() {
        // TODO: Document reserved event suffix "::ack"
        JSONObject obj = new JSONObject();
        obj.put("id", this.id);
        obj.put("event", this.event + "::ack");
        return obj.toString();
    }
}
