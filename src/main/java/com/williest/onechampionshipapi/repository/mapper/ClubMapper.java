package com.williest.onechampionshipapi.repository.mapper;

import com.williest.onechampionshipapi.model.Club;
import com.williest.onechampionshipapi.model.ClubStatistics;
import com.williest.onechampionshipapi.model.Coach;
import com.williest.onechampionshipapi.model.League;
import com.williest.onechampionshipapi.repository.crudOperation.ClubStatisticsDAO;
import com.williest.onechampionshipapi.repository.crudOperation.CoachDAO;
import com.williest.onechampionshipapi.repository.crudOperation.LeagueDAO;
import com.williest.onechampionshipapi.service.exception.ServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ClubMapper implements Function<ResultSet, Club> {
    private final CoachDAO coachDAO;
    private final LeagueDAO leagueDAO;

    @Override
    public Club apply(ResultSet rs) {
        League league = this.leagueDAO.findTheOneLeague();

        try {
            Club club = Club.builder()
                    .id((UUID) rs.getObject("club_id"))
                    .name(rs.getString("club_name"))
                    .acronym(rs.getString("acronym"))
                    .yearCreation(rs.getString("creation_year"))
                    .stadium(rs.getString("stadium_name")).build();

            club.setLeague(league);

            Coach coach = this.coachDAO.findById((UUID) rs.getObject("coach_id"));
            club.setCoach(coach);

            return club;
        } catch (SQLException e) {
            throw new ServerException("ERROR IN CLUB MAPPER " + e.getMessage());
        }
    }
}
