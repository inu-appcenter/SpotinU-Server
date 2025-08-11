package kr.inuappcenter.spotinu.domain.auth.exception;

import kr.inuappcenter.spotinu.global.exception.BaseErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AuthErrorCode implements BaseErrorCode {


  UNAUTHORIZED_400(HttpStatus.BAD_REQUEST, "계정이 비활성화 되었습니다."),
  UNAUTHORIZED_401(HttpStatus.BAD_REQUEST, "유저 인증 실패."),
  UNAUTHORIZED_402(HttpStatus.BAD_REQUEST, "유효성 검사 실패."),
  INVALID_NUMBER_FORMAT(HttpStatus.BAD_REQUEST, "학번 형식이 잘못되었습니다.")
  ;

  private final HttpStatus status;
  private final String message;

  AuthErrorCode(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }
}
