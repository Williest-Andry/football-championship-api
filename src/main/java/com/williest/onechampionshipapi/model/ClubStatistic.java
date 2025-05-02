package com.williest.onechampionshipapi.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class ClubStatistic extends EntityStatistic{
    private int rankingPoints;
    private int concededGoals;
    private int differenceGoals;
    private int cleanSheetNumber;
}
