package kr.inuappcenter.spotinu.domain.spot.service;

import kr.inuappcenter.spotinu.domain.common.storage.exception.StorageException;
import kr.inuappcenter.spotinu.domain.common.storage.service.S3FileStorageService;
import kr.inuappcenter.spotinu.domain.review.dto.response.ReviewResponse;
import kr.inuappcenter.spotinu.domain.review.mapper.ReviewMapper;
import kr.inuappcenter.spotinu.domain.spot.dto.request.SpotCreateRequest;
import kr.inuappcenter.spotinu.domain.spot.dto.request.SpotFilterRequest;
import kr.inuappcenter.spotinu.domain.spot.dto.response.SpotDetailResponse;
import kr.inuappcenter.spotinu.domain.spot.dto.response.SpotDownLoadResponse;
import kr.inuappcenter.spotinu.domain.spot.dto.response.SpotResponse;
import kr.inuappcenter.spotinu.domain.spot.entity.Spot;
import kr.inuappcenter.spotinu.domain.spot.entity.SpotPhoto;
import kr.inuappcenter.spotinu.domain.spot.exception.SpotException;
import kr.inuappcenter.spotinu.domain.spot.mapper.SpotMapper;
import kr.inuappcenter.spotinu.domain.spot.repository.SpotRepository;
import kr.inuappcenter.spotinu.global.response.PageResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static kr.inuappcenter.spotinu.domain.common.storage.exception.StorageErrorCode.S3_UPLOAD_FAIL;
import static kr.inuappcenter.spotinu.domain.spot.exception.SpotErrorCode.SPOT_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SpotServiceImpl implements SpotService {

  private final SpotRepository spotRepository;
  private final SpotMapper spotMapper;
  private final ReviewMapper reviewMapper;
  private final S3FileStorageService s3FileStorageService;

  @Override
  @Transactional(readOnly = true)
  @Cacheable(
    value = "spots",
    key = "'page_'+#page+'_size_'+#size",
    cacheManager = "cacheManager"
  )
  public PageResponseDto<SpotResponse> getAllSpots(int page, int size) {

    log.info("Fetching all spots - page: {}, size: {}", page, size);
    Pageable pageable = PageRequest.of(page, size);
    Page<Spot> spots = spotRepository.findAllWithPhotos(pageable);

    spots.forEach(spot -> spot.getPhotos().size()); // Lazy 초기화

    log.info("Fetched {} spots", spots.getNumberOfElements());
    return PageResponseDto.from(spots.map(spotMapper::toResponse));
  }

  @Override
  @Transactional(readOnly = true)
  @Cacheable(
    value = "spots",
    key = "'filter::sleep:' + #spotFilterRequest.sleepingAllowed + " +
      "'|eat:' + #spotFilterRequest.eatingAllowed + " +
      "'|outlet:' + #spotFilterRequest.hasPowerOutlet + " +
      "'|study:' + #spotFilterRequest.studyAllowed + " +
      "'|entertainment:' + #spotFilterRequest.entertainment + " +
      "'|reservation:' + #spotFilterRequest.reservationRequired + " +
      "'|type:' + #spotFilterRequest.placeType + " +
      "'|page:' + #page + '_size:' + #size",
    cacheManager = "cacheManager"  // TTL 적용 가능한 CacheManager 사용
  )
  public PageResponseDto<SpotResponse> searchSpots(SpotFilterRequest spotFilterRequest, int page, int size) {

    log.info("Searching spots with filters {} - page: {}, size: {}", spotFilterRequest, page, size);
    Pageable pageable = PageRequest.of(page, size);
    Page<Spot> spots = spotRepository.searchSpots(
      spotFilterRequest.getSleepingAllowed(),
      spotFilterRequest.getEatingAllowed(),
      spotFilterRequest.getHasPowerOutlet(),
      spotFilterRequest.getStudyAllowed(),
      spotFilterRequest.getEntertainment(),
      spotFilterRequest.getReservationRequired(),
      spotFilterRequest.getPlaceType(),
      pageable
      );

    spots.forEach(spot -> spot.getPhotos().size()); // Lazy 초기화

    log.info("Search returned {} spots", spots.getNumberOfElements());
    return PageResponseDto.from(spots.map(spotMapper::toResponse));
  }

  @Override
  @Transactional(readOnly = true)
  public SpotDetailResponse getSpotDetail(Long spotId) {
    log.info("Getting spot detail for spotId: {}", spotId);
    Spot spot = spotRepository.findById(spotId)
      .orElseThrow(() -> {
        log.warn("Spot not found for id: {}", spotId);
        return new SpotException(SPOT_NOT_FOUND);
      });

    SpotDetailResponse response = spotMapper.toDetailResponse(spot);

    List<ReviewResponse> reviewResponses = reviewMapper.toReviewResponses(spot.getReviews());
    response.setReviews(reviewResponses);
    log.info("Spot detail prepared with {} reviews", reviewResponses.size());

    return response;
  }

  @Override
  @CacheEvict(value = "spots", allEntries = true)
  public void create(Long memberId, SpotCreateRequest spotCreateRequest, List<MultipartFile> photos) {
    log.info("Creating new spot by memberId: {}", memberId);
    // todo : member 조회, Admin인지 확인.

    Spot spot = spotMapper.toEntity(spotCreateRequest);
    log.debug("Mapped Spot entity from SpotCreateRequest: {}", spot);

    if (photos != null && !photos.isEmpty()) {
      int index = 1;
      for (MultipartFile photoFile : photos) {
        try {
          String photoUrl = s3FileStorageService.uploadImage(photoFile);
          log.debug("Uploaded photo {} URL: {}", index, photoUrl);

          SpotPhoto spotPhoto = SpotPhoto.builder()
            .url(photoUrl)
            .thumbnail(index == 1)
            .orderIndex(index++)
            .build();

          spot.addPhoto(spotPhoto);
        } catch (Exception e) {
          log.error("Failed to upload photo at index {} for spot creation by memberId {}", index, memberId, e);
          throw new StorageException(S3_UPLOAD_FAIL, e);
        }
      }
    } else {
      log.info("No photos uploaded for spot creation by memberId: {}", memberId);
    }

    spotRepository.save(spot);
    log.info("Spot created successfully: {}", spot.getName());
  }

  @Override
  public void delete(Long spotId) {
    Spot spot = spotRepository.findById(spotId)
      .orElseThrow(() -> {
        log.warn("Spot not found for id: {}", spotId);
        return new SpotException(SPOT_NOT_FOUND);
      });

    spotRepository.delete(spot);
    log.info("Spot deleted successfully: {}", spot.getName());
  }

  @Override
  public List<SpotDownLoadResponse> downloadAllSpots() {
    log.info("Fetching all spots from database");
    List<Spot> spots = spotRepository.findAll();
    log.info("Found {} spots", spots.size());
    return spotMapper.toDownloadResponseList(spots);
  }

  @Override
  @Transactional(readOnly = true)
  public Spot getSpotProxy(Long spotId) {
    return spotRepository.getReferenceById(spotId); // DB 조회는 실제로 필드 접근할 때 발생
  }

  @Override
  public String generateETag(List<SpotDownLoadResponse> spots) {
    // 간단하게 JSON hashCode 기반
    String json = spots.toString();
    String eTag = "\"" + Integer.toHexString(json.hashCode()) + "\"";
    log.info("Generated ETag={}", eTag);
    return eTag;
  }
}
