package kr.inuappcenter.spotinu.domain.spot.mapper;

import kr.inuappcenter.spotinu.domain.favorite.entity.Favorite;
import kr.inuappcenter.spotinu.domain.spot.dto.request.SpotCreateRequest;
import kr.inuappcenter.spotinu.domain.spot.dto.response.SpotDetailResponse;
import kr.inuappcenter.spotinu.domain.spot.dto.response.SpotDownLoadResponse;
import kr.inuappcenter.spotinu.domain.spot.dto.response.SpotResponse;
import kr.inuappcenter.spotinu.domain.spot.entity.Spot;
import kr.inuappcenter.spotinu.domain.spot.entity.SpotPhoto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SpotPhotoMapper.class})
public interface SpotMapper {

  @Mapping(target = "photo", expression = "java(getThumbnailUrl(spot))")
  SpotResponse toResponse(Spot spot);
  SpotDetailResponse toDetailResponse(Spot spot);
  Spot toEntity(SpotCreateRequest spotCreateRequest);
  SpotDownLoadResponse toDownloadResponse(Spot spot);
  List<SpotDownLoadResponse> toDownloadResponseList(List<Spot> spots);

  // Spot의 썸네일 URL 가져오기 헬퍼
  default String getThumbnailUrl(Spot spot) {
    return spot.getPhotos().stream()
      .filter(SpotPhoto::isThumbnail)
      .findFirst()
      .map(SpotPhoto::getUrl)
      .orElse(null);
  }
}
