package kr.inuappcenter.spotinu.domain.spot.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.inuappcenter.spotinu.domain.spot.entity.PlaceType;
import kr.inuappcenter.spotinu.domain.spot.entity.QSpot;
import kr.inuappcenter.spotinu.domain.spot.entity.Spot;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class SpotRepositoryImpl implements SpotRepositoryCustom {

  private final JPAQueryFactory queryFactory;
  private final QSpot spot = QSpot.spot;

  @Override
  public Page<Spot> searchSpots(Boolean sleepingAllowed,
                                Boolean eatingAllowed,
                                Boolean hasPowerOutlet,
                                Boolean studyAllowed,
                                Boolean isEntertainment,
                                Boolean isReservationRequired,
                                PlaceType placeType,
                                Pageable pageable) {

    List<Spot> results = queryFactory
      .selectFrom(spot)
      .where(
        sleepingAllowedEq(sleepingAllowed),
        eatingAllowedEq(eatingAllowed),
        hasPowerOutletEq(hasPowerOutlet),
        studyAllowedEq(studyAllowed),
        isEntertainmentEq(isEntertainment),
        isReservationRequiredEq(isReservationRequired),
        placeTypeEq(placeType)
      )
      .offset(pageable.getOffset())
      .limit(pageable.getPageSize())
      .fetch();

    Long total = queryFactory
      .select(spot.count())
      .from(spot)
      .where(
        sleepingAllowedEq(sleepingAllowed),
        eatingAllowedEq(eatingAllowed),
        hasPowerOutletEq(hasPowerOutlet),
        studyAllowedEq(studyAllowed),
        isEntertainmentEq(isEntertainment),
        isReservationRequiredEq(isReservationRequired),
        placeTypeEq(placeType)
      )
      .fetchOne();

    // total이 null일 수 있으므로 방어 코드 추가
    long totalCount = total != null ? total : 0L;

    return new PageImpl<>(results, pageable, totalCount);
  }

  /** 조건 메서드들 */
  private BooleanExpression sleepingAllowedEq(Boolean sleepingAllowed) {
    return sleepingAllowed == null ? null : spot.sleepingAllowed.eq(sleepingAllowed);
  }

  private BooleanExpression eatingAllowedEq(Boolean eatingAllowed) {
    return eatingAllowed == null ? null : spot.eatingAllowed.eq(eatingAllowed);
  }

  private BooleanExpression hasPowerOutletEq(Boolean hasPowerOutlet) {
    return hasPowerOutlet == null ? null : spot.hasPowerOutlet.eq(hasPowerOutlet);
  }

  private BooleanExpression studyAllowedEq(Boolean studyAllowed) {
    return studyAllowed == null ? null : spot.studyAllowed.eq(studyAllowed);
  }

  private BooleanExpression isEntertainmentEq(Boolean isEntertainment) {
    return isEntertainment == null ? null : spot.entertainment.eq(isEntertainment);
  }

  private BooleanExpression isReservationRequiredEq(Boolean isReservationRequired) {
    return isReservationRequired == null ? null : spot.reservationRequired.eq(isReservationRequired);
  }

  private BooleanExpression placeTypeEq(PlaceType placeType) {
    return placeType == null ? null : spot.placeType.eq(placeType);
  }
}
