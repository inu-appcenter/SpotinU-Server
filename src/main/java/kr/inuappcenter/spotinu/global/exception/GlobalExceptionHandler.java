package kr.inuappcenter.spotinu.global.exception;

import kr.inuappcenter.spotinu.global.response.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.HashMap;
import java.util.Map;

import static kr.inuappcenter.spotinu.global.exception.CommonErrorCode.*;
import static kr.inuappcenter.spotinu.domain.auth.exception.AuthErrorCode.*;
import static kr.inuappcenter.spotinu.domain.member.exception.MemberErrorCode.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

  /** 유효성 검사 실패 처리 */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ResponseDto<?>> handleValidationException(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors()
      .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

    return ResponseEntity
      .status(INVALID_INPUT.getStatus())
      .body(ResponseDto.error(
        INVALID_INPUT.getStatus().value(),
        INVALID_INPUT.getMessage(),
        errors
        ));
  }

  /** 로그인 시 비밀번호가 틀렸거나 인증 정보가 잘못된 경우 처리 */
  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ResponseDto<?>> handleBadCredentials(BadCredentialsException ex) {
    return ResponseEntity
      .status(UNAUTHORIZED_401.getStatus())
      .body(ResponseDto.error(
        UNAUTHORIZED_401.getStatus().value(),
        UNAUTHORIZED_401.getMessage()
      ));
  }

  /** 로그인 시 존재하지 않는 이메일(사용자)로 로그인 시도한 경우 처리 */
  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<ResponseDto<?>> handleUsernameNotFound(UsernameNotFoundException ex) {
    return ResponseEntity
      .status(USER_NOT_FOUND.getStatus())
      .body(ResponseDto.error(
        USER_NOT_FOUND.getStatus().value(),
        USER_NOT_FOUND.getMessage()
      ));
  }

  /** 계정이 비활성화 되었거나(Disabled) 잠긴 경우(Locked) 처리 */
  @ExceptionHandler({DisabledException.class, LockedException.class})
  public ResponseEntity<ResponseDto<?>> handleAccountStatus(AuthenticationException ex) {
    return ResponseEntity
      .status(UNAUTHORIZED_400.getStatus())
      .body(ResponseDto.error(
        UNAUTHORIZED_400.getStatus().value(),
        UNAUTHORIZED_400.getMessage()
      ));
  }

  /** request part, multipart file 사용할 때 max file size*/
  @ExceptionHandler(MaxUploadSizeExceededException.class)
  public ResponseEntity<ResponseDto<?>> handleMaxSizeException(MaxUploadSizeExceededException ex) {
    return ResponseEntity
      .status(PAYLOAD_TOO_LARGE.getStatus())
      .body(ResponseDto.error(
        PAYLOAD_TOO_LARGE.getStatus().value(),
        PAYLOAD_TOO_LARGE.getMessage()
      ));
  }

  /** 공통 CustomException 처리 */
  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ResponseDto<?>> handleCustomException(CustomException ex) {
    BaseErrorCode code = ex.getBaseErrorCode();
    return ResponseEntity
      .status(code.getStatus())
      .body(ResponseDto.error(
        code.getStatus().value(),
        code.getMessage()
      ));
  }

  /** 예상 못한 모든 예외 처리 */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ResponseDto<?>> handleAll(Exception ex) {
    return ResponseEntity
      .status(INTERNAL_SERVER_ERROR.getStatus())
      .body(ResponseDto.error(
        INTERNAL_SERVER_ERROR.getStatus().value(),
        INTERNAL_SERVER_ERROR.getMessage() + ex.getMessage()
      ));
  }
}
