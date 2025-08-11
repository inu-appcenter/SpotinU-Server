package kr.inuappcenter.spotinu.global.exception;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {
  HttpStatus getStatus();
  String getMessage();
}

