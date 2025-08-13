package kr.inuappcenter.spotinu.domain.spot.mapper;

import kr.inuappcenter.spotinu.domain.spot.dto.response.SpotPhotoResponse;
import kr.inuappcenter.spotinu.domain.spot.entity.SpotPhoto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpotPhotoMapper {

  SpotPhotoResponse toResponse(SpotPhoto photo);
  List<SpotPhotoResponse> toResponseList(List<SpotPhoto> photos);
}
