package kr.inuappcenter.spotinu.domain.spot.service;

import kr.inuappcenter.spotinu.domain.spot.dto.request.SpotCreateRequest;
import kr.inuappcenter.spotinu.domain.spot.dto.request.SpotFilterRequest;
import kr.inuappcenter.spotinu.domain.spot.dto.response.SpotDetailResponse;
import kr.inuappcenter.spotinu.domain.spot.dto.response.SpotDownLoadResponse;
import kr.inuappcenter.spotinu.domain.spot.dto.response.SpotResponse;
import kr.inuappcenter.spotinu.domain.spot.entity.Spot;
import kr.inuappcenter.spotinu.global.response.PageResponseDto;
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
  PageResponseDto<SpotResponse> getAllSpots(int page,
                                 int size
  );

  /**
   * 장소 검색(필터)
   * @param spotFilterRequest
   * @param page
   * @param size
   * @return
   */
  PageResponseDto<SpotResponse> searchSpots(SpotFilterRequest spotFilterRequest,
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

  /**
   * 장소 다운로드 - 로컬 캐시
   * @return
   */
  List<SpotDownLoadResponse> downloadAllSpots();

  Spot getSpotProxy(Long spotId);
  String generateETag(List<SpotDownLoadResponse> spots);
}
