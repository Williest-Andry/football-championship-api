package com.williest.onechampionshipapi.repository.crudOperation;

import com.williest.onechampionshipapi.model.ClubStatistics;
import com.williest.onechampionshipapi.model.PlayerStatistics;
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

    public List<ClubStatistics> findAllByClubIdAndSeasonYear(UUID clubId, String seasonYear) {
        List<ClubStatistics> foundClubStatistics = new ArrayList<>();
        sqlRequest = "SELECT club_statistic.club_statistic_id, match.match_id AS id_match, " +
                "COUNT(goal_id) AS scored_goals " +
                "FROM club_statistic " +
                "JOIN match ON match.match_id = club_statistic.match_id " +
                "JOIN season ON season.season_id = club_statistic.season_id " +
                "JOIN goal ON goal.club_statistic_id = club_statistic.club_statistic_id " +
                "WHERE club_statistic.club_id = ? AND year = ? AND match.actual_status ='FINISHED' " +
                "GROUP BY club_statistic.club_statistic_id, match.match_id;";
        try(Connection dbConnection = dataSource.getConnection();
            PreparedStatement select = dbConnection.prepareStatement(sqlRequest);){
            select.setObject(1, clubId);
            select.setString(2, seasonYear);
            try(ResultSet rs = select.executeQuery()){
                while(rs.next()){
                    int concededGoals = this.findConcededGoalByClubIdAndSeasonYear(clubId, seasonYear);

                    int matchesWhenConcededGoals = this.findAllWhenConcededGoalByClubIdAndSeasonYear(clubId, seasonYear);

                    int allMatchesOfTheClub = this.findAllMatchByClubIdAndSeasonYear(clubId, seasonYear);

                    ClubStatistics clubStatistics = this.clubStatisticsMapper.applyWithAllStats(rs,
                            concededGoals, matchesWhenConcededGoals,
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

    public List<ClubStatistics> findAllByClubId(UUID clubId) {
        List<ClubStatistics> foundClubStatistics = new ArrayList<>();
        sqlRequest = "SELECT club_statistic.club_statistic_id, club_statistic.match_id AS id_match, " +
                "COUNT(goal_id) AS scored_goals " +
                "FROM club_statistic " +
                "JOIN match ON match.match_id = club_statistic.match_id " +
                "JOIN goal ON goal.club_statistic_id = club_statistic.club_statistic_id " +
                "WHERE club_statistic.club_id = ? " +
                "GROUP BY club_statistic.club_statistic_id, club_statistic.match_id;";
        try(Connection dbConnection = dataSource.getConnection();
            PreparedStatement select = dbConnection.prepareStatement(sqlRequest);){
            select.setObject(1, clubId);
            try(ResultSet rs = select.executeQuery()){
                while(rs.next()){
                    ClubStatistics clubStatistics = this.clubStatisticsMapper.apply(rs);
                    foundClubStatistics.add(clubStatistics);
                }
            }
        } catch(SQLException e) {
            throw new ServerException("ERROR IN FIND ALL CLUB STATISTICS BY CLUB ID : " + e.getMessage());
        }

        return foundClubStatistics;
    }

    public int findAllWhenConcededGoalByClubIdAndSeasonYear(UUID clubId, String seasonYear){
        int totalFoundMatches = 0;
        sqlRequest = "SELECT match.match_id AS match_conceded_goals FROM club_statistic  " +
                "JOIN goal ON goal.club_statistic_id =  club_statistic. club_statistic_id " +
                "JOIN match ON match.match_id = goal.match_id "+
                "JOIN season ON season.season_id = match.season_id " +
                "JOIN club ON club.club_id = match.club_playing_home OR club.club_id = match.club_playing_home " +
                "WHERE year = ? " +
                "AND club_statistic.club_id != ? AND (club_playing_home = ? OR club_playing_away = ?) " +
                "GROUP BY match.match_id;";

        try(Connection dbConnection = dataSource.getConnection();
            PreparedStatement select = dbConnection.prepareStatement(sqlRequest);){
            select.setString(1, seasonYear);
            select.setObject(2, clubId);
            select.setObject(3, clubId);
            select.setObject(4, clubId);
            try(ResultSet rs = select.executeQuery()){
                while(rs.next()){
                    totalFoundMatches = rs.getRow();
                }
            }
        } catch(SQLException e) {
            throw new ServerException("ERROR IN FIND ALL MATCHES WHEN CONCEDED GOAL BY CLUB ID AND SEASON YEAR : "
                    + e.getMessage());
        }

        return totalFoundMatches;
    }

    public int findAllMatchByClubIdAndSeasonYear(UUID clubId, String seasonYear){
        int totalFoundMatches = 0;
        sqlRequest = "SELECT COUNT(match_id) AS all_matches FROM match " +
                "JOIN season ON season.season_id = match.season_id " +
                "JOIN club ON club.club_id = match.club_playing_home OR club.club_id =match.club_playing_home " +
                "WHERE year = ? AND club.club_id = ?;";

        try(Connection dbConnection = dataSource.getConnection();
            PreparedStatement select = dbConnection.prepareStatement(sqlRequest);){
            select.setString(1, seasonYear);
            select.setObject(2, clubId);
            try(ResultSet rs = select.executeQuery()){
                while(rs.next()){
                    totalFoundMatches = rs.getInt("all_matches");
                }
            }
        } catch(SQLException e) {
            throw new ServerException("ERROR IN FIND ALL MATCHES BY CLUB ID AND SEASON YEAR : " + e.getMessage());
        }

        return totalFoundMatches;
    }

    @Override
    public ClubStatistics findById(UUID id) {
        ClubStatistics clubStatistics = null;
        sqlRequest = "SELECT *, match_id AS id_match FROM club_statistic " +
                "WHERE club_statistic.club_statistic_id = ? ";

        try(Connection dbConnection = dataSource.getConnection();
            PreparedStatement select = dbConnection.prepareStatement(sqlRequest);){
            select.setObject(1, id);
            try(ResultSet rs = select.executeQuery();){
                if(rs.next()) {
                    clubStatistics = this.clubStatisticsMapper.apply(rs);
                }
            }
        } catch(SQLException e){
            throw new ServerException("ERROR IN FIND CLUB STATISTICS BY ID : " + e.getMessage());
        }

        return clubStatistics;
    }

    @Override
    public ClubStatistics save(ClubStatistics clubStatistics) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ClubStatistics saveWithMatchIdAndSeasonId(ClubStatistics clubStatistics, UUID clubId, UUID seasonId, UUID matchId) {
        sqlRequest = "INSERT INTO club_statistic(club_statistic_id, club_id, season_id, match_id) VALUES(?,?,?,?);";

        try(Connection dbConnection = dataSource.getConnection();
            PreparedStatement insert = dbConnection.prepareStatement(sqlRequest);){
            insert.setObject(1, clubStatistics.getId());
            insert.setObject(2, clubId);
            insert.setObject(3, seasonId);
            insert.setObject(4, matchId);
            insert.executeUpdate();
        } catch(SQLException e){
            throw new ServerException("ERROR IN SAVE CLUB STATISITC : " + e.getMessage());
        }

        return this.findById(clubStatistics.getId());
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
