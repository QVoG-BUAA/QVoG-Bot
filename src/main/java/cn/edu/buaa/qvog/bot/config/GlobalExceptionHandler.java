/*
 * Copyright (C) QVoG@BUAA 2024
 * Programmed by Tony S.
 */

package cn.edu.buaa.qvog.bot.config;

import cn.edu.buaa.qvog.bot.common.dto.MessageResponse;
import cn.edu.buaa.qvog.bot.common.utils.Requests;
import cn.edu.buaa.qvog.bot.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.resource.NoResourceFoundException;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<MessageResponse> handleBadRequest(BadRequestException e) {
        logError(HttpStatus.BAD_REQUEST, e);

        return ResponseEntity.badRequest().body(MessageResponse.badRequest(e.getMessage()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<MessageResponse> handleUnauthorized(UnauthorizedException e) {
        logError(HttpStatus.UNAUTHORIZED, e);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(MessageResponse.unauthorized(e.getMessage()));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<MessageResponse> handleNoResourceFound(NoResourceFoundException e) {
        logError(HttpStatus.NOT_FOUND, e);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).build();
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<MessageResponse> handleInternalServerError(InternalServerErrorException e) {
        logError(HttpStatus.INTERNAL_SERVER_ERROR, e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                MessageResponse.internalServerError(e.getMessage())
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<MessageResponse> handleNotFound(NotFoundException e) {
        logError(HttpStatus.NOT_FOUND, e);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                MessageResponse.notFound(e.getMessage())
        );
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<MessageResponse> handleForbidden(ForbiddenException e) {
        logError(HttpStatus.FORBIDDEN, e);

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                MessageResponse.forbidden(e.getMessage())
        );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<MessageResponse> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logError(HttpStatus.METHOD_NOT_ALLOWED, e);

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
                MessageResponse.methodNotAllowed(e.getMessage())
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logError(HttpStatus.BAD_REQUEST, e);

        var error = e.getBindingResult().getFieldError();
        if (error == null) {
            return ResponseEntity.badRequest().body(MessageResponse.badRequest("Validation failed"));
        }
        return ResponseEntity.badRequest().body(
                MessageResponse.badRequest(String.format("Field error %s: %s",
                        error.getField(), error.getDefaultMessage())));
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<MessageResponse> handleMissingPathVariableException(MissingPathVariableException e) {
        logError(HttpStatus.BAD_REQUEST, e);
        return ResponseEntity.badRequest().body(
                MessageResponse.badRequest("Missing path variable: " + e.getVariableName())
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<MessageResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        logError(HttpStatus.BAD_REQUEST, e);
        return ResponseEntity.badRequest().body(
                MessageResponse.badRequest("Invalid parameter: " + e.getName())
        );
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<MessageResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        logError(HttpStatus.BAD_REQUEST, e);
        return ResponseEntity.badRequest().body(
                MessageResponse.badRequest("Missing request parameter: " + e.getParameterName())
        );
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<MessageResponse> handleMissingServletRequestPartException(MissingServletRequestPartException e) {
        logError(HttpStatus.BAD_REQUEST, e);
        return ResponseEntity.badRequest().body(
                MessageResponse.badRequest("Missing request part: " + e.getRequestPartName())
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<MessageResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logError(HttpStatus.BAD_REQUEST, e);
        return ResponseEntity.badRequest().body(
                MessageResponse.badRequest("Invalid request body")
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageResponse> handleException(Exception e, WebRequest request) {
        logError(HttpStatus.INTERNAL_SERVER_ERROR, e);
        return ResponseEntity.badRequest().body(
                MessageResponse.badRequest("How dare you!")
        );
    }

    private void logError(HttpStatus status, Exception e) {
        if (e.getMessage() == null) {
            log.error("{} at {}", status, getRequestUrl(), e);
        } else {
            log.error("{} at {}: {}", status, getRequestUrl(), e.getMessage());
        }
    }

    private String getRequestUrl() {
        return Requests.getCurrentRequest().getRequestURI();
    }
}
