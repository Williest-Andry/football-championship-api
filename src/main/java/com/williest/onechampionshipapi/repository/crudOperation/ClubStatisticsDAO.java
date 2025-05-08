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

    public List<ClubStatistics> findAllByClubIdAndSeasonYear(UUID clubId, String seasonYear) {
        List<ClubStatistics> foundClubStatistics = new ArrayList<>();
        sqlRequest = "SELECT club_statistic_id, scored_goals, conceded_goals, " +
                "SUM(scored_goals - conceded_goals) AS difference_goals, " +
                "COUNT(conceded_goals) FILTER (WHERE conceded_goals = 0) AS clean_sheets "+
                "FROM club_statistic " +
                "JOIN match ON match.match_id = club_statistic.match_id " +
                "JOIN season ON season.season_id = club_statistic.season_id " +
                "WHERE club_id = ? AND year = ? AND match.actual_status ='FINISHED' " +
                "GROUP BY club_statistic_id, scored_goals, conceded_goals;";
        try(Connection dbConnection = dataSource.getConnection();
            PreparedStatement select = dbConnection.prepareStatement(sqlRequest);){
            select.setObject(1, clubId);
            select.setString(2, seasonYear);
            try(ResultSet rs = select.executeQuery()){
                while(rs.next()){
                    ClubStatistics clubStatistics = this.clubStatisticsMapper.apply(rs);
                    foundClubStatistics.add(clubStatistics);
                }
            }
        } catch(SQLException e) {
            throw new ServerException("ERROR IN FIND ALL CLUB STATISTICS BY SEASON YEAR : " + e.getMessage());
        }

        return foundClubStatistics;
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
