package com.system.shop.exception;

import com.system.shop.common.Result;
import com.system.shop.common.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<Void> handleBusinessException(BusinessException e) {
        logger.error("Business exception: ", e);
        String message = messageSource.getMessage(e.getMessageKey(), e.getArgs(), e.getMessageKey(), LocaleContextHolder.getLocale());
        return Result.error(e.getCode(), message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error("Validation exception: ", e);
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String message = fieldErrors.stream()
                .map(error -> messageSource.getMessage(error.getDefaultMessage(), null, error.getDefaultMessage(), LocaleContextHolder.getLocale()))
                .collect(Collectors.joining(", "));
        return Result.error(ResultCode.VALIDATE_FAILED.getCode(), message);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<Void> handleBindException(BindException e) {
        logger.error("Bind exception: ", e);
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String message = fieldErrors.stream()
                .map(error -> messageSource.getMessage(error.getDefaultMessage(), null, error.getDefaultMessage(), LocaleContextHolder.getLocale()))
                .collect(Collectors.joining(", "));
        return Result.error(ResultCode.VALIDATE_FAILED.getCode(), message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<Void> handleConstraintViolationException(ConstraintViolationException e) {
        logger.error("Constraint violation exception: ", e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        String message = violations.stream()
                .map(violation -> messageSource.getMessage(violation.getMessage(), null, violation.getMessage(), LocaleContextHolder.getLocale()))
                .collect(Collectors.joining(", "));
        return Result.error(ResultCode.VALIDATE_FAILED.getCode(), message);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<Void> handleBadCredentialsException(BadCredentialsException e) {
        logger.error("Bad credentials exception: ", e);
        String message = messageSource.getMessage("user.password.error", null, "Invalid username or password", LocaleContextHolder.getLocale());
        return Result.error(ResultCode.UNAUTHORIZED.getCode(), message);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<Void> handleAccessDeniedException(AccessDeniedException e) {
        logger.error("Access denied exception: ", e);
        String message = messageSource.getMessage("permission.denied", null, "Permission denied", LocaleContextHolder.getLocale());
        return Result.error(ResultCode.FORBIDDEN.getCode(), message);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<Void> handleException(Exception e) {
        logger.error("Unexpected error: ", e);
        String message = messageSource.getMessage("common.error", null, "Operation failed", LocaleContextHolder.getLocale());
        return Result.error(ResultCode.ERROR.getCode(), message);
    }
} 