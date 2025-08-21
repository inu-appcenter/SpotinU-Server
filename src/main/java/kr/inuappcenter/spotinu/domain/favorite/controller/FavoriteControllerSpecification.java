package kr.inuappcenter.spotinu.domain.favorite.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.inuappcenter.spotinu.domain.favorite.dto.request.FavoriteRequest;
import kr.inuappcenter.spotinu.domain.favorite.dto.request.SpotIdsRequest;
import kr.inuappcenter.spotinu.domain.favorite.dto.response.FavoriteDetailResponse;
import kr.inuappcenter.spotinu.domain.favorite.dto.response.FavoriteResponse;
import kr.inuappcenter.spotinu.domain.member.entity.CustomUserDetails;
import kr.inuappcenter.spotinu.global.response.ResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "favorite", description = "Favorite API")
@RequestMapping("/api/v1/favorites")
public interface FavoriteControllerSpecification {

  /**
   * 즐겨찾기 토글 (좋아요 추가 또는 취소)
   */
  @PostMapping("/{spotId}/toggle")
  @Operation(summary = "즐겨찾기 토글 (좋아요 추가 또는 취소)", description = "특정 스팟에 대한 즐겨찾기 ON/OFF")
  @PreAuthorize("isAuthenticated()")
  ResponseEntity<ResponseDto<String>> toggleFavorite(
    @AuthenticationPrincipal
    @Parameter(description = "로그인한 사용자 정보", required = true)
    CustomUserDetails userDetails,

    @PathVariable
    @Parameter(description = "spot pk")
    Long spotId,

    @RequestBody
    @Valid
    @Parameter(description = "즐겨찾기 요청 DTO")
    FavoriteRequest request);

  /**
   * 내가 즐겨찾기한 장소 목록 조회
   */
  @GetMapping
  @Operation(summary = "내가 즐겨찾기한 장소 목록 조회", description = "내가 즐겨찾기한 장소 목록을 조회합니다.")
  @PreAuthorize("isAuthenticated()")
  ResponseEntity<ResponseDto<List<FavoriteResponse>>> getMyFavorites(
    @AuthenticationPrincipal
    @Parameter(description = "로그인한 사용자 정보", required = true)
    CustomUserDetails userDetails);


  @PostMapping("/details")
  @Operation(summary = "내가 즐겨찾기한 장소 세부 조회", description = "내가 즐겨찾기한 장소 세부를 조회합니다.")
  @PreAuthorize("isAuthenticated()")
  ResponseEntity<ResponseDto<Page<FavoriteDetailResponse>>> getFavoriteDetails(
    @AuthenticationPrincipal
    @Parameter(description = "로그인한 사용자 정보", required = true)
    CustomUserDetails userDetails,

    @RequestBody
    @Valid
    @Parameter(description = "Spot Ids 요청 DTO", required = true)
    SpotIdsRequest request,

    @RequestParam(defaultValue = "0")
    @Parameter(description = "페이지 번호 (0부터 시작)")
    int page,

    @RequestParam(defaultValue = "10")
    @Parameter(description = "페이지 크기")
    int size
    );



}
