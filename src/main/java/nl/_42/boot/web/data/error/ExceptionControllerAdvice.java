package nl._42.boot.web.data.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Advice for handling exceptions in controllers.
 */
@Slf4j
@ResponseBody
@ControllerAdvice
public class ExceptionControllerAdvice {

    public static final String SERVER_NOT_FOUND_ERROR = "SERVER.NOT_FOUND_ERROR";
    public static final String SERVER_GENERIC_ERROR = "SERVER.GENERIC_ERROR";

    /**
     * Handles HttpRequestMethodNotSupportedException, which is thrown when an HTTP request with an unsupported request method is performed.
     * For example when doing a POST to a GET endpoint.
     * @return GenericErrorResult containing an error code.
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public GenericErrorResult handleRequestMethodNotSupported() {
        return new GenericErrorResult(SERVER_NOT_FOUND_ERROR);
    }

    /**
     * Handles MissingServletRequestParameterException, which is thrown when a required servlet request parameter is not given.
     * @param e MissingServletRequestParameterException
     * @return ErrorResult containing an error code.
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResult handleAMissingServletRequestParam(MissingServletRequestParameterException e) {
        return new ErrorResult(e.getMessage());
    }

    /**
     * Handles MethodArgumentNotValidException, which is thrown when validation on an argument annotated with {@code @Valid} fails.
     * @param ex MethodArgumentNotValidException
     * @return ErrorResult containing field and/or global errors.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResult handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        return new ErrorResult(ex.getBindingResult());
    }
   
    /**
     * Handles BindException, which is thrown when binding errors are considered fatal.
     * @param ex BindException
     * @return ErrorResult containing field and/or global errors.
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResult handleMethodArgumentBindException(BindException ex) {
        return new ErrorResult(ex.getBindingResult());
    }
    
    /**
     * Fallback handler for all Exception classes that do not have their own exception handling method.
     * @param handler Handler
     * @param ex Exception
     * @return GenericErrorResult containing an error code.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public GenericErrorResult handleOtherExceptions(Object handler, Exception ex) {
        log.error("Handling request, for [" + handler + "], resulted in the following exception.", ex);
        return new GenericErrorResult(SERVER_GENERIC_ERROR);
    }

}
