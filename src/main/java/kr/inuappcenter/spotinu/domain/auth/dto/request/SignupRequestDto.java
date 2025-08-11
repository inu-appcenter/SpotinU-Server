package kr.inuappcenter.spotinu.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.inuappcenter.spotinu.domain.member.entity.Role;
import lombok.Getter;

@Getter
@Schema(description = "회원 가입 DTO")
public class SignupRequestDto {

  @Schema(
    description = "이름",
    example = "홍길동"
  )
  @NotBlank(message = "이름은 필수 입력 값입니다.")
  private String name;

  @Schema(
    description = "학번",
    example = "202012345"
  )
  @NotNull(message = "학번 입력은 필수입니다.")
  private Long studentNumber;

  @Schema(
    description = "비밀번호",
    example = "qwerqwer1@"
  )
  @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
  // @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,20}$")
  @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
  private String password;

  @Schema(
    description = "사용자/어드민",
    example = "USER/ADMIN"
  )
  @Enumerated(EnumType.STRING)
  private Role role;
}
