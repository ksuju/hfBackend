package com.ll.hfback.domain.member.alert.events;

import com.ll.hfback.domain.member.alert.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlertEventHandler {

  private final AlertService alertService;


  @Async
  @EventListener
  public void handleAlert(AlertEvent event) {
    event.getReceiverIds().forEach(receiverId ->
        alertService.send(
            receiverId,
            event.getAlertType(),
            event.getNavigationData(),
            event.getMessageArgs()
        )
    );
  }
}
