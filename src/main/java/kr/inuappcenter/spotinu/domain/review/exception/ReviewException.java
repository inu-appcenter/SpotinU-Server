package kr.inuappcenter.spotinu.domain.review.exception;

import kr.inuappcenter.spotinu.global.exception.CustomException;

/**
 * 후기(Review) 관련 예외를 처리하는 클래스.
 * 모든 Review 관련 예외는 이 클래스를 통해 던집니다.
 */
public class ReviewException extends CustomException {

  public ReviewException(ReviewErrorCode errorCode) {
    super(errorCode);
  }
}
