package kr.inuappcenter.spotinu.domain.member.service;

import kr.inuappcenter.spotinu.domain.auth.exception.AuthException;
import kr.inuappcenter.spotinu.domain.member.exception.MemberException;
import kr.inuappcenter.spotinu.domain.member.repository.MemberRepository;
import kr.inuappcenter.spotinu.domain.member.entity.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static kr.inuappcenter.spotinu.domain.auth.exception.AuthErrorCode.INVALID_NUMBER_FORMAT;
import static kr.inuappcenter.spotinu.domain.member.exception.MemberErrorCode.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final MemberRepository memberRepository;

  @Override
  public UserDetails loadUserByUsername(String studentNumber) throws UsernameNotFoundException {

    long studentNumberLong;
    try {
      studentNumberLong = Long.parseLong(studentNumber);
    } catch (NumberFormatException e) {
      log.info("로그인 실패 - 잘못된 학번 형식: {}", studentNumber);
      throw new AuthException(INVALID_NUMBER_FORMAT);
    }

    return memberRepository.findByStudentNumber(studentNumberLong)
      .map(CustomUserDetails::new) // Entity → UserDetails 변환
      .orElseThrow(() -> {
        log.info("로그인 실패 - 존재하지 않는 학번: {}", studentNumber);
        return new MemberException(USER_NOT_FOUND);
      });
  }
}

