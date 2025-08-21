package kr.inuappcenter.spotinu.domain.favorite.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class FavoriteResponse {

  private Long id;
  private String latitude;  // 위도
  private String longitude; // 경도
}
