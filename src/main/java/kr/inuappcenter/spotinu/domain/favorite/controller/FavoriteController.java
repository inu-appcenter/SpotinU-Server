package kr.inuappcenter.spotinu.domain.favorite.controller;

import kr.inuappcenter.spotinu.domain.favorite.dto.request.FavoriteRequest;
import kr.inuappcenter.spotinu.domain.favorite.dto.request.SpotIdsRequest;
import kr.inuappcenter.spotinu.domain.favorite.dto.response.FavoriteDetailResponse;
import kr.inuappcenter.spotinu.domain.favorite.dto.response.FavoriteResponse;
import kr.inuappcenter.spotinu.domain.favorite.service.FavoriteService;
import kr.inuappcenter.spotinu.domain.member.entity.CustomUserDetails;
import kr.inuappcenter.spotinu.global.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FavoriteController implements FavoriteControllerSpecification{

  private final FavoriteService favoriteService;

  @Override
  public ResponseEntity<ResponseDto<String>> toggleFavorite(CustomUserDetails userDetails, Long spotId, FavoriteRequest request) {
    boolean liked = favoriteService.toggleFavorite(userDetails, spotId, request.getMemo());
    String msg = liked ? "즐겨찾기 추가됨" : "즐겨찾기 취소됨";
    return ResponseEntity.ok(ResponseDto.success(msg));
  }

  @Override
  public ResponseEntity<ResponseDto<List<FavoriteResponse>>> getMyFavorites(CustomUserDetails userDetails) {
    List<FavoriteResponse> favoriteResponses = favoriteService.getMyFavorite(userDetails);
    return ResponseEntity
      .ok(ResponseDto.success(favoriteResponses));
  }

  @Override
  public ResponseEntity<ResponseDto<Page<FavoriteDetailResponse>>> getFavoriteDetails(CustomUserDetails userDetails, SpotIdsRequest request, int page, int size) {
    log.info("Fetching favorite details: memberId={}, spotIds={}, page={}, size={}",
      userDetails.getId(), request.getSpotIds(), page, size);

    Page<FavoriteDetailResponse> details = favoriteService
      .getMyFavoriteDetail(userDetails, request, page, size);

    log.info("Found favorite details for memberId={}", userDetails.getId());
    return ResponseEntity.ok(ResponseDto.success(details));
  }
}
