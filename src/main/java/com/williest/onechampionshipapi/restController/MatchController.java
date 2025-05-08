package com.williest.onechampionshipapi.restController;

import com.williest.onechampionshipapi.model.Match;
import com.williest.onechampionshipapi.restController.createRestEntity.UpdateMatchStatus;
import com.williest.onechampionshipapi.restController.mapper.MatchRestMapper;
import com.williest.onechampionshipapi.restController.restEntity.MatchRest;
import com.williest.onechampionshipapi.service.MatchService;
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
public class MatchController {
    private final MatchService matchService;
    private final MatchRestMapper matchRestMapper;

    @PostMapping("/matchMaker/{seasonYear}")
    public ResponseEntity<Object> matchMakerForOneSeason(@PathVariable String seasonYear) {
        try{
            List<Match> allMatchInSeason = this.matchService.matchMakerForOneSeason(seasonYear);
            List<MatchRest> allMatchesRestInSeason = allMatchInSeason.stream().map(this.matchRestMapper::apply).toList();
            return ResponseEntity.ok().body(allMatchesRestInSeason);
        } catch(ServerException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        } catch(ClientException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/matches/{seasonYear}")
    public ResponseEntity<Object> getAllMatchesForOneSeason(@PathVariable String seasonYear,
                                                            @RequestParam(required = false) String matchStatus,
                                                            @RequestParam(required = false) String clubPlayingName,
                                                            @RequestParam(required = false) String matchAfter,
                                                            @RequestParam(required = false) String matchBeforeOrEquals) {
        try{
            List<Match> allMatchesInSeason = this.matchService.getAllMatchForOneSeason(seasonYear, matchStatus,
                    clubPlayingName, matchAfter, matchBeforeOrEquals);
            List<MatchRest> allMatchesRestInSeason = allMatchesInSeason.stream().map(this.matchRestMapper::apply).toList();
            return ResponseEntity.ok().body(allMatchesRestInSeason);
        } catch(ServerException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        } catch(ClientException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/matches/{id}/status")
    public ResponseEntity<Object> updateMatchStatus(@PathVariable String id, @RequestBody UpdateMatchStatus status){
        try{
            Match updatedMatch = this.matchService.updateMatchStatus(id, status.getStatus());
            MatchRest updatedMatchRest = this.matchRestMapper.apply(updatedMatch);
            return ResponseEntity.ok().body(updatedMatchRest);
        } catch(ServerException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        } catch(ClientException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
