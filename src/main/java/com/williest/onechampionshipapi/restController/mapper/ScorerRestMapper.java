package com.williest.onechampionshipapi.restController.mapper;

import com.williest.onechampionshipapi.model.Club;
import com.williest.onechampionshipapi.model.Player;
import com.williest.onechampionshipapi.model.Scorer;
import com.williest.onechampionshipapi.restController.createRestEntity.AddGoal;
import com.williest.onechampionshipapi.restController.restEntity.PlayerScorerRest;
import com.williest.onechampionshipapi.restController.restEntity.ScorerRest;
import com.williest.onechampionshipapi.service.typeVerification.IdVerification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;
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
                scorer.getOwnGoal()
        );
    }

    public Scorer toModel(AddGoal goal){
        UUID validClubId = IdVerification.validUUID(String.valueOf(goal.getClubId()));
        Club scorerClub = Club.builder().id(validClubId).build();

        UUID validScorerId = IdVerification.validUUID(goal.getScorerIdentifier());
        Player playerScorer = Player.builder().id(validScorerId).build();
        playerScorer.setClub(scorerClub);

        return new Scorer(
                playerScorer,
                goal.getMinuteOfGoal(),
                false
        );
    }
}
