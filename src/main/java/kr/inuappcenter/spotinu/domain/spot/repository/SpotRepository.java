package kr.inuappcenter.spotinu.domain.spot.repository;

import kr.inuappcenter.spotinu.domain.spot.entity.Spot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpotRepository extends JpaRepository<Spot, Long>, SpotRepositoryCustom {
}
