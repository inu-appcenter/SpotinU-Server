package kr.inuappcenter.spotinu.domain.review.entity;

import jakarta.persistence.*;
import kr.inuappcenter.spotinu.domain.spot.entity.Spot;

@Entity
public class ReviewPhoto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String url;

  private boolean isThumbnail;

  private int orderIndex;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "review_id")
  private Review review;
}
