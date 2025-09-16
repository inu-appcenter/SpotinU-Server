package kr.inuappcenter.spotinu.domain.review.service;

import kr.inuappcenter.spotinu.domain.common.storage.exception.StorageException;
import kr.inuappcenter.spotinu.domain.common.storage.service.S3FileStorageService;
import kr.inuappcenter.spotinu.domain.member.entity.CustomUserDetails;
import kr.inuappcenter.spotinu.domain.member.entity.Member;
import kr.inuappcenter.spotinu.domain.member.entity.Role;
import kr.inuappcenter.spotinu.domain.member.exception.MemberErrorCode;
import kr.inuappcenter.spotinu.domain.member.exception.MemberException;
import kr.inuappcenter.spotinu.domain.member.repository.MemberRepository;
import kr.inuappcenter.spotinu.domain.review.dto.request.ReviewCreateRequest;
import kr.inuappcenter.spotinu.domain.review.dto.response.ReviewResponse;
import kr.inuappcenter.spotinu.domain.review.entity.Review;
import kr.inuappcenter.spotinu.domain.review.entity.ReviewPhoto;
import kr.inuappcenter.spotinu.domain.review.exception.ReviewErrorCode;
import kr.inuappcenter.spotinu.domain.review.exception.ReviewException;
import kr.inuappcenter.spotinu.domain.review.mapper.ReviewMapper;
import kr.inuappcenter.spotinu.domain.review.repository.ReviewRepository;
import kr.inuappcenter.spotinu.domain.spot.entity.Spot;
import kr.inuappcenter.spotinu.domain.spot.entity.SpotPhoto;
import kr.inuappcenter.spotinu.domain.spot.exception.SpotErrorCode;
import kr.inuappcenter.spotinu.domain.spot.exception.SpotException;
import kr.inuappcenter.spotinu.domain.spot.repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static kr.inuappcenter.spotinu.domain.common.storage.exception.StorageErrorCode.S3_UPLOAD_FAIL;
import static kr.inuappcenter.spotinu.domain.member.entity.Role.ADMIN;
import static kr.inuappcenter.spotinu.domain.member.exception.MemberErrorCode.USER_NOT_FOUND;
import static kr.inuappcenter.spotinu.domain.review.exception.ReviewErrorCode.REVIEW_NOT_FOUND;
import static kr.inuappcenter.spotinu.domain.spot.exception.SpotErrorCode.SPOT_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

  private final S3FileStorageService s3FileStorageService;
  private final ReviewRepository reviewRepository;
  private final MemberRepository memberRepository;
  private final SpotRepository spotRepository;
  private final ReviewMapper reviewMapper;

  @Override
  public void create(Long memberId, Long spotId, ReviewCreateRequest reviewCreateRequest, List<MultipartFile> photos) {
    log.info("Creating new review by memberId: {}", memberId);
    Member member = memberRepository.findById(memberId)
      .orElseThrow(() -> new MemberException(USER_NOT_FOUND));

    Spot spot = spotRepository.findById(spotId)
      .orElseThrow(() -> new SpotException(SPOT_NOT_FOUND));

    Review review = reviewMapper.toEntity(member, spot, reviewCreateRequest);
    log.debug("Mapped Review entity from ReviewCreateRequest: {}", review);

    if (photos != null && !photos.isEmpty()) {
      int index = 1;
      for (MultipartFile photoFile : photos) {
        try {
          String photoUrl = s3FileStorageService.uploadImage(photoFile);
          log.debug("Uploaded photo {} URL: {}", index, photoUrl);

          ReviewPhoto reviewPhoto = ReviewPhoto.builder()
            .url(photoUrl)
            .orderIndex(index++)
            .thumbnail(index == 2) // index++ 후라 2가 첫 사진
            .build();

          review.addPhoto(reviewPhoto);
        } catch (Exception e) {
          log.error("Failed to upload photo at index {} for review creation by memberId {}", index, memberId, e);
          throw new StorageException(S3_UPLOAD_FAIL, e);
        }
      }
    } else {
      log.info("No photos uploaded for review creation by memberId: {}", memberId);
    }

    reviewRepository.save(review);
    log.info("Review created successfully: {}", review.getSpot().getName());
  }

  @Override
  public void delete(CustomUserDetails userDetails, Long reviewId) {
    Review review = reviewRepository.findByIdWithPhotos(reviewId);
    if (review == null) {
      throw new ReviewException(REVIEW_NOT_FOUND);
    }

    review.getPhotos().forEach(photo -> {
      try {
        s3FileStorageService.deleteImage(photo.getUrl());
        log.info("Deleted photo from S3: {}", photo.getUrl());
      } catch (Exception e) {
        log.error("Failed to delete photo from S3: {}", photo.getUrl(), e);
      }
    });

    // 관리자면 바로 삭제 허용
    if (!(userDetails.getRole() == ADMIN ||
      review.getMember().getId().equals(userDetails.getId()))) {
      throw new ReviewException(ReviewErrorCode.NOT_OWNER);
    }

    reviewRepository.delete(review);
    log.info("Review deleted successfully: {}", review.getSpot().getName());
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ReviewResponse> getMyReviews(Long memberId, int page, int size) {
    log.info("Fetching all reviews - page: {}, size: {}", page, size);

    Pageable pageable = PageRequest.of(page, size);
    Page<Review> reviews = reviewRepository.findByMemberId(memberId, pageable);

    log.info("Fetched {} reviews", reviews.getNumberOfElements());

    return reviews.map(reviewMapper::toReviewResponse);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ReviewResponse> getReviews(Long spotId, int page, int size) {
    log.info("Fetching all reviews - page: {}, size: {}", page, size);

    Pageable pageable = PageRequest.of(page, size);
    // TODO: Spot이 존재 하는지 확인해야됨.
    Page<Review> reviews = reviewRepository.findBySpotId(spotId, pageable);

    log.info("Fetched {} reviews", reviews.getNumberOfElements());

    return reviews.map(reviewMapper::toReviewResponse);
  }
}
