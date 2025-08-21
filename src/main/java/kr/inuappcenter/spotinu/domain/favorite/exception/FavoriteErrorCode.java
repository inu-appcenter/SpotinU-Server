package kr.inuappcenter.spotinu.domain.favorite.exception;

import kr.inuappcenter.spotinu.global.exception.BaseErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum FavoriteErrorCode implements BaseErrorCode {

  FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "즐겨찾기 접근 권한 없음"),
  ADD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "즐겨찾기 추가 실패"),
  DELETE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "즐겨찾기 삭제 실패"),
  NOT_FOUND(HttpStatus.NOT_FOUND, "즐겨찾기 정보가 없습니다");

  private final HttpStatus status;
  private final String message;

  FavoriteErrorCode(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }
}
