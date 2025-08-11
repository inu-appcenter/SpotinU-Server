package kr.inuappcenter.spotinu.domain.auth.exception;

import kr.inuappcenter.spotinu.global.exception.CustomException;

/**
 * 인증(Auth) 관련 예외를 처리하는 클래스.
 * 모든 Auth 관련 예외는 이 클래스를 통해 던집니다.
 */
public class AuthException extends CustomException {

  public AuthException(AuthErrorCode errorCode) {
    super(errorCode);
  }
}