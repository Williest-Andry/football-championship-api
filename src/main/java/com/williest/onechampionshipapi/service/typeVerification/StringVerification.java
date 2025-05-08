package com.williest.onechampionshipapi.service.typeVerification;

import java.sql.Timestamp;

public class StringVerification {
    public static boolean isValidString(String string) {
        return string != null && !string.isEmpty();
    }

    public static boolean isValidDateString(String string) {
        if(!isValidString(string)){
            return false;
        }
        try{
            Timestamp.valueOf(string);
            return true;
        } catch(Exception e){
            return false;
        }
    }
}
