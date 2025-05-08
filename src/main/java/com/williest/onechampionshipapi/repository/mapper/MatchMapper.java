package com.williest.onechampionshipapi.repository.mapper;

import com.williest.onechampionshipapi.model.*;
import com.williest.onechampionshipapi.model.enumeration.MatchStatus;
import com.williest.onechampionshipapi.repository.crudOperation.ClubDAO;
import com.williest.onechampionshipapi.repository.crudOperation.ClubScoreDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class MatchMapper implements Function<ResultSet, Match> {
    private final ClubDAO clubDAO;
    private final ClubScoreDAO clubScoreDAO;

    @Override
    public Match apply(ResultSet rs) {
        try {
            UUID matchId = (UUID) rs.getObject("match_id");

            // Club playing home
            MatchClub clubPlayingHome = new MatchClub();
            Club clubHome = this.clubDAO.findById((UUID) rs.getObject("club_playing_home"));
            List<ClubScore> clubHomeScores = this.clubScoreDAO.findByMatchIdAndClubId(clubHome.getId(), matchId);
            List<Scorer> allClubHomeScorers = clubHomeScores.stream().map(ClubScore::getScorers).findAny().orElse(new ArrayList<>());
            ClubScore totalClubHomeScores = new ClubScore(
                    !clubHomeScores.isEmpty() ? clubHomeScores.getFirst().getScore() : 0,
                    allClubHomeScorers
            );
            clubPlayingHome.setClub(clubHome);
            clubPlayingHome.setClubScore(totalClubHomeScores);

            // Club playing away
            MatchClub clubPlayingAway = new MatchClub();
            Club clubAway = this.clubDAO.findById((UUID) rs.getObject("club_playing_away"));
            List<ClubScore> clubAwayScores = this.clubScoreDAO.findByMatchIdAndClubId(clubAway.getId(), matchId);
            List<Scorer> allClubAwayScorers = clubAwayScores.stream().map(ClubScore::getScorers).findAny().orElse(new ArrayList<>());
            ClubScore totalClubAwayScores = new ClubScore(
                    !clubAwayScores.isEmpty() ? clubAwayScores.getFirst().getScore() : 0,
                    allClubAwayScorers
            );
            clubPlayingAway.setClub(clubAway);
            clubPlayingAway.setClubScore(totalClubAwayScores);

            Season season = Season.builder().id((UUID) rs.getObject("season_id")).build();

            return Match.builder()
                    .id(matchId)
                    .clubPlayingHome(clubPlayingHome)
                    .clubPlayingAway(clubPlayingAway)
                    .stadium(rs.getString("stadium"))
                    .matchDateTime(rs.getTimestamp("match_date_time") != null ?
                            rs.getTimestamp("match_date_time").toLocalDateTime() : null)
                    .actualStatus(MatchStatus.valueOf(rs.getString("actual_status")))
                    .season(season)
                    .playerStatistics(null).clubStatistics(null).build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
