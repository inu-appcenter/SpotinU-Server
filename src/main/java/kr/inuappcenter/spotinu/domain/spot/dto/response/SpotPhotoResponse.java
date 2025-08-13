package kr.inuappcenter.spotinu.domain.spot.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SpotPhotoResponse {

  private Long id;
  private String url;
  private boolean thumbnail;
  private int orderIndex;
}
