import Mock.Coordinate;
import Mock.CoordinateI;
import Mock.Field;
import Mock.FieldI;
import junit.framework.TestCase;

public class FieldITest extends TestCase {

    String path1 = "src/test/python/assets/potFields_small/rimea-half-floor-origin-0-destination-0-DYN_AVOID-Standard-0.csv";
    String path2 = "src/test/python/assets/potFields_small/rimea-half-floor-origin-0-destination-0-DYN_AVOID-Standard-1.csv";

    FieldI field = new Field(path1);

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
    }

    public void GetFirstCoordinate(int testXIndex, int testYIndex, double testValue) {
        CoordinateI firstCoordinateField = field.getCoordinates().get(0);
        CoordinateI firstCoordinate = new Coordinate(testXIndex, testYIndex, testValue);
        assertEquals(firstCoordinate,firstCoordinateField);
    }

    public void GetLastCoordinate(int testXIndex, int testYIndex, double testValue, int testLastIndex) {
        int lastIndex = field.getCoordinates().size() - 1;
        assertEquals(testLastIndex, lastIndex);
        CoordinateI lastCoordinateField = field.getCoordinates().get(lastIndex);
        CoordinateI lastCoordinate = new Coordinate(testXIndex, testYIndex, testValue);
        assertEquals(lastCoordinate, lastCoordinateField);
    }

    public void testGetCoordinates() {
        GetFirstCoordinate(2,3,112.16);
        GetLastCoordinate(1098,97,0.00,106404);
    }

    public void testSetCoordinates() {
        field.setCoordinates(path2);
        GetFirstCoordinate(2,3,110.80);
        GetLastCoordinate(1098,97,0.00,106404);
    }
}