package com.ll.hfback.domain.member.alert.service;

import com.ll.hfback.domain.member.alert.repository.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertRepository alertRepository;
}
