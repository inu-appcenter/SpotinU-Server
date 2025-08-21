package kr.inuappcenter.spotinu.domain.spot.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import kr.inuappcenter.spotinu.domain.spot.entity.PlaceType;
import lombok.Getter;

@Getter
@Schema(description = "장소 등록 DTO")
public class SpotCreateRequest {

  @Schema(description = "장소 위도", example = "1")
  @NotBlank(message = "장소 위도는 필수 입력 값입니다.")
  private String latitude;

  @Schema(description = "장소 경도", example = "1")
  @NotBlank(message = "장소 경도는 필수 입력 값입니다.")
  private String longitude;

  @Schema(description = "장소 이름", example = "이룸관")
  @NotBlank(message = "이름은 필수 입력 값입니다.")
  private String name;

  @Schema(description = "XXX호관 XXX호", example = "106호관 101호")
  @NotBlank(message = "장소 설명 디테일은 필수 입력 값입니다.")
  private String locationDetail;  // XX호관 XXX호

  @Schema(description = "간단한 장소 설명", example = "혼자 공부하기 좋고 쾌적한 공간띠")
  @NotBlank(message = "간단한 소개는 필수 입력 값입니다.")
  private String description; // 간단한 소개

  @Schema(description = "운영 시간", example = "1800 ~ 0600")
  @NotBlank(message = "운영 시간은 필수 입력 값입니다.")
  private String businessHours; // 운영 시간


  @Schema(description = "취침 가능 여부", example = "true")
  private boolean sleepingAllowed;

  @Schema(description = "취식 가능 여부", example = "false")
  private boolean eatingAllowed;

  @Schema(description = "콘센트 유무", example = "true")
  private boolean hasPowerOutlet;

  @Schema(description = "개인 공부 가능 여부", example = "true")
  private boolean studyAllowed;

  @Schema(description = "오락시설 여부", example = "false")
  private boolean entertainment;

  @Schema(description = "예약 필요 여부", example = "false")
  private boolean reservationRequired;

  @Schema(description = "장소 유형(INDOOR/OUTDOOR)", example = "INDOOR")
  @Enumerated(EnumType.STRING)
  private PlaceType placeType;

  @Schema(description = "운영 시간 세부 내용", example = "휴무일 없음, 시험기간 연장 운영")
  private String businessHoursDetail;

  @Schema(description = "장소 상세 설명", example = "책상과 콘센트가 많아 단체 공부에도 적합")
  private String descriptionDetail;
}
