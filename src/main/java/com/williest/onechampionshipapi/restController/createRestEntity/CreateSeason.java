package com.williest.onechampionshipapi.restController.createRestEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class CreateSeason {
    private int year;
    @Getter(AccessLevel.NONE)
    private String alias;

    public String getValidAlias() {
        int yearPlusOne = year + 1;
        return "S" + year + "-" + yearPlusOne;
    }

    public boolean isValid(){
        return year > 0 && String.valueOf(year).length() == 4 && year >= LocalDate.now().getYear()
                && alias != null && alias.length() == 10;
    }
}
