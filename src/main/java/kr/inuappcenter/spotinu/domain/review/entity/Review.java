package kr.inuappcenter.spotinu.domain.review.entity;

import jakarta.persistence.*;
import kr.inuappcenter.spotinu.domain.member.entity.Member;
import kr.inuappcenter.spotinu.domain.spot.entity.Spot;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Review {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "spot_id", nullable = false)
  private Spot spot;

  // 방문 일시

  // 사진들
  @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ReviewPhoto> photos = new ArrayList<>();

  // 리뷰 코멘트
  private String content;

  // 방문 후기 키워드
  @Enumerated(EnumType.STRING)
  private SpotKeyword keyword;
}
