package com.atria.billingservice.exception;

import com.atria.billingservice.constant.ErrorCode;
import com.atria.billingservice.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler  extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

        validationErrorList.forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String validationMsg = error.getDefaultMessage();
            validationErrors.put(fieldName, validationMsg);
        });
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FolioNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleFolioNotFound(FolioNotFoundException ex, WebRequest webRequest) {
        return buildResponse(ex, webRequest, ErrorCode.FOLIO_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateChargeException.class)
    public ResponseEntity<ErrorResponseDto> handleDuplicateCharge(DuplicateChargeException ex, WebRequest webRequest) {
        return buildResponse(ex, webRequest, ErrorCode.DUPLICATE_CHARGE, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DuplicateTransactionException.class)
    public ResponseEntity<ErrorResponseDto> handleDuplicateCharge(DuplicateTransactionException ex, WebRequest webRequest) {
        return buildResponse(ex, webRequest, ErrorCode.DUPLICATE_TRANSACTION_ID, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidFolioStateException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidState(InvalidFolioStateException ex, WebRequest webRequest) {
        return buildResponse(ex, webRequest, ErrorCode.INVALID_FOLIO_STATE, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PaymentFailedException.class)
    public ResponseEntity<ErrorResponseDto> handlePaymentFailed(PaymentFailedException ex, WebRequest webRequest) {
        return buildResponse(ex, webRequest, ErrorCode.PAYMENT_FAILED, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedTenantAccessException.class)
    public ResponseEntity<ErrorResponseDto> handleUnauthorized(UnauthorizedTenantAccessException ex, WebRequest webRequest) {
        return buildResponse(ex, webRequest, ErrorCode.UNAUTHORIZED_TENANT, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGeneric(Exception ex, WebRequest webRequest) {
        return buildResponse(ex, webRequest, ErrorCode.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponseDto> buildResponse(Exception ex, WebRequest webRequest, ErrorCode errorCode, HttpStatus httpStatus) {
        return new ResponseEntity<>(
                new ErrorResponseDto(webRequest.getDescription(false),
                        errorCode.toString(),
                        ex),
                httpStatus
        );
    }
}