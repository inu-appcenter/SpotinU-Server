package kr.inuappcenter.spotinu.domain.favorite.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class FavoriteDetailResponse {

  private Long id;  // spot Id
  private String name;  // 장소 이름
  private String locationDetail;  // XX호관 XX호
  private String businessHours; // 운영 시간
  private String memo;  // 사용자 입력 메모

  private String url; // Spot Photo의 thumbnail
}
