package com.openclassrooms.realestatemanager;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FormatDateUnitTest {
    @Test
    public void formatDate_isCorrect() {
        String date = "31/10/2021";
        assertEquals(date, Utils.getTodayDate());
    }
}
