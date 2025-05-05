package com.williest.onechampionshipapi.repository.mapper;

import com.williest.onechampionshipapi.model.Club;
import com.williest.onechampionshipapi.model.Coach;
import com.williest.onechampionshipapi.model.Player;
import com.williest.onechampionshipapi.model.enumeration.PlayerPosition;
import com.williest.onechampionshipapi.repository.crudOperation.CoachDAO;
import com.williest.onechampionshipapi.service.exception.ServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class PlayerMapper implements Function<ResultSet, Player> {
    private final CoachDAO coachDAO;

    public Player applyWithClubIdOnly(ResultSet rs){
        try{
            Player player = Player.builder().id((UUID) rs.getObject("player_id")).build();
            player.setName(rs.getString("player_name"));
            player.setNumber(rs.getInt("player_number"));
            player.setBirth_year(rs.getString("player_birth_year"));
            player.setPlayerPosition(PlayerPosition.valueOf(rs.getString("player_position")));
            player.setNationality(rs.getString("player_nationality"));

            Club club = Club.builder().id((UUID) rs.getObject("club_id")).build();
            player.setClub(club);

            return player;
        } catch (SQLException e){
            throw new ServerException("ERROR IN PLAYER MAPPER ONLY : " + e.getMessage());
        }
    }

    @Override
    public Player apply(ResultSet rs) {
        try {
            Player player = this.applyWithClubIdOnly(rs);

            Club club = Club.builder().id((UUID) (rs.getObject("club_id"))).build();
            club.setName(rs.getString("club_name"));
            club.setAcronym(rs.getString("acronym"));
            club.setYearCreation(rs.getString("creation_year"));
            club.setStadium(rs.getString("stadium_name"));

            UUID coachId = (UUID) rs.getObject("coach_id");
            Coach coach = this.coachDAO.findById(coachId);

            club.setCoach(coach);
            player.setClub(club);
            return player;
        } catch (SQLException e) {
            throw new ServerException("ERROR IN PLAYER MAPPER : " + e.getMessage());
        }
    }

}
