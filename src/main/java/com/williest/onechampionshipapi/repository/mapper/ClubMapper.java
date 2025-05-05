package com.williest.onechampionshipapi.repository.mapper;

import com.williest.onechampionshipapi.model.Club;
import com.williest.onechampionshipapi.model.ClubStatistics;
import com.williest.onechampionshipapi.model.Coach;
import com.williest.onechampionshipapi.repository.crudOperation.ClubStatisticsDAO;
import com.williest.onechampionshipapi.repository.crudOperation.CoachDAO;
import com.williest.onechampionshipapi.service.exception.ServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ClubMapper implements Function<ResultSet, Club> {
    private final CoachMapper coachMapper;
    private final CoachDAO coachDAO;
    private final ClubStatisticsDAO clubStatisticsDAO;

    @Override
    public Club apply(ResultSet rs) {
        try {
            Club club = Club.builder()
                    .id((UUID) rs.getObject("club_id"))
                    .name(rs.getString("club_name"))
                    .acronym(rs.getString("acronym"))
                    .yearCreation(rs.getString("creation_year"))
                    .stadium(rs.getString("stadium_name")).build();


            Coach coach = this.coachDAO.findById((UUID) rs.getObject("coach_id"));
            club.setCoach(coach);

            List<ClubStatistics> clubStatistics = this.clubStatisticsDAO.findAllByClubId(club.getId());
            club.setClubStatistics(clubStatistics);

            return club;
        } catch (SQLException e) {
            throw new ServerException("ERROR IN CLUB MAPPER " + e.getMessage());
        }
    }
}
