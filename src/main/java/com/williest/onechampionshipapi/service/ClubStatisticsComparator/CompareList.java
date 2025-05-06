package com.williest.onechampionshipapi.service.ClubStatisticsComparator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CompareList {
    public static <T> boolean hasDuplicates(List<T> list) {
        Set<T> set = new HashSet<>();
        for (T element : list) {
            if (!set.add(element)) {
                return true;
            }
        }
        return false;
    }
}
