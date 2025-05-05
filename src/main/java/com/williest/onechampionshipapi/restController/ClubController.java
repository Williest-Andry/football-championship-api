package com.williest.onechampionshipapi.restController;

import com.williest.onechampionshipapi.model.Club;
import com.williest.onechampionshipapi.model.Player;
import com.williest.onechampionshipapi.restController.createRestEntity.CreatePlayer;
import com.williest.onechampionshipapi.restController.mapper.ClubRestMapper;
import com.williest.onechampionshipapi.restController.mapper.PlayerRestMapper;
import com.williest.onechampionshipapi.restController.restEntity.ClubRest;
import com.williest.onechampionshipapi.restController.restEntity.ClubRestWithStatistics;
import com.williest.onechampionshipapi.restController.restEntity.PlayerRest;
import com.williest.onechampionshipapi.restController.restEntity.SavedPlayerRest;
import com.williest.onechampionshipapi.service.ClubService;
import com.williest.onechampionshipapi.service.PlayerService;
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
public class ClubController {
    private final ClubService clubService;
    private final ClubRestMapper clubRestMapper;
    private final PlayerRestMapper playerRestMapper;
    private final PlayerService playerService;

    @GetMapping("/clubs")
    public ResponseEntity<Object> getAllClubs(){
        try{
            List<Club> allClubs = this.clubService.getAllClubs();
            List<ClubRest> allClubsRest = allClubs.stream().map(this.clubRestMapper::apply).toList();
            return ResponseEntity.ok().body(allClubsRest);
        } catch(ServerException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/clubs")
    public ResponseEntity<Object> saveClub(@RequestBody List<ClubRest> clubsTosave){
        try{
            List<Club> clubs = clubsTosave.stream().map(this.clubRestMapper::toModel).toList();
            List<Club> savedClub = this.clubService.saveAll(clubs);
            List<ClubRest> savedClubRest = savedClub.stream().map(this.clubRestMapper::apply).toList();
            return ResponseEntity.ok().body(savedClubRest);
        } catch(ServerException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        } catch(ClientException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/clubs/{id}/players")
    public ResponseEntity<Object> getClubPlayers(@PathVariable String id){
        try{
            List<Player> clubPlayers = this.playerService.getClubPlayersByClubId(id);
            List<SavedPlayerRest> clubPlayersRest = clubPlayers.stream().map(this.playerRestMapper::applyWithoutClub).toList();
            return ResponseEntity.ok().body(clubPlayersRest);
        } catch(ServerException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        } catch(ClientException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/clubs/{id}/players")
    public ResponseEntity<Object> updateClubPlayers(@PathVariable String id, @RequestBody List<CreatePlayer> playersToUpdate){
        try{
            List<Player> players = playersToUpdate.stream().map(this.playerRestMapper::toModel).toList();
            List<Player> updatedPlayers = this.clubService.updateClubPlayers(id, players);
            List<SavedPlayerRest> updatedPlayersRest = updatedPlayers.stream().map(this.playerRestMapper::applyWithoutClub)
                    .toList();
            return ResponseEntity.ok().body(updatedPlayersRest);
        } catch(ServerException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        } catch(ClientException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/clubs/{id}/players")
    public ResponseEntity<Object> addPlayersToClub(@PathVariable String id, @RequestBody List<CreatePlayer> playersToAdd){
        try{
            List<Player> players = playersToAdd.stream().map(this.playerRestMapper::toModel).toList();
            List<Player> addedPlayers = this.clubService.addClubPlayers(id, players);
            List<SavedPlayerRest> updatedPlayersRest = addedPlayers.stream().map(this.playerRestMapper::applyWithoutClub)
                    .toList();
            return ResponseEntity.ok().body(updatedPlayersRest);
        } catch(ServerException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        } catch(ClientException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/clubs/statistics/{seasonYear}")
    public ResponseEntity<Object> getAllClubsStatisticsInOneSeason(@PathVariable String seasonYear){
        try{
            List<Club> clubsWithStatistics = this.clubService.getAllClubsStatisticsBySeasonYear(seasonYear);
            List<ClubRestWithStatistics> clubsStatistics = clubsWithStatistics.stream().map(this.)
            return ResponseEntity.ok().body(clubsStatistics);
        } catch(ServerException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        } catch(ClientException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
