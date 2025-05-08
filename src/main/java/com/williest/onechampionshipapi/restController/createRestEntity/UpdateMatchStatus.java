package com.williest.onechampionshipapi.restController.createRestEntity;

import com.williest.onechampionshipapi.model.enumeration.MatchStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateMatchStatus {
    private MatchStatus status;
}
