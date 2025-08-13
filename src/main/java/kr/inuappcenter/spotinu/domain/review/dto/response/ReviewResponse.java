package kr.inuappcenter.spotinu.domain.review.dto.response;

import kr.inuappcenter.spotinu.domain.review.entity.SpotKeyword;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ReviewResponse {

  private String memberName;
  private List<ReviewPhotoResponse> photos;
  private String content;
  private LocalDate visitDate;
  private LocalTime visitTime;
  private Set<SpotKeyword> keywords;

}
