package com.looker;

import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author cecil
 */
public final class Message {
    public String event;
    public String scope;
    public Object payload;
    
    public Message(String raw) {
        JSONObject jsonObj = this.fromJson(raw);
        this.event = (String) jsonObj.get("event");
        this.scope = (String) jsonObj.get("scope");
        this.payload = jsonObj.get("data");
        
        System.out.println(this.payload.toString());
    }
    
    public Message(String event, String scope, Object payload) {
        this.event = event;
        this.scope = scope;
        this.payload = payload;
    }
    
    public JSONObject fromJson(String rawJson) {
        JSONTokener jsonToken = new JSONTokener(rawJson);
        return new JSONObject(jsonToken);
    }
    
    public String toJson(JSONObject obj) {
        return obj.toString();
    }
}
