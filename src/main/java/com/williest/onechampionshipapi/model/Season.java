package com.williest.onechampionshipapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.williest.onechampionshipapi.model.enumeration.SeasonStatus;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Season {
    @Builder.Default
    private final UUID id = UUID.randomUUID();
    private SeasonStatus status;
    private int year;
    private String alias;
    @JsonIgnore
    private List<ClubStatistics> clubStatistics;
    @JsonIgnore
    private List<PlayerStatistics> playerStatistics;

    public String getAlias(){
        if(alias.length() != 10){
            return "S"+String.valueOf(year)+"-"+String.valueOf(year + 1);
        }
        return alias;
    }
}