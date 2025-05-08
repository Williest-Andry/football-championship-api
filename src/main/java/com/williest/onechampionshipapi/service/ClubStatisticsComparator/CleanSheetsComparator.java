package com.williest.onechampionshipapi.service.ClubStatisticsComparator;

import com.williest.onechampionshipapi.model.Club;

import java.util.Comparator;

public class CleanSheetsComparator implements Comparator<Club> {
    @Override
    public int compare(Club club1, Club club2) {
        int club1Ranking = club1.getGeneralClubStatistics().getCleanSheetNumber();
        int club2Ranking = club2.getGeneralClubStatistics().getCleanSheetNumber();

        return Integer.compare(club2Ranking, club1Ranking);
    }
}
