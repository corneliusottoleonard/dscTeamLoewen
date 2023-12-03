package mock;

import java.util.Objects;

public class Coordinate implements CoordinateI {
    private final int xIndex;
    private final int yIndex;
    private final double value;
    public Coordinate(int xIndex, int yIndex, double value) {
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.value = value;
    }

    public int getxIndex() {
        return xIndex;
    }

    public int getyIndex() {
        return yIndex;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "xIndex=" + xIndex +
                ", yIndex=" + yIndex +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return xIndex == that.xIndex && yIndex == that.yIndex && Double.compare(value, that.value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xIndex, yIndex, value);
    }
}
