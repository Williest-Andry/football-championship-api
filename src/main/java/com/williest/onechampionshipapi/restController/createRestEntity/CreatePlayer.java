package com.williest.onechampionshipapi.restController.createRestEntity;

import com.williest.onechampionshipapi.model.enumeration.PlayerPosition;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class CreatePlayer {
    private final String id;
    private String name;
    private int number;
    private String nationality;
    private int age;
    private PlayerPosition playerPosition;

    public String getBirthYear(){
        return String.valueOf(LocalDate.now().getYear() - age);
    }

    public boolean isValid(){
        return id != null && name != null && !name.isEmpty() && name.length() > 1
                && number > 0 && nationality != null && age > 0 && playerPosition != null;
    }
}
