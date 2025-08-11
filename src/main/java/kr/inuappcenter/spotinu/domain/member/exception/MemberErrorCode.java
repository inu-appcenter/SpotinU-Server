package kr.inuappcenter.spotinu.domain.member.exception;

import kr.inuappcenter.spotinu.global.exception.BaseErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MemberErrorCode implements BaseErrorCode {

  USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 사용자 입니다."),
  DUPLICATE_STUDENT_NUMBER(HttpStatus.CONFLICT, "이미 존재하는 학번입니다.");

  private final HttpStatus status;
  private final String message;

  MemberErrorCode(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }
}
