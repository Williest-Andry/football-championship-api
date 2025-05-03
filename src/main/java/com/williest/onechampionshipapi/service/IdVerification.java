package com.williest.onechampionshipapi.service;

import java.util.UUID;

public class IdVerification {
    public static UUID validUUID(String entityId){
        UUID uuid;
        try{
            uuid = UUID.fromString(entityId);
        } catch (IllegalArgumentException e){
            uuid = null;
        }
        return uuid;
    }
}
