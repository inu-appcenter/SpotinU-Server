package kr.inuappcenter.spotinu.domain.spot.mapper;

import kr.inuappcenter.spotinu.domain.spot.dto.request.SpotCreateRequest;
import kr.inuappcenter.spotinu.domain.spot.dto.response.SpotDetailResponse;
import kr.inuappcenter.spotinu.domain.spot.dto.response.SpotResponse;
import kr.inuappcenter.spotinu.domain.spot.entity.Spot;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {SpotPhotoMapper.class})
public interface SpotMapper {

  SpotResponse toResponse(Spot spot);
  SpotDetailResponse toDetailResponse(Spot spot);
  Spot toEntity(SpotCreateRequest spotCreateRequest);
}
