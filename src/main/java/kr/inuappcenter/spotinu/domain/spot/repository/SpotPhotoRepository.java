package kr.inuappcenter.spotinu.domain.spot.repository;

import kr.inuappcenter.spotinu.domain.spot.entity.SpotPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpotPhotoRepository extends JpaRepository<SpotPhoto, Long> {
}
