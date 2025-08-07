package kr.inuappcenter.spotinu.domain.spot.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 간단하게 장소들 있는곳에 배치될 엔티티
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Spot {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String locationDetail;  // XX호관 XXX호

  private String description; // 간단한 소개

  private String businessHours; // 운영 시간

  private String thumbnailUrl;

  // Filter
  private boolean sleepingAllowed;  // 취침
  private boolean eatingAllowed;  // 취식
  private boolean hasPowerOutlet; // 콘센트
  private boolean studyAllowed; // 개인 공부
  private boolean isEntertainment;  // 오락시설
  private boolean isReservationRequired;  // 예약제

  @Enumerated(EnumType.STRING)
  private PlaceType placeType;  // 실내/야외

  // SpotPhoto

}
