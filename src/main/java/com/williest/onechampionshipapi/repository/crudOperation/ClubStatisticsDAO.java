package com.williest.onechampionshipapi.repository.crudOperation;

import com.williest.onechampionshipapi.model.ClubStatistics;
import com.williest.onechampionshipapi.repository.DataSourceDB;
import com.williest.onechampionshipapi.repository.mapper.ClubStatisticsMapper;
import com.williest.onechampionshipapi.service.exception.ServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ClubStatisticsDAO implements EntityDAO<ClubStatistics> {
    private final DataSourceDB dataSource;
    private String sqlRequest;
    private final ClubStatisticsMapper clubStatisticsMapper;
    private final MatchDAO matchDAO;

    public List<ClubStatistics> findAllByClubIdAndSeasonYear(UUID clubId, String seasonYear) {
        List<ClubStatistics> foundClubStatistics = new ArrayList<>();
        sqlRequest = "SELECT club_statistic.club_statistic_id, " +
                "COUNT(goal_id) AS scored_goals " +
                "FROM club_statistic " +
                "JOIN match ON match.match_id = club_statistic.match_id " +
                "JOIN season ON season.season_id = club_statistic.season_id " +
                "JOIN goal ON goal.club_statistic_id = club_statistic.club_statistic_id " +
                "WHERE club_statistic.club_id = ? AND year = ? AND match.actual_status ='FINISHED' " +
                "GROUP BY club_statistic.club_statistic_id;";
        try(Connection dbConnection = dataSource.getConnection();
            PreparedStatement select = dbConnection.prepareStatement(sqlRequest);){
            select.setObject(1, clubId);
            select.setString(2, seasonYear);
            try(ResultSet rs = select.executeQuery()){
                while(rs.next()){
                    int concededGoalsRs = this.findConcededGoalByClubIdAndSeasonYear(clubId, seasonYear);

                    int matchesWhenConcededGoalsRs = this.matchDAO
                            .findAllWhenConcededGoalByClubIdAndSeasonYear(clubId, seasonYear);

                    int allMatchesOfTheClub = this.matchDAO
                            .findAllByClubIdAndSeasonYear(clubId, seasonYear);

                    ClubStatistics clubStatistics = this.clubStatisticsMapper.applyWithAllStats(rs,
                            concededGoalsRs, matchesWhenConcededGoalsRs,
                            allMatchesOfTheClub);
                    foundClubStatistics.add(clubStatistics);
                }
            }
        } catch(SQLException e) {
            throw new ServerException("ERROR IN FIND ALL CLUB STATISTICS BY SEASON YEAR : " + e.getMessage());
        }

        return foundClubStatistics;
    }

    public int findConcededGoalByClubIdAndSeasonYear(UUID clubId, String seasonYear) {
        int concededGoals = 0;
        sqlRequest = "SELECT COUNT(goal_id) AS conceded_goals FROM club_statistic " +
                "JOIN match ON match.match_id = club_statistic.match_id " +
                "JOIN season ON season.season_id = club_statistic.season_id " +
                "JOIN goal ON goal.club_statistic_id = club_statistic.club_statistic_id "+
                "WHERE club_statistic.club_id != ? AND year = ? AND match.actual_status ='FINISHED' " +
                "AND (club_playing_home = ? OR club_playing_away = ?);";
        try(Connection dbConnection = dataSource.getConnection();
            PreparedStatement select = dbConnection.prepareStatement(sqlRequest);){
            select.setObject(1, clubId);
            select.setString(2, seasonYear);
            select.setObject(3, clubId);
            select.setObject(4, clubId);
            try(ResultSet rs = select.executeQuery()){
                while(rs.next()){
                    concededGoals = rs.getInt("conceded_goals");
                }
            }
        } catch(SQLException e) {
            throw new ServerException("ERROR IN FIND ALL CONCEDED GOALS BY CLUB ID AND SEASON YEAR : "
                    + e.getMessage());
        }

        return concededGoals;
    }

    @Override
    public ClubStatistics findById(UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ClubStatistics save(ClubStatistics entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ClubStatistics> saveAll(List<ClubStatistics> entities) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ClubStatistics update(ClubStatistics entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ClubStatistics deleteById(UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
