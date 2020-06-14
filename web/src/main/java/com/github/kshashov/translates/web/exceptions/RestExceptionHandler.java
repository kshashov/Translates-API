package com.github.kshashov.translates.web.exceptions;

import com.github.kshashov.translates.common.errors.BadRequestException;
import com.github.kshashov.translates.common.errors.NoPermissionException;
import com.github.kshashov.translates.common.errors.NotFoundException;
import com.github.kshashov.translates.common.errors.TranslatesException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({AccessDeniedException.class, NoPermissionException.class})
    protected ResponseEntity<ErrorResponse> handleAccessDeniedException(RuntimeException ex, WebRequest request) {
        log.error("", ex);

        String message = "You have no permissions to perform this action";

        if (ex instanceof NoPermissionException) {
            message = ex.getMessage();
        }

        return new ResponseEntity<>(
                new ErrorResponse(message),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex, WebRequest request) {
        log.error("", ex);

        return new ResponseEntity<>(
                new ErrorResponse(ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleBadRequestException(NotFoundException ex, WebRequest request) {
        log.error("", ex);

        return new ResponseEntity<>(
                new ErrorResponse(ex.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TranslatesException.class)
    protected ResponseEntity<ErrorResponse> handleTranslatesException(TranslatesException ex, WebRequest request) {
        log.error("", ex);

        return new ResponseEntity<>(
                new ErrorResponse(ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Throwable.class)
    protected ResponseEntity<ErrorResponse> handleExceptions(Throwable ex, WebRequest request) {
        log.error("", ex);
        return new ResponseEntity<>(
                new ErrorResponse("An internal error occurred"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({
            MissingServletRequestParameterException.class,
            ServletRequestBindingException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentNotValidException.class,
            MissingServletRequestPartException.class,
            BindException.class
    })
    public final ResponseEntity<ErrorResponse> handleBadRequestSpringExceptions(Exception ex, WebRequest request) throws Exception {
        log.error("", ex);

        String message = "Bad request";

        if (ex instanceof MethodArgumentNotValidException) {
            message = ex.getMessage();
        }

        return new ResponseEntity<>(
                new ErrorResponse(message),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpMediaTypeNotAcceptableException.class,
            NoHandlerFoundException.class
    })
    public final ResponseEntity<ErrorResponse> handleMVCSpringExceptions(Exception ex, WebRequest request) throws Exception {
        log.error("", ex);

        if (ex instanceof HttpRequestMethodNotSupportedException) {
            return new ResponseEntity<>(
                    new ErrorResponse("Method not supported"),
                    HttpStatus.METHOD_NOT_ALLOWED);
        } else if (ex instanceof HttpMediaTypeNotSupportedException) {
            return new ResponseEntity<>(
                    new ErrorResponse("Unsupported media type"),
                    HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        } else if (ex instanceof NoHandlerFoundException) {
            return new ResponseEntity<>(
                    new ErrorResponse("Resource not found"),
                    HttpStatus.NOT_FOUND);
        } else {
            // HttpMediaTypeNotAcceptableException
            return new ResponseEntity<>(
                    new ErrorResponse("Media type not acceptable"),
                    HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
