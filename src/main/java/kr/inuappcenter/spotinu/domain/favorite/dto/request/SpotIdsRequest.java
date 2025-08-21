package kr.inuappcenter.spotinu.domain.favorite.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "구역에 해당하는 SpotId 요청 DTO")
public class SpotIdsRequest {
  @Schema(description = "Spot PK List")
  private List<Long> spotIds;
}
