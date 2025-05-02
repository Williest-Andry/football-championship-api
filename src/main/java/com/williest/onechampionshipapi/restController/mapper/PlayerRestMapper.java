package com.williest.onechampionshipapi.restController.mapper;

import com.williest.onechampionshipapi.model.Player;
import com.williest.onechampionshipapi.restController.createRestEntity.CreatePlayer;
import com.williest.onechampionshipapi.restController.restEntity.ClubRest;
import com.williest.onechampionshipapi.restController.restEntity.PlayerRest;
import com.williest.onechampionshipapi.restController.restEntity.SavedPlayerRest;
import com.williest.onechampionshipapi.service.exception.ClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class PlayerRestMapper implements Function<Player, PlayerRest> {
    private final ClubRestMapper clubRestMapper;

    public SavedPlayerRest applyWithoutClub(Player player) {
        return new SavedPlayerRest(
                player.getId(),
                player.getName(),
                player.getNumber(),
                player.getNationality(),
                player.getBirth_year(),
                player.getPlayerPosition()
        );
    }

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
        if(createPlayer == null){
            throw new ClientException("The player object can't be empty");
        }
        if(!createPlayer.isValid()){
            throw new ClientException("The player format is incorrect");
        }

        UUID createPlayerId;
        try{
            createPlayerId = UUID.fromString(createPlayer.getId());
        } catch (IllegalArgumentException e){
            createPlayerId = null;
        }

        return Player.builder()
                .id(createPlayerId)
                .name(createPlayer.getName())
                .number(createPlayer.getNumber())
                .nationality(createPlayer.getNationality())
                .birth_year(createPlayer.getBirthYear())
                .playerPosition(createPlayer.getPlayerPosition())
                .club(null).build();
    }
}
