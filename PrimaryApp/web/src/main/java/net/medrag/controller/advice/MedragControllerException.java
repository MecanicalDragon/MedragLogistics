package net.medrag.controller.advice;

/**
 * Wrapping exception of controller class
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public class MedragControllerException extends Exception {

    public MedragControllerException() {
    }

    public MedragControllerException(String message) {
        super(message);
    }

    public MedragControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public MedragControllerException(Throwable cause) {
        super(cause);
    }

}
