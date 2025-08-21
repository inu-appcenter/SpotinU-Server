package kr.inuappcenter.spotinu.domain.favorite.repository;

import kr.inuappcenter.spotinu.domain.favorite.entity.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

  // 특정 회원과 특정 스팟에 대한 즐겨찾기 조회
  Optional<Favorite> findByMemberIdAndSpotId(Long memberId, Long spotId);

  // 특정 회원의 즐겨찾기 목록 조회 (페이징)
  List<Favorite> findByMemberId(Long memberId);

  // 존재 여부 체크
  boolean existsByMemberIdAndSpotId(Long memberId, Long spotId);

  Page<Favorite> findByMemberIdAndSpotIdIn(Long memberId, List<Long> ids, Pageable pageable);
}
