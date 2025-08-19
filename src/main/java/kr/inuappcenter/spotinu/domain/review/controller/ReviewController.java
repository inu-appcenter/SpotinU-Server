package kr.inuappcenter.spotinu.domain.review.controller;

import kr.inuappcenter.spotinu.domain.member.entity.CustomUserDetails;
import kr.inuappcenter.spotinu.domain.review.dto.request.ReviewCreateRequest;
import kr.inuappcenter.spotinu.domain.review.dto.response.ReviewResponse;
import kr.inuappcenter.spotinu.domain.review.service.ReviewService;
import kr.inuappcenter.spotinu.global.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController implements ReviewControllerSpecification {

  private final ReviewService reviewService;

  @Override
  public ResponseEntity<ResponseDto<Void>> create(CustomUserDetails userDetails, Long spotId, ReviewCreateRequest reviewCreateRequest, List<MultipartFile> photos) {
    reviewService.create(userDetails.getMember().getId(), spotId, reviewCreateRequest, photos);
    return ResponseEntity.status(HttpStatus.CREATED)
      .body(ResponseDto.success("Review created successfully"));
  }

  @Override
  public ResponseEntity<ResponseDto<Void>> createNoPhotos(CustomUserDetails userDetails, Long spotId, ReviewCreateRequest reviewCreateRequest) {
    reviewService.create(userDetails.getMember().getId(), spotId, reviewCreateRequest, null);
    return ResponseEntity.status(HttpStatus.CREATED)
      .body(ResponseDto.success("Review created successfully"));
  }

  @Override
  public ResponseEntity<ResponseDto<Page<ReviewResponse>>> getSpotReviews(Long spotId, int page, int size) {
    Page<ReviewResponse> reviews = reviewService.getReviews(spotId, page, size);
    return ResponseEntity.ok(ResponseDto.success(reviews));
  }

  @Override
  public ResponseEntity<ResponseDto<Page<ReviewResponse>>> getReviews(CustomUserDetails userDetails, int page, int size) {
    Page<ReviewResponse> reviews = reviewService.getMyReviews(userDetails.getMember().getId(), page, size);
    return ResponseEntity.ok(ResponseDto.success(reviews));
  }

  @Override
  public ResponseEntity<ResponseDto<Void>> delete(CustomUserDetails userDetails, Long reviewId) {
    reviewService.delete(userDetails, reviewId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
      .body(ResponseDto.success("Review deleted successfully"));
  }
}
