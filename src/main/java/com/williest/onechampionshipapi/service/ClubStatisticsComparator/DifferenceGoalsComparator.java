package com.williest.onechampionshipapi.service.ClubStatisticsComparator;

import com.williest.onechampionshipapi.model.Club;

import java.util.Comparator;

public class DifferenceGoalsComparator implements Comparator<Club> {
    @Override
    public int compare(Club club1, Club club2) {
        int club1Ranking = club1.getGeneralClubStatistics().getDifferenceGoals();
        int club2Ranking = club2.getGeneralClubStatistics().getDifferenceGoals();

        return Integer.compare(club2Ranking, club1Ranking);
    }
}
