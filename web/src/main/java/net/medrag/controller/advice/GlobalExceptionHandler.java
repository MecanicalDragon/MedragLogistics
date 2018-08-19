package net.medrag.controller.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * Global exception handler
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * We handle any exception!!!
     *
     * @param ex - exception, meant to be handled
     * @return - error page
     */
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex) {
        LOGGER.error("Exception happened: ", ex);
        return "public/error";
    }

}

