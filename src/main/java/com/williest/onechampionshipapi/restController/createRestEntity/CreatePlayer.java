package com.williest.onechampionshipapi.restController.createRestEntity;

import com.williest.onechampionshipapi.model.enumeration.PlayerPosition;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class CreatePlayer {
    private final UUID id;
    private String name;
    private int number;
    private String nationality;
    private int age;
    private PlayerPosition playerPosition;

    public String getBirthYear(){
        return String.valueOf(LocalDate.now().getYear() - age);
    }
}
