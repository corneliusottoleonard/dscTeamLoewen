package mock_test;

import mock.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class FieldSequenceITest {
    private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    String csv_path1 = "src/python/tests/assets/potFields_small/rimea-half-floor-origin-0-destination-0-DYN_AVOID-Standard-0.csv";
    String csv_path2 = "src/python/tests/assets/potFields_small/rimea-half-floor-origin-0-destination-0-DYN_AVOID-Standard-1.csv";
    FieldSequenceI fields = new FieldSequence();

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

    @Test
    void getFields() {
        fields.addField(csv_path1);
        fields.addField(csv_path2);

        FieldI field = fields.getFields().get(0);

        int length = field.getCoordinates().size();
        CoordinateI firstCoordinate = field.getCoordinates().get(0);
        CoordinateI lastCoordinate = field.getCoordinates().get(length-1);
        CoordinateI testFirstCoord = new Coordinate(2,3,112.16);
        CoordinateI testLastCoord = new Coordinate(1098,97,0.00);

        assertEquals(firstCoordinate,testFirstCoord);
        assertEquals(lastCoordinate,testLastCoord);

        FieldI field2 = fields.getFields().get(1);

        int length2 = field2.getCoordinates().size();
        CoordinateI firstCoordinate2 = field2.getCoordinates().get(0);
        CoordinateI lastCoordinate2 = field2.getCoordinates().get(length2-1);
        CoordinateI testFirstCoord2 = new Coordinate(2,3,110.80);
        CoordinateI testLastCoord2 = new Coordinate(1098,97,0.00);

        assertEquals(firstCoordinate2,testFirstCoord2);
        assertEquals(lastCoordinate2,testLastCoord2);
    }
}