package com.williest.onechampionshipapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.williest.onechampionshipapi.model.enumeration.SeasonStatus;
import com.williest.onechampionshipapi.service.exception.ClientException;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Season {
    @Builder.Default
    private final UUID id = UUID.randomUUID();
    @Setter(AccessLevel.NONE)
    private SeasonStatus status;
    private int year;
    @Getter(AccessLevel.NONE)
    private String alias;
    @JsonIgnore
    private List<ClubStatistics> clubStatistics;
    @JsonIgnore
    private List<PlayerStatistics> playerStatistics;

    public String getValidAlias() {
        int yearPlusOne = year + 1;
        return "S" + year + "-" + yearPlusOne;
    }

    public void updateStatus(SeasonStatus newStatus) {
        SeasonStatus actualStatus = this.status;
        switch (actualStatus) {
            case NOT_STARTED -> {
                switch (newStatus) {
                    case NOT_STARTED -> this.status = SeasonStatus.NOT_STARTED;
                    case STARTED -> this.status = SeasonStatus.STARTED;
                    case null, default -> throw new ClientException("CAN'T UPDATE STATUS BECAUSE ACTUAL STATUS IS : " + this.status);
                }
            }
            case STARTED -> {
                switch (newStatus) {
                    case STARTED -> this.status = SeasonStatus.STARTED;
                    case FINISHED -> this.status = SeasonStatus.FINISHED;
                    case null, default -> throw new ClientException("CAN'T UPDATE STATUS BECAUSE ACTUAL STATUS IS : " + this.status);
                }
            }
            case FINISHED -> {
                switch (newStatus) {
                    case FINISHED -> this.status = SeasonStatus.FINISHED;
                    case null, default -> throw new ClientException("CAN'T UPDATE STATUS BECAUSE ACTUAL STATUS IS : " + this.status);
                }
            }
            case null, default -> this.status = SeasonStatus.NOT_STARTED;
        }
    }
}