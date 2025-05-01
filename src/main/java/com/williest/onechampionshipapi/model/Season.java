package com.williest.onechampionshipapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Season {
    private final UUID id = UUID.randomUUID();
    private LocalDate beginDate;
    private LocalDate endDate;
}