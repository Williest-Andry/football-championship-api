package com.williest.onechampionshipapi.model;

import lombok.*;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class ClubStatistic extends EntityStatistic{
    private int rankingPoints;
    private int concededGoals;
    private int differenceGoals;
    private int cleanSheetNumber;

    public ClubStatistic(UUID id, int scoredGoals, int rankingPoints, int concededGoals, int differenceGoals, int cleanSheetNumber){
        super(id, scoredGoals);
        this.rankingPoints = rankingPoints;
        this.concededGoals = concededGoals;
        this.differenceGoals = differenceGoals;
        this.cleanSheetNumber = cleanSheetNumber;
    }
}
