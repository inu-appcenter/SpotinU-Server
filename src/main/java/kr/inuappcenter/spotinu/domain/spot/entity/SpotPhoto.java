package kr.inuappcenter.spotinu.domain.spot.entity;

import jakarta.persistence.*;
import kr.inuappcenter.spotinu.domain.review.entity.Review;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SpotPhoto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String url;

  private boolean thumbnail;

  private int orderIndex;

  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "spot_id")
  private Spot spot;

}
