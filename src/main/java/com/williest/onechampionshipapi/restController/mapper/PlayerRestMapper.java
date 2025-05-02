package com.williest.onechampionshipapi.restController.mapper;

import com.williest.onechampionshipapi.model.Player;
import com.williest.onechampionshipapi.restController.createRestEntity.CreatePlayer;
import com.williest.onechampionshipapi.restController.restEntity.ClubRest;
import com.williest.onechampionshipapi.restController.restEntity.PlayerRest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class PlayerRestMapper implements Function<Player, PlayerRest> {
    private final ClubRestMapper clubRestMapper;

    @Override
    public PlayerRest apply(Player player) {
        ClubRest clubRest = this.clubRestMapper.apply(player.getClub());
        return new PlayerRest(
                player.getId(),
                player.getName(),
                player.getNumber(),
                player.getNationality(),
                player.getBirth_year(),
                player.getPlayerPosition(),
                clubRest
        );
    }

    public Player toModel(CreatePlayer createPlayer) {
        return Player.builder()
                .id(createPlayer.getId())
                .name(createPlayer.getName())
                .number(createPlayer.getNumber())
                .nationality(createPlayer.getNationality())
                .birth_year(createPlayer.getBirthYear())
                .playerPosition(createPlayer.getPlayerPosition())
                .club(null).build();
    }
}
