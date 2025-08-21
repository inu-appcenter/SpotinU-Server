package kr.inuappcenter.spotinu.domain.spot.service;

import kr.inuappcenter.spotinu.domain.spot.dto.request.SpotCreateRequest;
import kr.inuappcenter.spotinu.domain.spot.dto.request.SpotFilterRequest;
import kr.inuappcenter.spotinu.domain.spot.dto.response.SpotDetailResponse;
import kr.inuappcenter.spotinu.domain.spot.dto.response.SpotResponse;
import kr.inuappcenter.spotinu.domain.spot.entity.Spot;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SpotService {

  /**
   * 전체 장소 조회
   * @param page
   * @param size
   * @return
   */
  Page<SpotResponse> getAllSpots(int page,
                                 int size
  );

  /**
   * 장소 검색(필터)
   * @param spotFilterRequest
   * @param page
   * @param size
   * @return
   */
  Page<SpotResponse> searchSpots(SpotFilterRequest spotFilterRequest,
                                 int page, int size
  );

  /**
   * 장소 세부 조회
   * @param spotId
   * @return
   */
  SpotDetailResponse getSpotDetail(Long spotId);

  /**
   * 장소 등록(Admin)
   * @param memberId
   * @param spotCreateRequest
   * @param photos
   */
  void create(Long memberId,
              SpotCreateRequest spotCreateRequest,
              List<MultipartFile> photos
  );

  /**
   * 장소 삭제(Admin)
   * @param spotId
   */
  void delete(Long spotId);

  Spot getSpotProxy(Long spotId);

}
