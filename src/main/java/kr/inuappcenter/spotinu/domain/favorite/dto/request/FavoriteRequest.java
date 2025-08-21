package kr.inuappcenter.spotinu.domain.favorite.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "즐겨찾기 생성 요청 DTO")
public class FavoriteRequest {
  @Schema(description = "메모", example = "가끔 혼밥하기 좋음.")
  private String memo;
}
