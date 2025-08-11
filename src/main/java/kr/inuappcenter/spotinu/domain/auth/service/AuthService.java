package kr.inuappcenter.spotinu.domain.auth.service;
import kr.inuappcenter.spotinu.domain.auth.dto.request.LoginRequestDto;
import kr.inuappcenter.spotinu.domain.auth.dto.request.SignupRequestDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

public interface AuthService {

  /**
   * 회원가입
   * @param requestDto
   */
  void signUp(SignupRequestDto requestDto);

  /**
   * 로그인
   * @param requestDto
   * @return token
   */
  String login(LoginRequestDto requestDto);

  /**
   * 회원 탈퇴
   * @param id
   */
  void delete(Long id);

  /**
   * 로그아웃
   * @param token
   */
  void logout(String token);

}
