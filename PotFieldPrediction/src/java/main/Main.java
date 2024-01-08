import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import edu.hm.teamLoewen.PotField;
import edu.hm.teamLoewen.PredictionService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;


/**
 * This class will be deleted later.
 */
public class Main {

    private static PotField.Field getFieldFromCsv(File csvFile) throws IOException, CsvValidationException {
        CSVReader reader = new CSVReader(new FileReader(csvFile.getAbsolutePath()));
        String[] header = reader.readNext();
        assert header == new String[]{"xIndex", "yIndex", "value"} : "CSV file not in expected format";

        String[] line;
        PotField.Field.Builder fieldBuilder = PotField.Field.newBuilder();
        while ((line = reader.readNext()) != null) {
            PotField.Coordinate.Builder coord = PotField.Coordinate.newBuilder();
            coord.setX(Integer.parseInt(line[0]));
            coord.setY(Integer.parseInt(line[1]));
            coord.setValue(Double.parseDouble(line[2]));
            fieldBuilder.addCoordinates(coord.build());
        }
        return fieldBuilder.build();
    }

    public static void main(String[] args) throws IOException, InterruptedException, FileNotFoundException, CsvValidationException {
        PredictionService predictionService = new PredictionService();
        predictionService.startService();

        String folderPath = "src/python/tests/assets/potFields_small";

        File folder = new File(folderPath);
        File[] files = folder.listFiles((_dir, name) -> name.endsWith(".csv"));

        if (files.length == 0) throw new FileNotFoundException("Directory contains no csv-files.");



        PotField.FieldSequence.Builder sequenceBuilder = PotField.FieldSequence.newBuilder();
        for (File f : files) {
            sequenceBuilder.addFields(getFieldFromCsv(f));
        }

        PotField.FieldSequence fieldSequence = sequenceBuilder.build();

        float error = predictionService.fit(fieldSequence);
        // System.out.println("Training Error: " + error);


        int predictionSteps = 5;
        PotField.FieldSequence predictions = predictionService.predict(fieldSequence, predictionSteps);
        // System.out.println("Predictions: " + Arrays.toString(predictions));

        // Stop the service
        predictionService.stopService();
    }
}
