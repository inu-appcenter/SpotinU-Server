package kr.inuappcenter.spotinu.domain.spot.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.inuappcenter.spotinu.domain.member.entity.CustomUserDetails;
import kr.inuappcenter.spotinu.domain.spot.dto.request.SpotCreateRequest;
import kr.inuappcenter.spotinu.domain.spot.dto.request.SpotFilterRequest;
import kr.inuappcenter.spotinu.domain.spot.dto.response.SpotDetailResponse;
import kr.inuappcenter.spotinu.domain.spot.dto.response.SpotDownLoadResponse;
import kr.inuappcenter.spotinu.domain.spot.dto.response.SpotResponse;
import kr.inuappcenter.spotinu.global.response.PageResponseDto;
import kr.inuappcenter.spotinu.global.response.ResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "spot", description = "Spot API")
@RequestMapping("/api/v1/spots")
public interface SpotControllerSpecification {

  /**
   * 전체 장소 목록 조회 (페이징)
   */
  @GetMapping
  ResponseEntity<ResponseDto<PageResponseDto<SpotResponse>>> getAllSpots(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size);

  /**
   * 필터 조건으로 장소 검색
   */
  @PostMapping("/search")
  ResponseEntity<ResponseDto<PageResponseDto<SpotResponse>>> searchSpots(
    @Valid @RequestBody SpotFilterRequest spotFilterRequest,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size);

  /**
   * 특정 장소 상세 조회
   */
  @GetMapping("/{spotId}")
  ResponseEntity<ResponseDto<SpotDetailResponse>> getSpotDetail(
    @PathVariable Long spotId);

  /**
   * 장소 생성 (사진 포함 가능)
   */
  @PostMapping(consumes = {"multipart/form-data"})
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<ResponseDto<Void>> create(
    @AuthenticationPrincipal CustomUserDetails userDetails,
    @RequestPart("spotCreateRequest") @Valid SpotCreateRequest spotCreateRequest,
    @RequestPart(value = "photos", required = false) List<MultipartFile> photos);

  /**
   * 장소 생성 (테스트 - 사진 없이)
   */
  @PostMapping(value = "/createNoPhotos", consumes = {"application/json"})
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<ResponseDto<Void>> createNoPhotos(
    @AuthenticationPrincipal CustomUserDetails userDetails,
    @RequestBody @Valid SpotCreateRequest spotCreateRequest);

  /**
   * 장소 삭제
   */
  @DeleteMapping("/{spotId}")
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<ResponseDto<Void>> delete(
    @AuthenticationPrincipal CustomUserDetails userDetails,
    @PathVariable Long spotId);

  /**
   * 장소 다운로드 - 로컬 캐시
   * @return
   */
  @GetMapping("/download")
  ResponseEntity<ResponseDto<List<SpotDownLoadResponse>>> downloadAllSpots(
    @RequestHeader(value = "If-None-Match", required = false) String ifNoneMatch
  );


}
