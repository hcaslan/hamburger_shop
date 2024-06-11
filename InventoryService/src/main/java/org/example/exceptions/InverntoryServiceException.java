package org.example.exceptions;

import lombok.Getter;


@Getter


public class InverntoryServiceException extends RuntimeException
{
    private ErrorType errorType;

    public InverntoryServiceException(ErrorType errorType)
    {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public InverntoryServiceException(ErrorType errorType, String customMessage)
    {
        super(customMessage);
        this.errorType = errorType;
    }
}
