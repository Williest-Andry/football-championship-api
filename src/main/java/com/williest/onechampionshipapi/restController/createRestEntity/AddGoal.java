package com.williest.onechampionshipapi.restController.createRestEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class AddGoal {
    private String clubId;
    private String scorerIdentifier;
    private int minuteOfGoal;
}
