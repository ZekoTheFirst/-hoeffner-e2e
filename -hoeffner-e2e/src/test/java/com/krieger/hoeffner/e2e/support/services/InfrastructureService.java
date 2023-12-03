package com.krieger.hoeffner.e2e.support.services;

public interface InfrastructureService {

    /**
     * Checks the status of a service throwing an exception with details if unhealthy.
     */
    boolean isHealthy() throws UnhealthyException;

    class UnhealthyException extends RuntimeException {
        public UnhealthyException(String message) {
            super(message);
        }

        public UnhealthyException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
