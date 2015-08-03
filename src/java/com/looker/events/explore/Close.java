/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
