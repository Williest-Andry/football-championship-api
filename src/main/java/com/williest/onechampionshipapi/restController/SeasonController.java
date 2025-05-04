package com.williest.onechampionshipapi.restController;

import com.williest.onechampionshipapi.model.Season;
import com.williest.onechampionshipapi.restController.createRestEntity.CreateSeason;
import com.williest.onechampionshipapi.restController.createRestEntity.UpdateSeasonStatus;
import com.williest.onechampionshipapi.restController.mapper.SeasonRestMapper;
import com.williest.onechampionshipapi.service.SeasonService;
import com.williest.onechampionshipapi.service.exception.ClientException;
import com.williest.onechampionshipapi.service.exception.NotFoundException;
import com.williest.onechampionshipapi.service.exception.ServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SeasonController {
    private final SeasonService seasonService;
    private final SeasonRestMapper seasonRestMapper;

    @GetMapping("/seasons")
    public ResponseEntity<Object> getAllSeasons() {
        try{
            List<Season> seasons = this.seasonService.getAllSeasons();
            return ResponseEntity.ok().body(seasons);
        } catch(ServerException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/seasons")
    public ResponseEntity<Object> createSeasons(@RequestBody List<CreateSeason> seasonsToCreate) {
        try{
            List<Season> seasons = seasonsToCreate.stream().map(this.seasonRestMapper::apply).toList();
            List<Season> savedSeason = this.seasonService.saveAll(seasons);
            return ResponseEntity.ok().body(savedSeason);
        } catch(ServerException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        } catch(ClientException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/seasons/{seasonYear}/status")
    public ResponseEntity<Object> updateSeasonStatus(@PathVariable String seasonYear, @RequestBody UpdateSeasonStatus status) {
        try{
            Season updatedSeason = this.seasonService.updateStatus(seasonYear, status);
            return ResponseEntity.ok().body(updatedSeason);
        } catch(ServerException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        } catch(ClientException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }  catch(NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
