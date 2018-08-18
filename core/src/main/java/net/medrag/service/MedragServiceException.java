package net.medrag.service;

/**
 * Intercepting exception of service tier. Being thrown in service tier exception case.
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public class MedragServiceException extends Exception {

    public MedragServiceException(String message) {
        super(message);
    }

    public MedragServiceException(Throwable cause) {
        super(cause);
    }

    public MedragServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
