package com.williest.onechampionshipapi.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Coach {
    @Builder.Default
    private final UUID id = UUID.randomUUID();
    private String name;
    private String nationality;
}
