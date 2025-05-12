package com.williest.onechampionshipapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Scorer {
    private Player player;
    private int minuteOfGoal;
    private Boolean ownGoal;
}
