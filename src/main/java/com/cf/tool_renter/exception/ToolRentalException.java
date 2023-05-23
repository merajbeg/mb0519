package com.cf.tool_renter.exception;

public class ToolRentalException extends RuntimeException {

    public ToolRentalException(String message) {
        super(message);
    }

    public ToolRentalException(String message, Throwable cause) {
        super(message, cause);
    }
}
