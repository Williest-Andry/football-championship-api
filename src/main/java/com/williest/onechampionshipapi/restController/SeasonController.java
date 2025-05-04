package com.williest.onechampionshipapi.restController;

import com.williest.onechampionshipapi.model.Season;
import com.williest.onechampionshipapi.service.SeasonService;
import com.williest.onechampionshipapi.service.exception.ServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SeasonController {
    private final SeasonService seasonService;

    @GetMapping("/seasons")
    public ResponseEntity<Object> getAllSeasons() {
        try{
            List<Season> seasons = this.seasonService.getAllSeasons();
            return ResponseEntity.ok().body(seasons);
        } catch(ServerException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
