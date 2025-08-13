package kr.inuappcenter.spotinu.domain.member.mapper;

import kr.inuappcenter.spotinu.domain.auth.dto.request.SignupRequestDto;
import kr.inuappcenter.spotinu.domain.member.entity.Member;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface MemberMapper {

  default Member toEntityWithEncodedPassword(SignupRequestDto dto, String encodedPassword) {
    if (dto == null) {
      return null;
    }
    return Member.create(dto.getName(), dto.getStudentNumber(), encodedPassword, dto.getRole());
  }
}

