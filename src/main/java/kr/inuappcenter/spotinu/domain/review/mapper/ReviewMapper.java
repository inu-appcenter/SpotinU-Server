package kr.inuappcenter.spotinu.domain.review.mapper;

import kr.inuappcenter.spotinu.domain.review.dto.response.ReviewResponse;
import kr.inuappcenter.spotinu.domain.review.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ReviewPhotoMapper.class})
public interface ReviewMapper {

  @Mapping(source = "member.name", target = "memberName")
  ReviewResponse toReviewResponse(Review review);

  List<ReviewResponse> toReviewResponses(List<Review> reviews);
}
