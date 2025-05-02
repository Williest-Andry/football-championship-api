package com.williest.onechampionshipapi.restController;

import com.williest.onechampionshipapi.model.Player;
import com.williest.onechampionshipapi.restController.mapper.PlayerRestMapper;
import com.williest.onechampionshipapi.restController.restEntity.PlayerRest;
import com.williest.onechampionshipapi.service.PlayerService;
import com.williest.onechampionshipapi.service.exception.ClientException;
import com.williest.onechampionshipapi.service.exception.NotFoundException;
import com.williest.onechampionshipapi.service.exception.ServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;
    private final PlayerRestMapper playerRestMapper;

    @GetMapping("/players")
    public ResponseEntity<Object> getPlayers(@RequestParam(required = false) String name,
                                             @RequestParam(required = false) Integer ageMinimum,
                                             @RequestParam(required = false) Integer ageMaximum,
                                             @RequestParam(required = false) String clubName) {
        try{
            List<Player> foundPlayers = this.playerService.getAllPlayers(name, ageMinimum, ageMaximum, clubName);
            List<PlayerRest> foundPlayersRest = foundPlayers.stream().map(this.playerRestMapper::apply).toList();
            return ResponseEntity.ok().body(foundPlayersRest);
        } catch(ServerException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        } catch(ClientException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}