package kr.inuappcenter.spotinu.global.exception;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 *
 * 모든 커스텀 예외의 상위 클래스
 */
@Getter
public class CustomException extends RuntimeException {
  private final BaseErrorCode baseErrorCode;
  private final LocalDateTime timestamp;

  public CustomException(BaseErrorCode baseErrorCode) {
    super(baseErrorCode.getMessage()); // 메시지는 자동 설정
    this.baseErrorCode = baseErrorCode;
    this.timestamp = LocalDateTime.now();
  }

  public CustomException(BaseErrorCode baseErrorCode, Throwable cause) {
    super(baseErrorCode.getMessage(), cause);
    this.baseErrorCode = baseErrorCode;
    this.timestamp = LocalDateTime.now();
  }

  public BaseErrorCode getErrorCode() { // 편의 메서드
    return this.baseErrorCode;
  }
}
