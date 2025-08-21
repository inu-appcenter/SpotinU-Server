package kr.inuappcenter.spotinu.domain.favorite.entity;

import jakarta.persistence.*;
import kr.inuappcenter.spotinu.domain.member.entity.Member;
import kr.inuappcenter.spotinu.domain.spot.entity.Spot;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Favorite {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String memo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "spot_id", nullable = false)
  private Spot spot;

  @Builder
  public Favorite(String memo, Member member, Spot spot) {
    this.memo = memo;
    this.member = member;
    this.spot = spot;
  }
}
