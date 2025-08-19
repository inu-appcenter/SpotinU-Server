package kr.inuappcenter.spotinu.domain.review.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReviewPhoto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String url;

  private boolean thumbnail;

  private int orderIndex;

  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "review_id")
  private Review review;

  @Builder
  public ReviewPhoto(String url, boolean thumbnail, int orderIndex) {
    this.url = url;
    this.thumbnail = thumbnail;
    this.orderIndex = orderIndex;
  }
}
