package kr.inuappcenter.spotinu.domain.review.mapper;

import kr.inuappcenter.spotinu.domain.review.dto.response.ReviewPhotoResponse;
import kr.inuappcenter.spotinu.domain.review.entity.ReviewPhoto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewPhotoMapper {

  ReviewPhotoResponse toReviewPhotoResponse(ReviewPhoto reviewPhoto);
  List<ReviewPhotoResponse> toReviewPhotoResponseList(List<ReviewPhoto> reviewPhotos);
}
