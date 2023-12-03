package mock;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Field implements FieldI {
    private String filePath;
    private List<CoordinateI> coordinates;

    public Field(String filePath) {
        coordinates = readCSV(filePath);
    }
    private List<CoordinateI> readCSV(String filePath) {
        List<CoordinateI> returnCoordinates = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            String[] header = csvReader.readNext();

            String[] line;
            while ((line = csvReader.readNext()) != null) {
                int xIndex = Integer.parseInt(line[0]);
                int yIndex = Integer.parseInt(line[1]);
                double value = Double.parseDouble(line[2]);

                Coordinate data = new Coordinate(xIndex, yIndex, value);
                returnCoordinates.add(data);
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
        return returnCoordinates;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<CoordinateI> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String filePath) {
        this.coordinates = readCSV(filePath);
    }

    @Override
    public String toString() {
        String output = "";
        for (int i = 0; i < 5; i++) {
            output = output+i+" "+this.coordinates.get(i)+"\n";
        }
        output = output+"...\n";
        int length = this.coordinates.size();

        for (int i = 0; i < 5; i++) {
            int index = length-5+i;
            output = output+index+" "+this.coordinates.get(index)+"\n";
        }
        return output;
    }
}
