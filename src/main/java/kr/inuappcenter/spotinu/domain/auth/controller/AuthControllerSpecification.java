package kr.inuappcenter.spotinu.domain.auth.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.inuappcenter.spotinu.domain.auth.dto.request.LoginRequestDto;
import kr.inuappcenter.spotinu.domain.auth.dto.request.SignupRequestDto;
import kr.inuappcenter.spotinu.domain.member.entity.CustomUserDetails;
import kr.inuappcenter.spotinu.global.response.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "auth", description = "Auth API")
public interface AuthControllerSpecification {

  /**
   * 회원 가입
   * @param requestDto
   * @return
   */
  @PostMapping("/signup")
  ResponseEntity<ResponseDto<Void>> signUp(@Valid @RequestBody SignupRequestDto requestDto);

  /**
   * 로그인
   * @param requestDto
   * @return
   */
  @PostMapping("/login")
  ResponseEntity<ResponseDto<String>> login(@Valid @RequestBody LoginRequestDto requestDto);

  /**
   * 회원 탈퇴
   * @param userDetails
   * @return
   */
  @DeleteMapping("/delete")
  ResponseEntity<ResponseDto<Void>> delete(@AuthenticationPrincipal CustomUserDetails userDetails);
}
