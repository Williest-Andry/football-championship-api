package com.williest.onechampionshipapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.williest.onechampionshipapi.model.enumeration.SeasonStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Season {
    @Builder.Default
    private final UUID id = UUID.randomUUID();
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
}