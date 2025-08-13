package kr.inuappcenter.spotinu.domain.common.storage.exception;

import kr.inuappcenter.spotinu.global.exception.BaseErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum StorageErrorCode implements BaseErrorCode {

  S3_DELETE_FAIL(HttpStatus.BAD_REQUEST, "삭제 실패."),
  S3_UPLOAD_FAIL(HttpStatus.BAD_REQUEST, "업로드 실패.");

  private final HttpStatus status;
  private final String message;

  StorageErrorCode(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }
}
