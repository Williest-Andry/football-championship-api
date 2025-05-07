package com.williest.onechampionshipapi.restController.restEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class PlayerScorerRest {
    private UUID id;
    private String name;
    private int number;
}
