package com.williest.onechampionshipapi.restController.createRestEntity;

import com.williest.onechampionshipapi.model.enumeration.SeasonStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateSeasonStatus {
    private SeasonStatus status;
}
