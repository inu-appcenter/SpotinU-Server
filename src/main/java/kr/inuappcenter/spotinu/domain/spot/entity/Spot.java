package kr.inuappcenter.spotinu.domain.spot.entity;

import jakarta.persistence.*;
import kr.inuappcenter.spotinu.domain.review.entity.Review;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 간단하게 장소들 있는곳에 배치될 엔티티
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Spot {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String x;
  private String y;

  private String name;

  private String locationDetail;  // XX호관 XXX호

  private String description; // 간단한 소개

  private String businessHours; // 운영 시간

  // Filter
  private boolean sleepingAllowed;  // 취침
  private boolean eatingAllowed;  // 취식
  private boolean hasPowerOutlet; // 콘센트
  private boolean studyAllowed; // 개인 공부
  private boolean entertainment;  // 오락시설
  private boolean reservationRequired;  // 예약제

  @Enumerated(EnumType.STRING)
  private PlaceType placeType;  // 실내/야외

  // SpotPhoto
  @OneToMany(mappedBy = "spot", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<SpotPhoto> photos = new ArrayList<>();

  private String businessHoursDetail;

  private String descriptionDetail;

  @OneToMany(mappedBy = "spot", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Review> reviews = new ArrayList<>();

  @Builder
  public Spot(
    String x,
    String y,
    String name,
    String locationDetail,
    String description,
    String businessHours,
    boolean sleepingAllowed,
    boolean eatingAllowed,
    boolean hasPowerOutlet,
    boolean studyAllowed,
    boolean entertainment,
    boolean reservationRequired,
    PlaceType placeType,
    String businessHoursDetail,
    String descriptionDetail
  ) {
    this.x = x;
    this.y = y;
    this.name = name;
    this.locationDetail = locationDetail;
    this.description = description;
    this.businessHours = businessHours;
    this.sleepingAllowed = sleepingAllowed;
    this.eatingAllowed = eatingAllowed;
    this.hasPowerOutlet = hasPowerOutlet;
    this.studyAllowed = studyAllowed;
    this.entertainment = entertainment;
    this.reservationRequired = reservationRequired;
    this.placeType = placeType;
    this.businessHoursDetail = businessHoursDetail;
    this.descriptionDetail = descriptionDetail;
    this.photos = new ArrayList<>();
    this.reviews = new ArrayList<>();
  }

  public void addPhoto(SpotPhoto photo) {
    this.photos.add(photo);
    photo.setSpot(this);  // SpotPhoto 쪽 연관관계 설정 (반대편)
  }

  public void addReview(Review review) {
    this.reviews.add(review);
    review.setSpot(this);  // Review 쪽 연관관계 설정
  }
}
