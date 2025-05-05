package com.williest.onechampionshipapi.service;

import java.util.UUID;

public class IdVerification {
    static UUID uuid;

    public static UUID validUUID(String entityId) {
        try {
            uuid = UUID.fromString(entityId);
        } catch (IllegalArgumentException e) {
            uuid = null;
        }
        return uuid;
    }

    public static UUID validOrGenerateUUID(String entityId) {
        try {
            uuid = UUID.fromString(entityId);
        } catch (IllegalArgumentException e) {
            uuid = UUID.randomUUID();
        }
        return uuid;
    }
}
