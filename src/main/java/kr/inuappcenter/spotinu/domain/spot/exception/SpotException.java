package kr.inuappcenter.spotinu.domain.spot.exception;

import kr.inuappcenter.spotinu.global.exception.CustomException;

/**
 * 장소(Spot) 관련 예외를 처리하는 클래스.
 * 모든 Spot 관련 예외는 이 클래스를 통해 던집니다.
 */
public class SpotException extends CustomException {

  public SpotException(SpotErrorCode errorCode) {
    super(errorCode);
  }
}
