package com.example.chappar10.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class ConverterTest {

    @Test
    public void personBornLastCentury() {
        //arrange
        Date date = new Date(1999, 10, 10);
        //act
        int age = Converter.getAge(date);
        //assert
        assertEquals(23, age);
    }

    @Test
    public void personBornThisCentury() {
        //arrange
        Date date = new Date(2000, 10, 10);
        //act
        int age = Converter.getAge(date);
        //assert
        assertEquals(22, age);
    }

    @Test
    public void personWasBornThisYear() {
        //arrange
        Date date = new Date(2022, 03, 10);
        //act
        int age = Converter.getAge(date);
        //assert
        assertEquals(0, age);
    }

    public void personWasBornThisMonth() {
        //arrange
        Date date = new Date(2022, 12, 1);
        //act
        int age = Converter.getAge(date);
        //assert
        assertEquals(0, age);
    }

   @Test
    public void personBornInFuture() {
        //arrange
        Date date = new Date(2025, 12, 10);
        //act
        int age = Converter.getAge(date);
        //assert
        assertEquals(-4, age);
    }
}
