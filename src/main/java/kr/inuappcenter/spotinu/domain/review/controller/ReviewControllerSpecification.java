package kr.inuappcenter.spotinu.domain.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import kr.inuappcenter.spotinu.domain.member.entity.CustomUserDetails;
import kr.inuappcenter.spotinu.domain.review.dto.request.ReviewCreateRequest;
import kr.inuappcenter.spotinu.domain.review.dto.response.ReviewResponse;
import kr.inuappcenter.spotinu.global.response.ResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "review", description = "Review API")
@RequestMapping("/api/v1")
public interface ReviewControllerSpecification {

  @Operation(summary = "스팟 리뷰 작성", description = "특정 스팟에 대한 리뷰를 작성합니다. 사진 첨부 가능.")
  @PostMapping(value = "/spots/{spotId}/reviews", consumes = {"multipart/form-data"})
  @PreAuthorize("isAuthenticated()")
  ResponseEntity<ResponseDto<Void>> create(
    @AuthenticationPrincipal
    @Parameter(description = "로그인한 사용자 정보", required = true)
    CustomUserDetails userDetails,

    @PathVariable
    @Parameter(description = "리뷰 작성할 스팟 ID", required = true)
    Long spotId,

    @RequestPart("reviewCreateRequest")
    @Valid
    @Parameter(description = "리뷰 작성 요청 DTO", required = true)
    ReviewCreateRequest reviewCreateRequest,

    @RequestPart(value = "photos", required = false)
    @Parameter(description = "리뷰 사진 목록", required = false)
    List<MultipartFile> photos
  );

  @Operation(summary = "스팟 리뷰 작성 - 테스트 전용(사진X)", description = "특정 스팟에 대한 리뷰를 작성합니다.")
  @PostMapping(value = "/spots/{spotId}/reviews-no-photos", consumes = {"application/json"})
  @PreAuthorize("isAuthenticated()")
  ResponseEntity<ResponseDto<Void>> createNoPhotos(
    @AuthenticationPrincipal
    @Parameter(description = "로그인한 사용자 정보", required = true)
    CustomUserDetails userDetails,

    @PathVariable("spotId")
    @Parameter(description = "리뷰 작성할 스팟 ID", required = true)
    Long spotId,

    @RequestBody
    @Valid
    @Parameter(description = "리뷰 작성 요청 DTO", required = true)
    ReviewCreateRequest reviewCreateRequest
  );

  @Operation(summary = "스팟 리뷰 목록 조회", description = "특정 스팟 리뷰 목록 조회. 페이징 가능.")
  @GetMapping("/spots/{spotId}/reviews")
  @PreAuthorize("isAuthenticated()")
  ResponseEntity<ResponseDto<Page<ReviewResponse>>> getSpotReviews(

    @PathVariable
    @Parameter(description = "spot pk")
    Long spotId,

    @RequestParam(defaultValue = "0")
    @Parameter(description = "페이지 번호 (0부터 시작)")
    int page,

    @RequestParam(defaultValue = "10")
    @Parameter(description = "페이지 크기")
    int size
  );

  @Operation(summary = "사용자 리뷰 목록 조회", description = "로그인한 사용자의 리뷰 목록 조회. 페이징 가능.")
  @GetMapping("/me/reviews")
  @PreAuthorize("isAuthenticated()")
  ResponseEntity<ResponseDto<Page<ReviewResponse>>> getReviews(
    @AuthenticationPrincipal
    @Parameter(description = "로그인한 사용자 정보", required = true)
    CustomUserDetails userDetails,

    @RequestParam(defaultValue = "0")
    @Parameter(description = "페이지 번호 (0부터 시작)")
    int page,

    @RequestParam(defaultValue = "10")
    @Parameter(description = "페이지 크기")
    int size
  );

  @Operation(summary = "리뷰 삭제", description = "사용자가 작성한 리뷰를 삭제합니다.")
  @DeleteMapping("/reviews/{reviewId}")
  @PreAuthorize("isAuthenticated()")
  ResponseEntity<ResponseDto<Void>> delete(
    @AuthenticationPrincipal
    @Parameter(description = "로그인한 사용자 정보", required = true)
    CustomUserDetails userDetails,

    @PathVariable
    @Parameter(description = "삭제할 리뷰 ID", required = true)
    Long reviewId
  );
}
