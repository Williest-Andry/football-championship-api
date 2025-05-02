package com.williest.onechampionshipapi.repository;

import com.williest.onechampionshipapi.service.exception.ServerException;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@NoArgsConstructor
public class DataSourceDB {
    private final String url = System.getenv("DB_URL");
    private final String user = System.getenv("DB_USERNAME");
    private final String password = System.getenv("DB_PASSWORD");

    public Connection getConnection(){
        try{
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new ServerException("DATABASE CONNECTION FAILED : " + e.getMessage());
        }
    }
}
