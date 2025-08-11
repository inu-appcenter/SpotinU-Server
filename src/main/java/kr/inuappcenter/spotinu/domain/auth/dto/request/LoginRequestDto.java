package kr.inuappcenter.spotinu.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "유저 로그인 DTO")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

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
  @NotNull(message = "패스워드 입력은 필수입니다.")
  private String password;
}
