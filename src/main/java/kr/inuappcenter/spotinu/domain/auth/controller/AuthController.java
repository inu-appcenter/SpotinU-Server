package kr.inuappcenter.spotinu.domain.auth.controller;

import kr.inuappcenter.spotinu.domain.auth.dto.request.LoginRequestDto;
import kr.inuappcenter.spotinu.domain.auth.dto.request.SignupRequestDto;
import kr.inuappcenter.spotinu.domain.auth.service.AuthService;
import kr.inuappcenter.spotinu.domain.member.entity.CustomUserDetails;
import kr.inuappcenter.spotinu.global.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController implements AuthControllerSpecification{

  private final AuthService authService;

  @Override
  public ResponseEntity<ResponseDto<Void>> signUp(SignupRequestDto requestDto) {
    authService.signUp(requestDto);
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(ResponseDto.success("유저 등록 성공"));
  }

  @Override
  public ResponseEntity<ResponseDto<String>> login(LoginRequestDto requestDto) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(ResponseDto.success("유저 로그인 성공", authService.login(requestDto)));
  }

  @Override
  public ResponseEntity<ResponseDto<Void>> delete(CustomUserDetails userDetails) {
    authService.delete(userDetails.getId());
    return ResponseEntity
      .status(HttpStatus.NO_CONTENT)
      .body(ResponseDto.success("유저 삭제 성공"));
  }
}
