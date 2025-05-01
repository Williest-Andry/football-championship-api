package com.williest.onechampionshipapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class Coach {
    private final UUID id = UUID.randomUUID();
    private String name;
    private String nationality;
}
