package kr.inuappcenter.spotinu.domain.spot.controller;

import kr.inuappcenter.spotinu.domain.member.entity.CustomUserDetails;
import kr.inuappcenter.spotinu.domain.spot.dto.request.SpotCreateRequest;
import kr.inuappcenter.spotinu.domain.spot.dto.request.SpotFilterRequest;
import kr.inuappcenter.spotinu.domain.spot.dto.response.SpotDetailResponse;
import kr.inuappcenter.spotinu.domain.spot.dto.response.SpotResponse;
import kr.inuappcenter.spotinu.domain.spot.service.SpotService;
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
public class SpotController implements SpotControllerSpecification {

  private final SpotService spotService;

  @Override
  public ResponseEntity<ResponseDto<Page<SpotResponse>>> getAllSpots(int page, int size) {
    Page<SpotResponse> spotResponses = spotService.getAllSpots(page, size);
    return ResponseEntity
      .ok(ResponseDto.success(spotResponses));
  }

  @Override
  public ResponseEntity<ResponseDto<Page<SpotResponse>>> searchSpots(SpotFilterRequest spotFilterRequest, int page, int size) {
    Page<SpotResponse> spotResponses = spotService.searchSpots(spotFilterRequest, page, size);
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
}
