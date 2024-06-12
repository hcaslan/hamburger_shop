package org.example.exceptions;

import lombok.Getter;


@Getter


public class InventoryMicroServiceException extends RuntimeException
{
    private ErrorType errorType;

    public InventoryMicroServiceException(ErrorType errorType)
    {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public InventoryMicroServiceException(ErrorType errorType, String customMessage)
    {
        super(customMessage);
        this.errorType = errorType;
    }
}
