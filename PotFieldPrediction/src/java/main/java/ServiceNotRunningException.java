/**
 * Exception raised if the prediction service could not be started.
 */
public class ServiceNotRunningException extends RuntimeException {
    public ServiceNotRunningException(String errorMessage) {
        super(errorMessage);
    }
}
