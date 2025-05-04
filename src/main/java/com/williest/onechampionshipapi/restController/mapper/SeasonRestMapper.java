package com.williest.onechampionshipapi.restController.mapper;

import com.williest.onechampionshipapi.model.Season;
import com.williest.onechampionshipapi.restController.createRestEntity.CreateSeason;
import com.williest.onechampionshipapi.service.exception.ClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class SeasonRestMapper implements Function<CreateSeason, Season> {
    @Override
    public Season apply(CreateSeason createSeason) {
        if(!createSeason.isValid()) {
            throw new ClientException("The year or the alias is not valid");
        }
        return Season.builder()
                .year(createSeason.getYear())
                .alias(createSeason.getValidAlias())
                .clubStatistics(null).playerStatistics(null).build();
    }
}
