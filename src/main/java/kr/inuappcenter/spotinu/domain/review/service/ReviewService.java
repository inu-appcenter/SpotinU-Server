package kr.inuappcenter.spotinu.domain.review.service;

import kr.inuappcenter.spotinu.domain.member.entity.CustomUserDetails;
import kr.inuappcenter.spotinu.domain.review.dto.request.ReviewCreateRequest;
import kr.inuappcenter.spotinu.domain.review.dto.response.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewService {

  /**
   * 후기 작성
   * @param memberId
   * @param spotId
   * @param reviewCreateRequest
   * @param photos
   */
  void create(Long memberId,
              Long spotId,
              ReviewCreateRequest reviewCreateRequest,
              List<MultipartFile> photos
  );
  // todo : 후기 수정

  /**
   * 후기 삭제
   * @param userDetails
   * @param reviewId
   */
  void delete(CustomUserDetails userDetails,
              Long reviewId
  );

  /**
   * 후기 조회(내가 쓴)
   * @param memberId
   */
  Page<ReviewResponse> getMyReviews(Long memberId,
                                    int page,
                                    int size
  );

  /**
   * Spot 후기 조회
   */
  Page<ReviewResponse> getReviews(Long spotId,
                                  int page,
                                  int size
  );
}
