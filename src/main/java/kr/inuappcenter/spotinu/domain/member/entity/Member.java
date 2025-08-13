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

  /**
   * 도메인 규칙 적용한 정적 팩토리 메서드
   */
  public static Member create(String name, Long studentNumber, String encodedPassword, Role role) {
    return Member.builder()
      .name(name)
      .studentNumber(studentNumber)
      .password(encodedPassword)
      .role(role != null ? role : Role.USER) // 기본값 설정
      .build();
  }

  // 비밀번호 변경용 도메인 메서드 (필요 시에만)
  public void changePassword(String encodedPassword) {
    this.password = encodedPassword;
  }
}
