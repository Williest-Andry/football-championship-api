package com.williest.onechampionshipapi.service.ClubStatisticsComparator;


import com.williest.onechampionshipapi.model.Club;

import java.util.Comparator;

public class RankingComparator implements Comparator<Club> {
    @Override
    public int compare(Club club1, Club club2) {
        int club1Ranking = club1.getGeneralClubStatistics().getRankingPoints();
        int club2Ranking = club2.getGeneralClubStatistics().getRankingPoints();

        return Integer.compare(club1Ranking, club2Ranking);
    }
}
