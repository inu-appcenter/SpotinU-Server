package kr.inuappcenter.spotinu.domain.spot.dto.response;

import kr.inuappcenter.spotinu.domain.review.dto.response.ReviewResponse;
import kr.inuappcenter.spotinu.domain.spot.entity.PlaceType;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SpotDetailResponse {

  private Long id;
  private String name;
  private String locationDetail;
  private String description;
  private String businessHours;
  private boolean sleepingAllowed;  // 취침
  private boolean eatingAllowed;  // 취식
  private boolean hasPowerOutlet; // 콘센트
  private boolean studyAllowed; // 개인 공부
  private boolean entertainment;  // 오락시설
  private boolean reservationRequired;  // 예약제
  private PlaceType placeType;  // 실내/야외
  private List<SpotPhotoResponse> photos;
  private String businessHoursDetail;
  private String descriptionDetail;
  @Setter
  private List<ReviewResponse> reviews;
}
