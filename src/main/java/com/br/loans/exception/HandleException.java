package com.br.loans.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class HandleException {

    private final MessageSource messageSource;
    private static final Logger log = LogManager.getLogger(HandleException.class);
    private final static String PREFIX_MESSAGE = "The field %s %s";

    public HandleException(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResponseEntity<List<ProblemDetail>> handlingException(final BindException exception) {
        final var fieldsError = exception.getBindingResult().getFieldErrors();

        return ResponseEntity.status(BAD_REQUEST)
                .body(
                        fieldsError.stream().map(fieldError -> {
                            var message = this.messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
                            log.error("Error {}", message);
                            return ProblemDetail.forStatusAndDetail(BAD_REQUEST, String.format(PREFIX_MESSAGE, fieldError.getField(), message));
                        }).collect(Collectors.toList()));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ProblemDetail> handlingException(final NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage()));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handlingException(final Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno"));
    }
}
