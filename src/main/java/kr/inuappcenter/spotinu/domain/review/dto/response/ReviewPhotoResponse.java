package kr.inuappcenter.spotinu.domain.review.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ReviewPhotoResponse {

  Long id;
  private String url;
  private boolean thumbnail;
  private int orderIndex;
}
