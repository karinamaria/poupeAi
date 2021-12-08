package br.com.poupeAi.handler;

import br.com.poupeAi.exception.ErrorDetail;
import br.com.poupeAi.exception.NegocioException;
import br.com.poupeAi.exception.ResourceNotFoundException;
import br.com.poupeAi.exception.ValidationErrorDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.joining;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handlerResourceNotFoundException(ResourceNotFoundException rnfException){
        ErrorDetail rnfDetails = ErrorDetail
                .builder()
                .timestamp(new Date().getTime())
                .status(HttpStatus.NOT_FOUND.value())
                .title("Resource not found")
                .detail(rnfException.getMessage())
                .developerMessage(rnfException.getClass().getName())
                .build();
        log.error("Resource not found");
        return new ResponseEntity<>(rnfDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handlerNegocioException(NegocioException rnfException){
        ErrorDetail rnfDetails = ErrorDetail
                .builder()
                .timestamp(new Date().getTime())
                .status(HttpStatus.CONFLICT.value())
                .title("Business rule error")
                .detail(rnfException.getMessage())
                .developerMessage(rnfException.getClass().getName())
                .build();
        log.error("Business rule error");
        return new ResponseEntity<>(rnfDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handlerMethodArgumentNotValidException(MethodArgumentNotValidException manveException){
        List<FieldError> fieldErrors = manveException.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(joining(","));
        String fieldMessages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(joining(","));
        ValidationErrorDetails rnfDetails = ValidationErrorDetails
                .builder()
                .timestamp(new Date().getTime())
                .status(HttpStatus.NOT_FOUND.value())
                .title("Field Validation Error")
                .detail("Field Validation Error")
                .developerMessage(manveException.getClass().getName())
                .field(fields)
                .fieldMessage(fieldMessages)
                .build();
        log.error("Field Validation Error");
        return new ResponseEntity<>(rnfDetails, HttpStatus.NOT_FOUND);
    }
}