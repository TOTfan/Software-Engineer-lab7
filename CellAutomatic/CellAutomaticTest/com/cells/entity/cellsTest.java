package com.cells.entity;

import com.cells.util.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class cellsTest {
    private Cells cellTest = new Cells(0,0);
    @BeforeEach
    void setUp() {
        cellTest.setRow(0);
        cellTest.setCol(0);
        cellTest.setStatus(Config.DEAD);
        cellTest.setLiveNums(0);
    }

    @Test
    void getStatus() {
        cellTest.setStatus(Config.DEAD);
        assertEquals(Config.DEAD, cellTest.getStatus());
        cellTest.setStatus(Config.ALIVE);
        assertEquals(Config.ALIVE, cellTest.getStatus());
        cellTest.setStatus(-10000000);
        assertEquals(Config.DEAD, cellTest.getStatus());
    }

    @Test
    void setStatus() {
        cellTest.setStatus(Config.DEAD);
        assertEquals(Config.DEAD, cellTest.getStatus());
        cellTest.setStatus(Config.ALIVE);
        assertEquals(Config.ALIVE, cellTest.getStatus());
        cellTest.setStatus(-10000000);
        assertEquals(Config.DEAD, cellTest.getStatus());
    }

    @Test
    void getRow() {
        cellTest.setRow(-25586);
        assertEquals(0, cellTest.getRow());
        cellTest.setRow(1145141919);
        assertEquals(0, cellTest.getRow());
        cellTest.setRow(37);
        assertEquals(37, cellTest.getRow());
    }

    @Test
    void setRow() {
        cellTest.setRow(-25586);
        assertEquals(0, cellTest.getRow());
        cellTest.setRow(1145141919);
        assertEquals(0, cellTest.getRow());
        cellTest.setRow(37);
        assertEquals(37, cellTest.getRow());
    }

    @Test
    void getCol() {
        cellTest.setCol(-25586);
        assertEquals(0, cellTest.getCol());
        cellTest.setCol(1145141919);
        assertEquals(0, cellTest.getCol());
        cellTest.setCol(17);
        assertEquals(17, cellTest.getCol());
    }

    @Test
    void setCol() {
        cellTest.setCol(-25586);
        assertEquals(0, cellTest.getCol());
        cellTest.setCol(1145141919);
        assertEquals(0, cellTest.getCol());
        cellTest.setCol(17);
        assertEquals(17, cellTest.getCol());
    }

}