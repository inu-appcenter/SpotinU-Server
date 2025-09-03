package kr.inuappcenter.spotinu.domain.spot.controller;

import kr.inuappcenter.spotinu.domain.member.entity.CustomUserDetails;
import kr.inuappcenter.spotinu.domain.spot.dto.request.SpotCreateRequest;
import kr.inuappcenter.spotinu.domain.spot.dto.request.SpotFilterRequest;
import kr.inuappcenter.spotinu.domain.spot.dto.response.SpotDetailResponse;
import kr.inuappcenter.spotinu.domain.spot.dto.response.SpotDownLoadResponse;
import kr.inuappcenter.spotinu.domain.spot.dto.response.SpotResponse;
import kr.inuappcenter.spotinu.domain.spot.service.SpotService;
import kr.inuappcenter.spotinu.global.response.PageResponseDto;
import kr.inuappcenter.spotinu.global.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SpotController implements SpotControllerSpecification {

  private final SpotService spotService;

  @Override
  public ResponseEntity<ResponseDto<PageResponseDto<SpotResponse>>> getAllSpots(int page, int size) {
    PageResponseDto<SpotResponse> spotResponses = spotService.getAllSpots(page, size);
    return ResponseEntity
      .ok(ResponseDto.success(spotResponses));
  }

  @Override
  public ResponseEntity<ResponseDto<PageResponseDto<SpotResponse>>> searchSpots(SpotFilterRequest spotFilterRequest, int page, int size) {
    PageResponseDto<SpotResponse> spotResponses = spotService.searchSpots(spotFilterRequest, page, size);
    return ResponseEntity
      .ok(ResponseDto.success(spotResponses));
  }

  @Override
  public ResponseEntity<ResponseDto<SpotDetailResponse>> getSpotDetail(Long spotId) {
    SpotDetailResponse response = spotService.getSpotDetail(spotId);
    return ResponseEntity
      .ok(ResponseDto.success(response));
  }

  @Override
  public ResponseEntity<ResponseDto<Void>> create(CustomUserDetails userDetails, SpotCreateRequest spotCreateRequest, List<MultipartFile> photos) {
    spotService.create(userDetails.getMember().getId(), spotCreateRequest, photos);
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(ResponseDto.success("Spot created successfully"));
  }

  @Override
  public ResponseEntity<ResponseDto<Void>> createNoPhotos(CustomUserDetails userDetails, SpotCreateRequest spotCreateRequest) {
    spotService.create(userDetails.getMember().getId(), spotCreateRequest, null);
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(ResponseDto.success("Spot created successfully"));
  }

  @Override
  public ResponseEntity<ResponseDto<Void>> delete(CustomUserDetails userDetails, Long spotId) {
    spotService.delete(spotId);
    return ResponseEntity
      .status(HttpStatus.NO_CONTENT)
      .body(ResponseDto.success("Spot deleted successfully"));
  }

  @Override
  public ResponseEntity<ResponseDto<List<SpotDownLoadResponse>>> downloadAllSpots(String ifNoneMatch) {
    List<SpotDownLoadResponse> spots = spotService.downloadAllSpots();
    String eTag = spotService.generateETag(spots);

    if (eTag.equals(ifNoneMatch)) {
      log.info("ETag matches client. Returning 304 Not Modified");
      return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
        .eTag(eTag)
        .build();
    }

    log.info("Returning {} spots with ETag={}", spots.size(), eTag);
    return ResponseEntity.ok()
      .cacheControl(CacheControl.maxAge(1, TimeUnit.DAYS).cachePublic())
      .eTag(eTag)
      .body(ResponseDto.success(spots));
  }
}
