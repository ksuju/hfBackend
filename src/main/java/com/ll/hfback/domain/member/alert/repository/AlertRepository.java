package com.ll.hfback.domain.member.alert.repository;


import com.ll.hfback.domain.member.alert.entity.Alert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Long> {
  Page<Alert> findByMemberId(Long memberId, Pageable pageable);

  Page<Alert> findByMemberIdAndIsReadFalse(Long memberId, Pageable pageable);
}
