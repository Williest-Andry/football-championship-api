package com.williest.onechampionshipapi.restController.restEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CoachRest {
    private String name;
    private String nationality;

    @JsonIgnore
    public boolean isValid(){
        return name != null && nationality != null;
    }
}
