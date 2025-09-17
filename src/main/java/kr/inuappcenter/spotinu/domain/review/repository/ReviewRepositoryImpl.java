package kr.inuappcenter.spotinu.domain.review.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.inuappcenter.spotinu.domain.review.entity.QReview;
import kr.inuappcenter.spotinu.domain.review.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

  private final JPAQueryFactory queryFactory;
  private final QReview review = QReview.review;

  @Override
  public Review findByIdWithPhotos(Long id) {
    return queryFactory
      .selectFrom(review)
      .leftJoin(review.photos).fetchJoin()
      .where(review.id.eq(id))
      .fetchOne();
  }
}
