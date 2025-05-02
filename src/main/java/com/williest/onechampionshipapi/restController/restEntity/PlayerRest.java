package com.williest.onechampionshipapi.restController.restEntity;

import com.williest.onechampionshipapi.model.enumeration.PlayerPosition;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class PlayerRest extends SavedPlayerRest{
    private ClubRest club;

    public PlayerRest(UUID id, String name, int number, String nationality, String birth_year,
                      PlayerPosition playerPosition, ClubRest club) {
        super(id, name, number, nationality, birth_year, playerPosition);
        this.club = club;
    }
}
