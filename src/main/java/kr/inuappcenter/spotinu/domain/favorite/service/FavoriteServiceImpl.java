package kr.inuappcenter.spotinu.domain.favorite.service;

import kr.inuappcenter.spotinu.domain.favorite.dto.request.SpotIdsRequest;
import kr.inuappcenter.spotinu.domain.favorite.dto.response.FavoriteDetailResponse;
import kr.inuappcenter.spotinu.domain.favorite.dto.response.FavoriteResponse;
import kr.inuappcenter.spotinu.domain.favorite.entity.Favorite;
import kr.inuappcenter.spotinu.domain.favorite.exception.FavoriteException;
import kr.inuappcenter.spotinu.domain.favorite.mapper.FavoriteMapper;
import kr.inuappcenter.spotinu.domain.favorite.repository.FavoriteRepository;
import kr.inuappcenter.spotinu.domain.member.entity.CustomUserDetails;
import kr.inuappcenter.spotinu.domain.spot.service.SpotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static kr.inuappcenter.spotinu.domain.favorite.exception.FavoriteErrorCode.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

  private final FavoriteRepository favoriteRepository;
  private final FavoriteMapper favoriteMapper;
  private final SpotService spotService;

  @Override
  public boolean toggleFavorite(CustomUserDetails userDetails, Long spotId, String memo) {
    Long memberId = userDetails.getId();
    log.info("Member {} toggling favorite for spot {}", memberId, spotId);

    Optional<Favorite> favoriteOpt = favoriteRepository.findByMemberIdAndSpotId(memberId, spotId);

    if (favoriteOpt.isPresent()) {
      favoriteRepository.delete(favoriteOpt.get());
      log.info("Favorite removed for member {} and spot {}", memberId, spotId);
      return false;
    }

    // 존재 검증 + 프록시
    try {
      Favorite favorite = Favorite.builder()
        .memo(memo)
        .member(userDetails.getMember())
        .spot(spotService.getSpotProxy(spotId))
        .build();
      favoriteRepository.save(favorite);
      log.info("Favorite added for member {} and spot {}", memberId, spotId);
      return true;
    } catch (Exception e) {
      log.error("Failed to add favorite for member {} and spot {}", memberId, spotId, e);
      throw new FavoriteException(ADD_FAIL, e); // 필요 시 커스텀 예외로 변환
    }
  }

  @Override
  @Transactional(readOnly = true)
  public List<FavoriteResponse> getMyFavorite(CustomUserDetails userDetails) {
    Long memberId = userDetails.getId();
    log.info("Fetching favorites for member {}", memberId);

    List<Favorite> favoriteList = favoriteRepository.findByMemberId(memberId);

    log.info("Found favorites for member {}", memberId);

    return favoriteList.stream()
      .map(favoriteMapper::toResponse)
      .toList();
  }

  /**
   * flow 생각..
   * Spot Id 들 받아옴
   * Repository 에서 조회 -> 어디 repo? -> favorite
   */
  @Override
  public Page<FavoriteDetailResponse> getMyFavoriteDetail(CustomUserDetails userDetails, SpotIdsRequest spotIdsRequest, int page, int size) {

    Long memberId = userDetails.getId();
    List<Long> spotIds = spotIdsRequest.getSpotIds();

    if (spotIds == null || spotIds.isEmpty()) {
      log.warn("SpotIds list is empty for memberId={}", memberId);
      throw new FavoriteException(NOT_FOUND);
    }

    Pageable pageable = PageRequest.of(page, size);
    log.info("Fetching favorites for memberId={} with spotIds={}, page={}, size={}",
      memberId, spotIds, page, size);

    Page<Favorite> favorites = favoriteRepository.findByMemberIdAndSpotIdIn(memberId, spotIds, pageable);
    if (favorites.isEmpty()) {
      log.info("No favorites found for memberId={} with spotIds={}", memberId, spotIds);
      throw new FavoriteException(NOT_FOUND);
    }

    log.info("Found {} favorites for memberId={}", favorites.getNumberOfElements(), memberId);

    return favorites.map(favoriteMapper::toDetailResponse);
  }
}
