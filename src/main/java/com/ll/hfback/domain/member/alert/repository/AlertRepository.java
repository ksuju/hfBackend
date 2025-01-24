package com.ll.hfback.domain.member.alert.repository;


import com.ll.hfback.domain.member.alert.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Long> {
}
