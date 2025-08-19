package kr.inuappcenter.spotinu.domain.review.exception;

import kr.inuappcenter.spotinu.global.exception.BaseErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ReviewErrorCode implements BaseErrorCode {

  NOT_OWNER(HttpStatus.FORBIDDEN, "본인이 작성한 후기가 아닙니다."),
  REVIEW_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 후기 입니다.");

  private final HttpStatus status;
  private final String message;

  ReviewErrorCode(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }
}
