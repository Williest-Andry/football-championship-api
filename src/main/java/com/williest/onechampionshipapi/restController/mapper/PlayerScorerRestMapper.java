package com.williest.onechampionshipapi.restController.mapper;

import com.williest.onechampionshipapi.model.Player;
import com.williest.onechampionshipapi.restController.restEntity.PlayerScorerRest;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PlayerScorerRestMapper implements Function<Player, PlayerScorerRest> {
    @Override
    public PlayerScorerRest apply(Player player) {
        return new PlayerScorerRest(
                player.getId(),
                player.getName(),
                player.getNumber()
        );
    }
}
