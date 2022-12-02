package com.book.booksaleservice.common.exception;

import com.book.booksaleservice.common.dto.response.ErrorResponseDTO;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvisor {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponseDTO> handleBusinessException(final BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        return new ResponseEntity<>(
                new ErrorResponseDTO(errorCode),
                HttpStatusCode.valueOf(errorCode.getStatus())
        );
    }
}
