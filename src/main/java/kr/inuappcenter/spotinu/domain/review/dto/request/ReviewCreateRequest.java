package kr.inuappcenter.spotinu.domain.review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.inuappcenter.spotinu.domain.review.entity.SpotKeyword;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Schema(description = "리뷰 생성 요청 DTO")
public record ReviewCreateRequest(
  @NotBlank(message = "리뷰 내용은 필수입니다.")
  @Schema(description = "리뷰 내용", example = "조용하고 공부하기 좋은 공간이에요.")
  String content,

  @NotNull(message = "방문 날짜는 필수입니다.")
  @Schema(description = "방문 날짜", example = "2025-08-14")
  LocalDate visitDate,

  @Schema(description = "방문 시간", example = "14:30")
  LocalTime visitTime,

  @Schema(description = "방문 키워드 리스트", example = "[\"COMFORTABLE_SEATING\", \"COZY\"]")
  Set<SpotKeyword> keywords
) {}
