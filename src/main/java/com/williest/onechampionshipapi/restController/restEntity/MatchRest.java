package com.williest.onechampionshipapi.restController.restEntity;

import com.williest.onechampionshipapi.model.enumeration.MatchStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class MatchRest {
    private UUID id;
    private MatchClubRest clubPlayingHome;
    private MatchClubRest clubPlayingAway;
    private String stadium;
    private LocalDateTime matchDateTime;
    private MatchStatus actualStatus;
}
