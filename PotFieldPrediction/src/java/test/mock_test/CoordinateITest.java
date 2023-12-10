package mock_test;


import mock.Coordinate;
import mock.CoordinateI;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateITest {

    CoordinateI coordinate = new Coordinate(1,2,0.1);

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void getxIndex() {
        assertEquals(1,coordinate.getxIndex());
    }

    @org.junit.jupiter.api.Test
    void getyIndex() {
        assertEquals(2,coordinate.getyIndex());
    }
    @org.junit.jupiter.api.Test
    void getValue() {
        assertEquals(0.1,coordinate.getValue());
    }
}