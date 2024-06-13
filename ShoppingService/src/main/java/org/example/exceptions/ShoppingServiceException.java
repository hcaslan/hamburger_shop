package org.example.exceptions;

import lombok.Getter;


@Getter


public class ShoppingServiceException extends RuntimeException
{
    private ErrorType errorType;

    public ShoppingServiceException(ErrorType errorType)
    {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public ShoppingServiceException(ErrorType errorType, String customMessage)
    {
        super(customMessage);
        this.errorType = errorType;
    }
}
