package kr.inuappcenter.spotinu.domain.favorite.service;

import kr.inuappcenter.spotinu.domain.favorite.dto.request.SpotIdsRequest;
import kr.inuappcenter.spotinu.domain.favorite.dto.response.FavoriteDetailResponse;
import kr.inuappcenter.spotinu.domain.favorite.dto.response.FavoriteResponse;
import kr.inuappcenter.spotinu.domain.member.entity.CustomUserDetails;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FavoriteService {
  /**
   * 즐겨 찾기
   * @param userDetails
   * @param spotId
   * @param memo
   * @return
   */
  boolean toggleFavorite(CustomUserDetails userDetails,
                      Long spotId,
                      String memo);

  /**
   * 즐겨 찾기 조회
   * @param userDetails
   * @return
   */
  List<FavoriteResponse> getMyFavorite(CustomUserDetails userDetails);

  /**
   * 즐겨 찾기 디테일 조회
   * @param userDetails
   * @param page
   * @param size
   * @param spotIdsRequest
   * @return
   */
  Page<FavoriteDetailResponse> getMyFavoriteDetail(CustomUserDetails userDetails, SpotIdsRequest spotIdsRequest, int page, int size);
}
