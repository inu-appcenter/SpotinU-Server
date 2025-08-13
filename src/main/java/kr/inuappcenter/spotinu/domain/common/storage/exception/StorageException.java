package kr.inuappcenter.spotinu.domain.common.storage.exception;


import kr.inuappcenter.spotinu.global.exception.CustomException;

/**
 * 유저(Member) 관련 예외를 처리하는 클래스.
 * 모든 Member 관련 예외는 이 클래스를 통해 던집니다.
 */
public class StorageException extends CustomException {

  public StorageException(StorageErrorCode errorCode) {
    super(errorCode);
  }

  public StorageException(StorageErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }
}
