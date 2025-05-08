package com.williest.onechampionshipapi.model;

import com.williest.onechampionshipapi.model.enumeration.MatchStatus;
import com.williest.onechampionshipapi.service.exception.ClientException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Match {
    @Builder.Default
    private final UUID id = UUID.randomUUID();
    private MatchClub clubPlayingHome;
    private MatchClub clubPlayingAway;
    private String stadium;
    private LocalDateTime matchDateTime;
    @Setter(AccessLevel.NONE)
    private MatchStatus actualStatus;
    private Season season;
    private League league;
    private List<PlayerStatistics> playerStatistics;
    private List<ClubStatistics> clubStatistics;

    public void updateActualStatus(MatchStatus newStatus) {
        MatchStatus oldStatus = this.actualStatus;

        switch (oldStatus) {
            case NOT_STARTED -> {
                switch (newStatus) {
                    case STARTED -> this.actualStatus = MatchStatus.STARTED;
                    case null, default -> throw new ClientException("CAN'T UPDATE STATUS BECAUSE ACTUAL STATUS IS : " + this.actualStatus);
                }
            }
            case STARTED -> {
                switch (newStatus) {
                    case STARTED -> this.actualStatus = MatchStatus.STARTED;
                    case FINISHED -> this.actualStatus = MatchStatus.FINISHED;
                    case null, default -> throw new ClientException("CAN'T UPDATE STATUS BECAUSE ACTUAL STATUS IS : " + this.actualStatus);
                }
            }
            case FINISHED -> {
                switch (newStatus) {
                    case FINISHED -> this.actualStatus = MatchStatus.FINISHED;
                    case null, default -> throw new ClientException("CAN'T UPDATE STATUS BECAUSE ACTUAL STATUS IS : " + this.actualStatus);
                }
            }
            case null, default -> this.actualStatus = MatchStatus.NOT_STARTED;
        }
    }
}
