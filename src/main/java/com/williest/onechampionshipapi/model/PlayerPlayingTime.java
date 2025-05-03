package com.williest.onechampionshipapi.model;

import com.williest.onechampionshipapi.model.enumeration.DurationUnit;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerPlayingTime {
    private Double value;
    private DurationUnit durationUnit;
}
