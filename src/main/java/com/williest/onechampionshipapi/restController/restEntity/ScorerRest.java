package com.williest.onechampionshipapi.restController.restEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScorerRest {
    private PlayerScorerRest player;
    private int minuteOfGoal;
    private boolean ownGoal;
}
