
import java.io.IOException;
import java.lang.InterruptedException;
import java.util.logging.Logger;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * A service for managing predictions using a machine learning model.
 */
public class PredictionService implements AutoCloseable {

    private Process process;
    private ProcessHandle processHandle;
    private final String[] PROCESS_COMMAND = {"/home/nicklas/Uni/dscTeamLoewen/env/bin/python3", "/home/nicklas/Uni/dscTeamLoewen/PotFieldPrediction/src/python/main.py"}; // TODO: replace by actual script
    private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private PredictionServiceGrpc.PredictionServiceBlockingStub blockingStub;


    /**
     * Starts the prediction service.
     */
    public void startService() throws IOException, ServiceNotRunningException, InterruptedException {
        process = new ProcessBuilder(PROCESS_COMMAND).inheritIO().start();
        Thread.sleep(4000);
        processHandle = process.toHandle();
        if (!processHandle.isAlive()) throw new ServiceNotRunningException("Unable to start prediction service.");
        logger.info("Prediction Service started with PID " + processHandle.pid());

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .maxInboundMessageSize(100 * 1024 * 1024)
                .build();
        blockingStub = PredictionServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Stops the prediction service.
     */
    public void stopService() throws InterruptedException {
        process.destroy();
        logger.info("Prediction Service stopped.");
    }

    public boolean isServiceRunning() {
        processHandle = process.toHandle();
        return processHandle.isAlive();
    }

    /**
     * Fits the model with the provided data.
     *
     * @param data The input data for training the model.
     * @return The error value after fitting the model.
     */
    public float fit(PotField.FieldSequence data) {
        if (!isServiceRunning()) throw new ServiceNotRunningException("Can't run fit since prediction service is not running.");

        PotField.FitRequest fitRequest = PotField.FitRequest.newBuilder().setFitData(data).build();
        PotField.FitResponse fitResponse = blockingStub.fit(fitRequest);

        return 0.0f; // Placeholder for error value
    }

    /**
     * Makes predictions using the trained model.
     *
     * @param data  The input data for making predictions.
     * @param steps The number of steps to predict into the future.
     * @return An array of prediction values.
     */
    public PotField.FieldSequence predict(PotField.FieldSequence data, int steps) {
        if (!isServiceRunning()) throw new ServiceNotRunningException("Can't run prediction since prediction service is not running.");
        
        PotField.PredictRequest predictRequest = PotField.PredictRequest.newBuilder().setField(data).setSteps(steps).build();
        PotField.PredictionResponse predictionResponse = blockingStub.predict(predictRequest);

        return predictionResponse.getPredictedData();
    }

    @Override
    public void close() {
        if (isServiceRunning()) try {
            stopService();
        } catch (InterruptedException err) {
            return; // TODO
        }
    }
}
