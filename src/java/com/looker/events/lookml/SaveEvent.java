/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
