import Mock.Coordinate;
import Mock.CoordinateI;
import junit.framework.TestCase;

public class CoordinateITest extends TestCase {

    CoordinateI coordinate = new Coordinate(1, 2, 3.0);

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
    }

    public void testGetxIndex() {
        assertEquals(1,coordinate.getxIndex());
    }

    public void testGetyIndex() {
        assertEquals(2,coordinate.getyIndex());
    }

    public void testGetValue() {
        assertEquals(3.0,coordinate.getValue());
    }
}