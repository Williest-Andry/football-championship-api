package com.williest.onechampionshipapi.model;

import com.williest.onechampionshipapi.model.enumeration.PlayerPosition;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Player {
    private String id;
    private String name;
    private int number;
    private String nationality;
    private LocalDate birthday;
    private PlayerPosition playerPosition;
}
