package org.example.exceptions;

// Bu sınıf tüm controller sınıfları için merkezi bir şekilde hata yönetimi sağlayacaktır.

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(InventoryMicroServiceException.class)
    public ResponseEntity<ErrorMessage> handleDemoException(InventoryMicroServiceException ex){
        ErrorType errorType = ex.getErrorType();
        return new ResponseEntity(createErrorMessage(ex),errorType.getStatus());
    }

    private ErrorMessage createErrorMessage(InventoryMicroServiceException ex)
    {
       return ErrorMessage.builder().code(ex.getErrorType().getCode()).message(ex.getMessage()).build();
    }


    @ExceptionHandler(RuntimeException.class) // Hata yakalayici bir metod olduğunu belirtmek için.
    public ResponseEntity<String> handleException(RuntimeException ex){
       return ResponseEntity.badRequest().body(ex.getMessage());
    }

//    @ExceptionHandler(AuthMicroServiceException.class)
//    public final ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(
//            MethodArgumentNotValidException exception) {
//
//        ErrorType errorType = ErrorType.BAD_REQUEST;
//        List<String> fields = new ArrayList<>();
//        exception
//                .getBindingResult()
//                .getFieldErrors()
//                .forEach(e -> fields.add(e.getField() + ": " + e.getDefaultMessage()));
//        ErrorMessage errorMessage = createErrorMessage(exception);
//        errorMessage.setFields(fields);
//        return new ResponseEntity<>(errorMessage,
//                errorType.getStatus());
//    }
}
