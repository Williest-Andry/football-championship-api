package com.williest.onechampionshipapi.restController.restEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.williest.onechampionshipapi.model.enumeration.PlayerPosition;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class PlayerRest {
    private final UUID id;
    private String name;
    private int number;
    private String nationality;
    @JsonIgnore
    private LocalDate birthday;
    private PlayerPosition playerPosition;
    private ClubRest club;

    public int getAge(){
        return LocalDate.now().getYear() - birthday.getYear();
    }
}
