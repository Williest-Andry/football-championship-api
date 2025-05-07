package com.williest.onechampionshipapi.restController.mapper;

import com.williest.onechampionshipapi.model.Scorer;
import com.williest.onechampionshipapi.restController.restEntity.PlayerScorerRest;
import com.williest.onechampionshipapi.restController.restEntity.ScorerRest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ScorerRestMapper implements Function<Scorer, ScorerRest> {
    private final PlayerScorerRestMapper playerScorerRestMapper;

    @Override
    public ScorerRest apply(Scorer scorer) {
        PlayerScorerRest playerScorerRest = this.playerScorerRestMapper.apply(scorer.getPlayer());

        return new ScorerRest(
                playerScorerRest,
                scorer.getMinuteOfGoal(),
                scorer.isOwnGoal()
        );
    }
}
