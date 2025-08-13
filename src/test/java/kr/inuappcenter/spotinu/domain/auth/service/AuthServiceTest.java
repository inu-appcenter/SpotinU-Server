//package kr.inuappcenter.spotinu.domain.auth.service;
//
//import kr.inuappcenter.spotinu.domain.auth.dto.request.LoginRequestDto;
//import kr.inuappcenter.spotinu.domain.auth.dto.request.SignupRequestDto;
//import kr.inuappcenter.spotinu.domain.member.entity.Member;
//import kr.inuappcenter.spotinu.domain.member.entity.Role;
//import kr.inuappcenter.spotinu.domain.member.exception.MemberException;
//import kr.inuappcenter.spotinu.domain.member.mapper.MemberMapper;
//import kr.inuappcenter.spotinu.domain.member.repository.MemberRepository;
//import kr.inuappcenter.spotinu.global.jwt.JwtTokenProvider;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.Optional;
//
//import static kr.inuappcenter.spotinu.domain.member.exception.StorageErrorCode.DUPLICATE_STUDENT_NUMBER;
//import static kr.inuappcenter.spotinu.domain.member.exception.StorageErrorCode.USER_NOT_FOUND;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class AuthServiceTest {
//
//  @Mock private MemberRepository memberRepository;
//  @Mock private AuthenticationManager authenticationManager;
//  @Mock private JwtTokenProvider jwtTokenProvider;
//  @Mock private PasswordEncoder passwordEncoder;
//  @Mock private MemberMapper memberMapper;
//
//  @InjectMocks private AuthService authService;
//
//  @Test
//  void signUp_success() {
//    SignupRequestDto dto = mock(SignupRequestDto.class);
//    Member member = mock(Member.class);
//
//    when(dto.getStudentNumber()).thenReturn(12345L);
//    when(memberRepository.existsByStudentNumber(12345L)).thenReturn(false);
//    when(dto.getPassword()).thenReturn("rawPassword");
//    when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");
//    // toEntityWithEncodedPassword 호출 시 mock Member 반환
//    when(memberMapper.toEntityWithEncodedPassword(dto, "encodedPassword")).thenReturn(member);
//
//    authService.signUp(dto);
//
//    // 이제 changePassword는 호출 안 됨 (팩토리 메서드에서 바로 세팅하므로)
//    verify(memberRepository).save(member);
//    verify(memberMapper).toEntityWithEncodedPassword(dto, "encodedPassword");
//  }
//
//
//  @Test
//  void signUp_duplicateStudentNumber_throwsException() {
//    SignupRequestDto dto = mock(SignupRequestDto.class);
//    when(dto.getStudentNumber()).thenReturn(12345L);
//    when(memberRepository.existsByStudentNumber(12345L)).thenReturn(true);
//
//    MemberException ex = assertThrows(MemberException.class, () -> authService.signUp(dto));
//    assertEquals(DUPLICATE_STUDENT_NUMBER, ex.getErrorCode());
//  }
//
//  @Test
//  void login_success() {
//    LoginRequestDto dto = new LoginRequestDto(12345L, "password");
//
//    Member member = Member.builder()
//      .name("홍길동")
//      .studentNumber(12345L)
//      .password("encoded-password")
//      .role(Role.USER)
//      .build();
//
//    when(jwtTokenProvider.generateToken(any())).thenReturn("token");
//    when(memberRepository.findByStudentNumber(12345L))
//      .thenReturn(Optional.of(member));
//
//    // authenticate는 void 메서드지만 예외 발생 가능하니 doNothing으로 설정
//    doNothing().when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
//
//    String token = authService.login(dto);
//
//    assertEquals("token", token);
//    verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
//  }
//
//  @Test
//  void login_userNotFound_throwsException() {
//    LoginRequestDto dto = new LoginRequestDto(12345L, "password");
//
//    doNothing().when(authenticationManager).authenticate(any());
//    when(memberRepository.findByStudentNumber(12345L)).thenReturn(Optional.empty());
//
//    MemberException ex = assertThrows(MemberException.class, () -> authService.login(dto));
//    assertEquals(USER_NOT_FOUND, ex.getErrorCode());
//  }
//
//  @Test
//  void delete_success() {
//    Member member = mock(Member.class);
//    when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
//
//    authService.delete(1L);
//
//    verify(memberRepository).delete(member);
//  }
//
//  @Test
//  void delete_userNotFound_throwsException() {
//    when(memberRepository.findById(1L)).thenReturn(Optional.empty());
//
//    MemberException ex = assertThrows(MemberException.class, () -> authService.delete(1L));
//    assertEquals(USER_NOT_FOUND, ex.getErrorCode());
//  }
//
//}