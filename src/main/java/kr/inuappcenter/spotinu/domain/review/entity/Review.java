package kr.inuappcenter.spotinu.domain.review.entity;

import jakarta.persistence.*;
import kr.inuappcenter.spotinu.domain.member.entity.Member;
import kr.inuappcenter.spotinu.domain.spot.entity.Spot;
import kr.inuappcenter.spotinu.domain.spot.entity.SpotPhoto;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "spot_id")
  private Spot spot;

  // 사진들
  @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ReviewPhoto> photos = new ArrayList<>();

  // 리뷰 코멘트
  private String content;

  // 방문 일시 (날짜)
  @Column(nullable = false)
  private LocalDate visitDate;

  // 방문 시간
  private LocalTime visitTime;

  // 방문 후기 키워드
  @ElementCollection(targetClass = SpotKeyword.class)
  @CollectionTable(
    name = "review_keywords",
    joinColumns = @JoinColumn(name = "review_id")
  )
  @Enumerated(EnumType.STRING)
  private Set<SpotKeyword> keywords = new HashSet<>();

  @Builder
  public Review(Member member, Spot spot, String content, LocalDate visitDate, LocalTime visitTime, Set<SpotKeyword> keywords) {
    this.member = member;
    this.spot = spot;
    this.content = content;
    this.visitDate = visitDate;
    this.visitTime = visitTime;
    this.keywords = keywords;
  }

  public void addPhoto(ReviewPhoto photo) {
    this.photos.add(photo);
    photo.setReview(this);  // SpotPhoto 쪽 연관관계 설정 (반대편)
  }
}
