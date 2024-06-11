package org.example.exceptions;

import lombok.Getter;


@Getter


public class ProfileMicroServiceException extends RuntimeException
{
    private ErrorType errorType;

    public ProfileMicroServiceException(ErrorType errorType)
    {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public ProfileMicroServiceException(ErrorType errorType, String customMessage)
    {
        super(customMessage);
        this.errorType = errorType;
    }
}
