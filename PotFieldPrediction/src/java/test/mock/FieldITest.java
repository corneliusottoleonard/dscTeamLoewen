package mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.main.java.mock.Coordinate;
import java.main.java.mock.CoordinateI;
import java.main.java.mock.Field;
import java.main.java.mock.FieldI;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class FieldITest {

    private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    String csv_path1 = "src/python/tests/assets/potFields_small/rimea-half-floor-origin-0-destination-0-DYN_AVOID-Standard-0.csv";
    String csv_path2 = "src/python/tests/assets/potFields_small/rimea-half-floor-origin-0-destination-0-DYN_AVOID-Standard-1.csv";
    FieldI field = new Field(csv_path1);

    @BeforeEach
    void setUp() {
        File f1 = new File(csv_path1);
        File f2 = new File(csv_path2);
        if(f1.exists()) {
            logger.info("csv_path1 exists");
        } else {
            logger.warning("csv_path1 is not correct");
        }
        if(f2.exists()) {
            logger.info("csv_path2 exists");
        } else {
            logger.warning("csv_path2 is not correct");
        }
    }

    CoordinateI getFirstCoordinate(FieldI testField) {
        return field.getCoordinates().get(1);
    }

    @Test
    void getCoordinates() {
        int length = field.getCoordinates().size();
        CoordinateI firstCoordinate = field.getCoordinates().get(0);
        CoordinateI lastCoordinate = field.getCoordinates().get(length-1);
        CoordinateI testFirstCoord = new Coordinate(2,3,112.16);
        CoordinateI testLastCoord = new Coordinate(1098,97,0.00);

        assertEquals(firstCoordinate,testFirstCoord);
        assertEquals(lastCoordinate,testLastCoord);
    }

    @Test
    void setCoordinates() {
        field.setCoordinates(csv_path2);
        int length = field.getCoordinates().size();
        CoordinateI firstCoordinate = field.getCoordinates().get(0);
        CoordinateI lastCoordinate = field.getCoordinates().get(length-1);
        CoordinateI testFirstCoord = new Coordinate(2,3,110.80);
        CoordinateI testLastCoord = new Coordinate(1098,97,0.00);

        assertEquals(firstCoordinate,testFirstCoord);
        assertEquals(lastCoordinate,testLastCoord);
    }
}