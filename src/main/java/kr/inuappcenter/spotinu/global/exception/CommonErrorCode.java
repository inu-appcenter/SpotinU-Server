package kr.inuappcenter.spotinu.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 공통(Common) 관련 예외를 처리하는 클래스.
 * 모든 Common 관련 예외는 이 클래스를 통해 던집니다.
 */
@Getter
public enum CommonErrorCode implements BaseErrorCode {
  PAYLOAD_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE, "파일 크기가 너무 큽니다."),
  INVALID_INPUT(HttpStatus.BAD_REQUEST, "유효성 검사 실패"),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");

  private final HttpStatus status;
  private final String message;

  CommonErrorCode(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }
}

