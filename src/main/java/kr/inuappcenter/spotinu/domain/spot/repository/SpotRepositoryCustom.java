package kr.inuappcenter.spotinu.domain.spot.repository;

import kr.inuappcenter.spotinu.domain.spot.entity.PlaceType;
import kr.inuappcenter.spotinu.domain.spot.entity.Spot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 커스텀 쿼리 메서드
 * JPA 기본 메서드 외에, 복잡한 검색 조건이 있는 쿼리
 */
public interface SpotRepositoryCustom {
  Page<Spot> findAllWithPhotos(Pageable pageable);
  Page<Spot> searchSpots(Boolean sleepingAllowed,
                         Boolean eatingAllowed,
                         Boolean hasPowerOutlet,
                         Boolean studyAllowed,
                         Boolean isEntertainment,
                         Boolean isReservationRequired,
                         PlaceType placeType,
                         Pageable pageable
  );
}
