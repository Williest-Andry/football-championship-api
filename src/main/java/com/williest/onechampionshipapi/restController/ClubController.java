package com.williest.onechampionshipapi.restController;

import com.williest.onechampionshipapi.model.Club;
import com.williest.onechampionshipapi.restController.mapper.ClubRestMapper;
import com.williest.onechampionshipapi.restController.restEntity.ClubRest;
import com.williest.onechampionshipapi.service.ClubService;
import com.williest.onechampionshipapi.service.exception.ServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClubController {
    private final ClubService clubService;
    private final ClubRestMapper clubRestMapper;

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
}
