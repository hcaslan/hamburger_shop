package org.example.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType
{
  PASSWORDS_ARE_NOT_SAME(5003,"Sifreler ayni degil", HttpStatus.NOT_FOUND),
  USER_NOT_FOUND(5002,"Kullanici bulunamadi", HttpStatus.NOT_FOUND),
  WRONG_PASSWORD(5003,"Yanlis sifre", HttpStatus.NOT_FOUND),
  EMAIL_TAKEN(5004,"Email daha önce alınmis", HttpStatus.NOT_FOUND),
  INVALID_TOKEN(5005,"Gecersiz token", HttpStatus.UNAUTHORIZED),
  TOKEN_CREATION_FAILED(5006,"Token yaratılamadı", HttpStatus.INTERNAL_SERVER_ERROR),
  TOKEN_VERIFICATION_FAILED(5007,"Token doğrulanamadı", HttpStatus.UNAUTHORIZED),
  INVALID_TOKEN_ARGUMENT(5008,"Geçersiz token tipi", HttpStatus.BAD_REQUEST),
  BAD_REQUEST(5009,"Hatalı istek", HttpStatus.BAD_REQUEST),
  USER_NOT_ACTIVE(5010,"Kullanıcı aktif değil", HttpStatus.BAD_REQUEST),
  ACTIVATION_CODE_NOT_FOUND(5011,"Aktivasyon kodu bulunamadı", HttpStatus.NOT_FOUND),
  USER_ALREADY_ACTIVE(5012,"Kullanıcı zaten aktif", HttpStatus.BAD_REQUEST),
  USER_IS_BANNED(5013,"Kullanıcı yasaklanmış durumda, aktive edilemez", HttpStatus.BAD_REQUEST),
  USER_DELETED(5014,"Hesap silinmiş, aktive edilemez", HttpStatus.BAD_REQUEST),
  USER_ALREADY_DELETED(5015,"Kullanıcı zaten silinmiş", HttpStatus.BAD_REQUEST),
  ID_NOT_FOUND(5016,"Id bulunamadı", HttpStatus.NOT_FOUND),
  INVALID_ACTIVATION_CODE(5017,"Geçersiz aktivasyon kodu", HttpStatus.BAD_REQUEST),
  TOKEN_ALREADY_USED(5018,"Token zaten kullanılmış", HttpStatus.BAD_REQUEST),
  ADDRESS_NOT_FOUND(5019,"Adres bulunamadı", HttpStatus.BAD_REQUEST);


  private Integer code;
  private String message;
  private HttpStatus status;
}
