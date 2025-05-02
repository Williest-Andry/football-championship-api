package com.williest.onechampionshipapi.restController.mapper;

import com.williest.onechampionshipapi.model.Player;
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
                player.getBirthday(),
                player.getPlayerPosition(),
                clubRest
        );
    }
}
