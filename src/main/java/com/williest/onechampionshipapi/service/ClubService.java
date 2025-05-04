package com.williest.onechampionshipapi.service;

import com.williest.onechampionshipapi.model.Club;
import com.williest.onechampionshipapi.repository.crudOperation.ClubDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubService {
    private final ClubDAO clubDAO;

    public List<Club> getAllClubs() {
        return this.clubDAO.findAll();
    }
}
