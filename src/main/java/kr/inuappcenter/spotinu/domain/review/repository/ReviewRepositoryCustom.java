package kr.inuappcenter.spotinu.domain.review.repository;

import kr.inuappcenter.spotinu.domain.review.entity.Review;

/**
 * 커스텀 쿼리 메서드
 * JPA 기본 메서드 외에, 복잡한 검색 조건이 있는 쿼리
 */
public interface ReviewRepositoryCustom {
  Review findByIdWithPhotos(Long id);
}
