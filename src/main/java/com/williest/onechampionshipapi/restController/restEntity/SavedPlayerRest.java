package com.williest.onechampionshipapi.restController.restEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.williest.onechampionshipapi.model.enumeration.PlayerPosition;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class SavedPlayerRest {
    private final UUID id;
    private String name;
    private int number;
    private String nationality;
    @JsonIgnore
    private String birth_year;
    private PlayerPosition playerPosition;

    public int getAge(){
        return LocalDate.now().getYear() - Integer.parseInt(birth_year);
    }
}
