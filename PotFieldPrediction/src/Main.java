import java.util.Arrays;

/**
 * This class will be deleted later.
 */
public class Main {
    public static void main(String[] args) {

        // You can test your PredictionService here
        PredictionService predictionService = new PredictionService();
        predictionService.startService();

        // Example: Fit the model
        Object[] trainingData = {
                /* Your training data here */
        };
        float error = predictionService.fit(trainingData);
        System.out.println("Training Error: " + error);

        // Example: Make predictions
        Object[] newData = {
                /* New data for prediction */
        };

        int predictionSteps = 0;
        Object[] predictions = predictionService.predict(newData, predictionSteps);
        System.out.println("Predictions: " + Arrays.toString(predictions));

        // Stop the service
        predictionService.stopService();
    }
}