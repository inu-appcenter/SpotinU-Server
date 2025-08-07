package kr.inuappcenter.spotinu.domain.review.entity;

import lombok.Getter;

@Getter
public enum SpotKeyword {

  COZY("아늑해요"),
  COMFORTABLE_SEATING("좌석이 편해요"),
  NICE_VIEW("뷰가 좋아요"),
  CLEAN_SPACE("공간이 청결해요"),
  STYLISH_INTERIOR("인테리어가 멋져요"),
  GOOD_FOR_GROUPS("단체모임하기 좋아요"),
  GOOD_FOR_SOLO("혼밥하기 좋아요"),
  NONE("선택할 키워드가 없어요");

  private final String description;

  SpotKeyword(String description) {
    this.description = description;
  }

}

