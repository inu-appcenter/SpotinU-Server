package kr.inuappcenter.spotinu.domain.spot.exception;

import kr.inuappcenter.spotinu.global.exception.BaseErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SpotErrorCode implements BaseErrorCode {

  SPOT_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 장소 입니다.");

  private final HttpStatus status;
  private final String message;

  SpotErrorCode(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }
}
