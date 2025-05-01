package com.williest.onechampionshipapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Season {
    private String id;
    private LocalDate beginDate;
    private LocalDate endDate;
}