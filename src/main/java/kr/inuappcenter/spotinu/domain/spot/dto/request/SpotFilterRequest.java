package kr.inuappcenter.spotinu.domain.spot.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.inuappcenter.spotinu.domain.spot.entity.PlaceType;
import lombok.Getter;

@Getter
@Schema(description = "장소 검색 DTO")
public class SpotFilterRequest {
  @Schema(description = "취침 가능 여부", example = "true")
  private Boolean sleepingAllowed;

  @Schema(description = "취식 가능 여부", example = "false")
  private Boolean eatingAllowed;

  @Schema(description = "콘센트 유무", example = "true")
  private Boolean hasPowerOutlet;

  @Schema(description = "개인 공부 가능 여부", example = "true")
  private Boolean studyAllowed;

  @Schema(description = "오락시설 여부", example = "false")
  private Boolean entertainment;

  @Schema(description = "예약 필요 여부", example = "false")
  private Boolean reservationRequired;

  @Schema(description = "장소 유형(INDOOR/OUTDOOR)", example = "INDOOR")
  @Enumerated(EnumType.STRING)
  private PlaceType placeType;
}
