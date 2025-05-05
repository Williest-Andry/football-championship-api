package com.williest.onechampionshipapi.restController.mapper;

import com.williest.onechampionshipapi.model.Coach;
import com.williest.onechampionshipapi.restController.restEntity.CoachRest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class CoachRestMapper implements Function<Coach, CoachRest> {

    @Override
    public CoachRest apply(Coach coach) {
        return new CoachRest(
                coach.getName(),
                coach.getNationality()
        );
    }

    public Coach toModel(CoachRest coachRest) {
        return Coach.builder()
                .name(coachRest.getName())
                .nationality(coachRest.getNationality()).build();
    }
}
