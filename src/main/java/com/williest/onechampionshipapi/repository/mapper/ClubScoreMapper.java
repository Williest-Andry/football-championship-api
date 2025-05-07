package com.williest.onechampionshipapi.repository.mapper;

import com.williest.onechampionshipapi.model.ClubScore;
import com.williest.onechampionshipapi.model.Scorer;
import com.williest.onechampionshipapi.repository.crudOperation.PlayerDAO;
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
public class ClubScoreMapper implements Function<ResultSet, ClubScore> {
    private final PlayerDAO playerDAO;

    @Override
    public ClubScore apply(ResultSet rs) {
        try {
            Scorer scorer = new Scorer(
                    this.playerDAO.findById((UUID) rs.getObject("player_id")),
                    rs.getInt("minute_of_goal"),
                    rs.getBoolean("own_goal")
            );

            return new ClubScore(
                    rs.getInt("total_score"),
                    List.of(scorer)
            );
        } catch (SQLException e) {
            throw new ServerException("ERROR IN CLUB SCORE MAPPER : " + e.getMessage());
        }
    }
}
