package kr.inuappcenter.spotinu.domain.spot.dto.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.inuappcenter.spotinu.domain.spot.entity.PlaceType;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SpotResponse {

  private Long id;
  private String latitude;
  private String longitude;
  private String name;
  private String locationDetail;
  private String description;
  private String photo;

  private boolean sleepingAllowed;  // 취침
  private boolean eatingAllowed;  // 취식
  private boolean hasPowerOutlet; // 콘센트
  private boolean studyAllowed; // 개인 공부
  private boolean entertainment;  // 오락시설
  private boolean reservationRequired;  // 예약제

  @Enumerated(EnumType.STRING)
  private PlaceType placeType;  // 실내/야외


}
