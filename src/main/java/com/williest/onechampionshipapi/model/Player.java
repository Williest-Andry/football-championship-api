package com.williest.onechampionshipapi.model;

import com.williest.onechampionshipapi.model.enumeration.PlayerPosition;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Player {
    private final UUID id = UUID.randomUUID();
    private String name;
    private int number;
    private String nationality;
    private LocalDate birthday;
    private PlayerPosition playerPosition;

    public int getAge(){
        return LocalDate.now().getYear() - birthday.getYear();
    }
}