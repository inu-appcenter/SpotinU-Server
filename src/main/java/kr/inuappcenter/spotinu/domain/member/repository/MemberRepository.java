package kr.inuappcenter.spotinu.domain.member.repository;

import kr.inuappcenter.spotinu.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository <Member, Long>{
  Optional<Member> findByStudentNumber(Long StudentNumber);
  boolean existsByStudentNumber(Long studentNumber);
  Page<Member> findAll(Pageable pageable);
}
