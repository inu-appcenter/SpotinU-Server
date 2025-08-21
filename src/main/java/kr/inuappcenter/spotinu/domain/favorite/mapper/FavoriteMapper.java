package kr.inuappcenter.spotinu.domain.favorite.mapper;

import kr.inuappcenter.spotinu.domain.favorite.dto.response.FavoriteDetailResponse;
import kr.inuappcenter.spotinu.domain.favorite.dto.response.FavoriteResponse;
import kr.inuappcenter.spotinu.domain.favorite.entity.Favorite;
import kr.inuappcenter.spotinu.domain.spot.entity.SpotPhoto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FavoriteMapper {

  @Mapping(source = "spot.latitude", target = "latitude")
  @Mapping(source = "spot.longitude", target = "longitude")
  FavoriteResponse toResponse(Favorite favorite);

  @Mapping(source = "spot.id", target = "id")
  @Mapping(source = "spot.name", target = "name")
  @Mapping(source = "spot.locationDetail", target = "locationDetail")
  @Mapping(source = "spot.businessHours", target = "businessHours")
  @Mapping(target = "url", expression = "java(getThumbnailUrl(favorite))")
  FavoriteDetailResponse toDetailResponse(Favorite favorite);

  // Spot의 썸네일 URL 가져오기 헬퍼
  default String getThumbnailUrl(Favorite favorite) {
    return favorite.getSpot().getPhotos().stream()
      .filter(SpotPhoto::isThumbnail)
      .findFirst()
      .map(SpotPhoto::getUrl)
      .orElse(null);
  }

}
