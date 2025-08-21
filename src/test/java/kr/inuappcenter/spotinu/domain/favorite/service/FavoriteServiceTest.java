//package kr.inuappcenter.spotinu.domain.favorite.service;
//
//import kr.inuappcenter.spotinu.domain.favorite.mapper.FavoriteMapper;
//import kr.inuappcenter.spotinu.domain.favorite.repository.FavoriteRepository;
//import kr.inuappcenter.spotinu.domain.member.entity.CustomUserDetails;
//import kr.inuappcenter.spotinu.domain.member.entity.Member;
//import kr.inuappcenter.spotinu.domain.member.repository.MemberRepository;
//import kr.inuappcenter.spotinu.domain.spot.entity.Spot;
//import kr.inuappcenter.spotinu.domain.spot.repository.SpotRepository;
//import kr.inuappcenter.spotinu.domain.spot.service.SpotService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//
//import static kr.inuappcenter.spotinu.domain.member.entity.Role.ADMIN;
//import static kr.inuappcenter.spotinu.domain.spot.entity.PlaceType.INDOOR;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@ActiveProfiles("test")
//@Transactional
//class FavoriteServiceTest {
//
//  @Autowired private FavoriteRepository favoriteRepository;
//  @Autowired private FavoriteService favoriteService;
//  @Autowired private FavoriteMapper favoriteMapper;
//  @Autowired private SpotService spotService;
//  @Autowired private MemberRepository memberRepository;
//  @Autowired private SpotRepository spotRepository;
//
//  private Member memberAdmin;
//  private Spot spot;
//  private CustomUserDetails userDetails;
//
//
//  @BeforeEach
//  void setUp() {
//    memberAdmin = memberRepository.save(Member.builder()
//      .studentNumber(202001669L)
//      .role(ADMIN)
//      .password("qwerqwer1@")
//      .name("Frozzun")
//      .build()
//    );
//    memberRepository.flush();
//
//    spot = spotRepository.save(Spot.builder()
//      .latitude("37.123")
//      .longitude("127.123")
//      .name("도서관")
//      .locationDetail("1호관 101호")
//      .description("테스트 장소")
//      .businessHours("09:00-18:00")
//      .sleepingAllowed(false)
//      .eatingAllowed(true)
//      .hasPowerOutlet(true)
//      .studyAllowed(true)
//      .entertainment(false)
//      .reservationRequired(false)
//      .placeType(INDOOR)
//      .build()
//    );
//    memberRepository.flush();
//
//    // 실제 로그인 과정을 거치지 않으니까, 직접 CustomUserDetails를 만들어서 주입
//    userDetails = new CustomUserDetails(memberAdmin);
//  }
//
//  @Test
//  @DisplayName("즐겨찾기 토글 성공 : 즐겨찾기 하기")
//  void successToggleFavoriteTrue() throws Exception {
//    boolean result = favoriteService.toggleFavorite(userDetails, spot.getId(), "메모");
//
//    // 새로 추가된 경우 true 가 리턴
//    assertTrue(result);
//
//    // 실제 DB에 해당 조합이 존재하는지 확인
//    assertTrue(favoriteRepository.existsByMemberIdAndSpotId(memberAdmin.getId(), spot.getId()));
//  }
//
//  @Test
//  @DisplayName("즐겨찾기 토글 성공 : 즐겨찾기 취소")
//  void successToggleFavoriteFalse() throws Exception {
//    // 먼저 등록
//    favoriteService.toggleFavorite(userDetails, spot.getId(), "메모");
//
//    boolean result = favoriteService.toggleFavorite(userDetails, spot.getId(), "메모");
//    assertFalse(result);
//    assertFalse(favoriteRepository.existsByMemberIdAndSpotId(memberAdmin.getId(), spot.getId()));
//  }
//
//  @Test
//  @DisplayName("")
//  void getMyFavorite() throws Exception {
//  }
//}