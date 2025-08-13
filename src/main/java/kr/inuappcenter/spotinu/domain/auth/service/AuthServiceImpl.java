package kr.inuappcenter.spotinu.domain.auth.service;

import kr.inuappcenter.spotinu.domain.auth.dto.request.LoginRequestDto;
import kr.inuappcenter.spotinu.domain.auth.dto.request.SignupRequestDto;
import kr.inuappcenter.spotinu.domain.member.entity.CustomUserDetails;
import kr.inuappcenter.spotinu.domain.member.entity.Member;
import kr.inuappcenter.spotinu.domain.member.exception.MemberException;
import kr.inuappcenter.spotinu.domain.member.mapper.MemberMapper;
import kr.inuappcenter.spotinu.domain.member.repository.MemberRepository;
import kr.inuappcenter.spotinu.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kr.inuappcenter.spotinu.domain.member.exception.MemberErrorCode.DUPLICATE_STUDENT_NUMBER;
import static kr.inuappcenter.spotinu.domain.member.exception.MemberErrorCode.USER_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final MemberRepository memberRepository;
  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;
  private final PasswordEncoder passwordEncoder;
  private final MemberMapper memberMapper;

  @Override
  public void signUp(SignupRequestDto requestDto) {

    validateStudentNumber(requestDto.getStudentNumber());

    String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

    Member member = memberMapper.toEntityWithEncodedPassword(requestDto, encodedPassword);
    memberRepository.save(member);
  }

  @Override
  @Transactional(readOnly = true)
  public String login(LoginRequestDto requestDto) {
    UsernamePasswordAuthenticationToken authenticationToken =
      new UsernamePasswordAuthenticationToken(requestDto.getStudentNumber(), requestDto.getPassword());

    authenticationManager.authenticate(authenticationToken);

    // 인증이 성공하면 JWT 토큰을 생성하여 반환
    CustomUserDetails userDetails = memberRepository.findByStudentNumber(requestDto.getStudentNumber())
      .map(CustomUserDetails::new)
      .orElseThrow(() -> new MemberException(USER_NOT_FOUND));

    return jwtTokenProvider.generateToken(userDetails);
  }

  @Override
  public void delete(Long id) {
    Member member = memberRepository.findById(id)
      .orElseThrow(() -> new MemberException(USER_NOT_FOUND));
    memberRepository.delete(member);
  }

  @Override
  @Transactional(readOnly = true)
  public void logout(String token) {

  }

  private void validateStudentNumber(Long studentNumber) {
    if (memberRepository.existsByStudentNumber(studentNumber)) throw new MemberException(DUPLICATE_STUDENT_NUMBER);
  }
}
