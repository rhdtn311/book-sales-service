package com.book.booksaleservice.common.dto.response;

import com.book.booksaleservice.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class ErrorResponseDTO extends ResponseDTO {

    public ErrorResponseDTO(ErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage());
    }

    public ErrorResponseDTO(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
    }
}
