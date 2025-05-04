package com.williest.onechampionshipapi.repository.mapper;

import com.williest.onechampionshipapi.model.Coach;
import com.williest.onechampionshipapi.service.exception.ServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class CoachMapper implements Function<ResultSet, Coach> {
    @Override
    public Coach apply(ResultSet rs) {
        try{
            return Coach.builder()
                    .id((UUID) rs.getObject("coach_id"))
                    .name(rs.getString("coach_name"))
                    .nationality(rs.getString("coach_nationality")).build();
        } catch (SQLException e){
            throw new ServerException("ERROR IN COACH MAPPER " + e.getMessage());
        }
    }
}
