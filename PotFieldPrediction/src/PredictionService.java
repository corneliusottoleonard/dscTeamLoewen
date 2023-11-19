import java.io.IOException;
import java.util.logging.Logger;

/**
 * A service for managing predictions using a machine learning model.
 */
public class PredictionService implements AutoCloseable {

    private Process process;
    private ProcessHandle processHandle;
    private final String[] PROCESS_COMMAND = {"python3", "./python/run.py"}; // TODO: replace by actual script
    private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    /**
     * Starts the prediction service.
     */
    public void startService() throws IOException, ServiceNotRunningException {
        process = new ProcessBuilder(PROCESS_COMMAND).inheritIO().start();
        processHandle = process.toHandle();
        if (!processHandle.isAlive()) throw new ServiceNotRunningException("Unable to start prediction service.");
        logger.info("Prediction Service started with PID " + processHandle.pid());
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
    public float fit(Object[] data) {
        if (!isServiceRunning()) throw new ServiceNotRunningException("Can't run fit since prediction service is not running.");
        // Add logic for fitting the model
        return 0.0f; // Placeholder for error value
    }

    /**
     * Makes predictions using the trained model.
     *
     * @param data  The input data for making predictions.
     * @param steps The number of steps to predict into the future.
     * @return An array of prediction values.
     */
    public Object[] predict(Object[] data, int steps) {
        if (!isServiceRunning()) throw new ServiceNotRunningException("Can't run prediction since prediction service is not running.");
        // Add logic for making predictions
        return new Object[steps]; // Placeholder for prediction values
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
