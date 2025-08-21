package kr.inuappcenter.spotinu.domain.favorite.exception;

import kr.inuappcenter.spotinu.global.exception.CustomException;

/**
 * 즐겨찾기(Favorite) 관련 예외를 처리하는 클래스.
 * 모든 Favorite 관련 예외는 이 클래스를 통해 던집니다.
 */
public class FavoriteException extends CustomException {

  private final FavoriteErrorCode errorCode;

  public FavoriteException(FavoriteErrorCode errorCode) {
    super(errorCode);
    this.errorCode = errorCode;
  }

  public FavoriteException(FavoriteErrorCode errorCode, Throwable cause) {
    super(errorCode);
    this.errorCode = errorCode;
    initCause(cause);
  }
}
