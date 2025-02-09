package com.ll.hfback.domain.member.member.controller;

import com.ll.hfback.domain.member.member.dto.FriendDto;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.domain.member.member.service.FriendService;
import com.ll.hfback.global.rsData.RsData;
import com.ll.hfback.global.webMvc.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/friends")
public class ApiV1FriendController {

  private final FriendService friendService;


  public record FriendRequestDto(
      Long receiverId
  ) {}

  // 친구 신청하기
  @PostMapping("/friend-requests")
  public RsData<Void> sendFriendRequest(
      @RequestBody FriendRequestDto request, @LoginUser Member loginUser
  ) {
    friendService.sendFriendRequest(loginUser.getId(), request.receiverId);
    return new RsData<>("200", "친구 신청을 보냈습니다.");
  }


  // 친구 신청 취소하기
  @DeleteMapping("/friend-requests/{requestId}")
  public RsData<Void> cancelFriendRequest(
      @PathVariable Long requestId, @LoginUser Member loginUser
  ) {
    friendService.cancelFriendRequest(requestId, loginUser.getId());
    return new RsData<>("200", "친구 신청을 취소했습니다.");
  }


  // 받은 친구 신청 수락하기
  @PostMapping("/friend-requests/{requestId}/accept")
  public RsData<Void> acceptFriendRequest(
      @PathVariable Long requestId, @LoginUser Member loginUser
  ) {
    friendService.acceptFriendRequest(requestId, loginUser.getId());
    return new RsData<>("200", "친구 신청을 수락했습니다.");
  }


  // 받은 친구 신청 거절하기
  @PostMapping("/friend-requests/{requestId}/reject")
  public RsData<Void> rejectFriendRequest(
      @PathVariable Long requestId, @LoginUser Member loginUser
  ) {
    friendService.rejectFriendRequest(requestId, loginUser.getId());
    return new RsData<>("200", "친구 신청을 거절했습니다.");
  }


  // 친구 삭제하기
  @DeleteMapping("/{friendId}")
  public RsData<Void> deleteFriend(
      @PathVariable Long friendId, @LoginUser Member loginUser
  ) {
    friendService.deleteFriend(friendId, loginUser.getId());
    return new RsData<>("200", "친구를 삭제했습니다.");
  }


  // 사용자별 친구 목록 (양쪽 수락)
  @GetMapping
  public RsData<List<FriendDto>> getFriendList(@LoginUser Member loginUser) {
    List<FriendDto> friendsList = friendService.getFriendList(loginUser.getId());

    return new RsData<>("200", "친구 목록을 조회했습니다.", friendsList);
  }


  // 사용자별 수락 대기 중인 친구 신청 목록
  @GetMapping("/friend-requests")
  public RsData<List<FriendDto>> getPendingRequests(@LoginUser Member loginUser) {
    List<FriendDto> pendingList = friendService.getPendingRequests(loginUser.getId());

    return new RsData<>("200", "친구 신청 목록을 조회했습니다.", pendingList);
  }

  // 사용자별 보낸 친구 신청 목록
  @GetMapping("/self-friend-requests")
  public RsData<List<FriendDto>> getSentRequests(@LoginUser Member loginUser) {
    List<FriendDto> sentList = friendService.getSentRequests(loginUser.getId());

    return new RsData<>("200", "친구 신청 목록을 조회했습니다.", sentList);
  }
}
