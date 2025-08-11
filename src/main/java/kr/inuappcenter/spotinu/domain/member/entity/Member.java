package kr.inuappcenter.spotinu.domain.member.entity;

import jakarta.persistence.*;
import kr.inuappcenter.spotinu.domain.favorite.entity.Favorite;
import kr.inuappcenter.spotinu.domain.review.entity.Review;
import kr.inuappcenter.spotinu.global.entity.BaseEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;

  // 포탈 학번
  private Long studentNumber;

  // 포탈 비번
  private String password;

  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToMany(mappedBy = "member", cascade = CascadeType.ALL , orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Review> reviews = new ArrayList<>();

  @OneToMany(mappedBy = "member", cascade = CascadeType.ALL , orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Favorite> favorites = new ArrayList<>();

  @Builder
  public Member(String name, Long studentNumber, String password, Role role) {
    this.name = name;
    this.studentNumber = studentNumber;
    this.password = password;
    this.role = role;
  }
}
