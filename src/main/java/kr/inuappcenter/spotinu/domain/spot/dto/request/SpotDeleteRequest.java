package kr.inuappcenter.spotinu.domain.spot.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(description = "장소 삭제 DTO")
public class SpotDeleteRequest {

  @Schema(description = "장소 id", example = "9")
  @NotNull(message = "장소 PK 입력은 필수입니다.")
  Long id;
}
