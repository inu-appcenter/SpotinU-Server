package kr.inuappcenter.spotinu.domain.spot.dto.response;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SpotResponse {

  private Long id;
  private String x;
  private String y;
  private String name;
  private String locationDetail;
  private String description;
  private String businessHours;
  private List<SpotPhotoResponse> photos;

}
