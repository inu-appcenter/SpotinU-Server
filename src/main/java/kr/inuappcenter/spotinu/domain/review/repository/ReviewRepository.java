package kr.inuappcenter.spotinu.domain.review.repository;

import kr.inuappcenter.spotinu.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
  Page<Review> findByMemberId(Long memberId, Pageable pageable);
  Page<Review> findBySpotId(Long spotId, Pageable pageable);
}
