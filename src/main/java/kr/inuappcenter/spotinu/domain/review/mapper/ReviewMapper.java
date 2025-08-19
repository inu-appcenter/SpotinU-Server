package kr.inuappcenter.spotinu.domain.review.mapper;

import kr.inuappcenter.spotinu.domain.member.entity.Member;
import kr.inuappcenter.spotinu.domain.review.dto.request.ReviewCreateRequest;
import kr.inuappcenter.spotinu.domain.review.dto.response.ReviewResponse;
import kr.inuappcenter.spotinu.domain.review.entity.Review;
import kr.inuappcenter.spotinu.domain.spot.entity.Spot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ReviewPhotoMapper.class})
public interface ReviewMapper {

  @Mapping(source = "member.name", target = "memberName")
  ReviewResponse toReviewResponse(Review review);

  List<ReviewResponse> toReviewResponses(List<Review> reviews);

  Review toEntity(Member member, Spot spot, ReviewCreateRequest request);
}
