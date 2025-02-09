package com.ll.hfback.domain.member.member.service;

import com.ll.hfback.domain.member.alert.service.AlertService;
import com.ll.hfback.domain.member.member.dto.FriendDto;
import com.ll.hfback.domain.member.member.entity.Friend;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.domain.member.member.entity.Member.MemberState;
import com.ll.hfback.domain.member.member.repository.FriendRepository;
import com.ll.hfback.domain.member.member.repository.MemberRepository;
import com.ll.hfback.global.exceptions.ErrorCode;
import com.ll.hfback.global.exceptions.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendService {

  private final FriendRepository friendRepository;
  private final MemberRepository memberRepository;
  private final AlertService alertService;


  @Transactional
  public void sendFriendRequest(Long requesterId, Long receiverId) {
    Member requester = memberRepository.findById(requesterId)
        .orElseThrow(() -> new ServiceException(ErrorCode.REQUESTER_NOT_FOUND));

    Member receiver = memberRepository.findById(receiverId)
        .orElseThrow(() -> new ServiceException(ErrorCode.RECEIVER_NOT_FOUND));

    _validateFriendRequest(requester, receiver);   // 신청 가능한 상태인지 검사

    Friend friendRequest = Friend.builder()
        .requester(requester)
        .receiver(receiver)
        .status(Friend.FriendStatus.PENDING)
        .build();
    friendRepository.save(friendRequest);
  }


  @Transactional
  public void cancelFriendRequest(Long requestId, Long requesterId) {
    Friend friend = friendRepository.findById(requestId)
        .orElseThrow(() -> new ServiceException(ErrorCode.FRIEND_REQUEST_NOT_FOUND));

    if (!friend.getRequester().getId().equals(requesterId)) {
      throw new ServiceException(ErrorCode.UNAUTHORIZED_ACCESS);
    }

    if (friend.isAccepted()) {
      throw new ServiceException(ErrorCode.ALREADY_HANDLED);
    }

    friendRepository.delete(friend);
  }


  @Transactional
  public void acceptFriendRequest(Long requestId, Long receiverId) {
    Friend friend = friendRepository.findById(requestId)
        .orElseThrow(() -> new ServiceException(ErrorCode.FRIEND_REQUEST_NOT_FOUND));

    if (!friend.getReceiver().getId().equals(receiverId)) {
      throw new ServiceException(ErrorCode.UNAUTHORIZED_ACCESS);
    }

    // 친구 수 제한 확인
    if (!friend.getRequester().canAddFriend() || !friend.getReceiver().canAddFriend()) {
      throw new ServiceException(ErrorCode.FRIEND_LIMIT_EXCEEDED);
    }

    friend.accept();
    friend.getRequester().increaseFriendCount();
    friend.getReceiver().increaseFriendCount();
  }


  @Transactional
  public void rejectFriendRequest(Long requestId, Long receiverId) {
    Friend friend = friendRepository.findById(requestId)
        .orElseThrow(() -> new ServiceException(ErrorCode.FRIEND_REQUEST_NOT_FOUND));

    if (!friend.getReceiver().getId().equals(receiverId)) {
      throw new ServiceException(ErrorCode.UNAUTHORIZED_ACCESS);
    }

    friendRepository.delete(friend);
  }


  @Transactional
  public void deleteFriend(Long friendId, Long loginUserId) {
    Friend friend = friendRepository.findById(friendId)
        .orElseThrow(() -> new ServiceException(ErrorCode.FRIEND_NOT_FOUND));

    if (!friend.getRequester().getId().equals(loginUserId) &&
        !friend.getReceiver().getId().equals(loginUserId)) {
      throw new ServiceException(ErrorCode.UNAUTHORIZED_ACCESS);
    }

    if (!friend.isAccepted()) {
      throw new ServiceException(ErrorCode.NOT_FRIEND);
    }

    friend.getRequester().decreaseFriendCount();
    friend.getReceiver().decreaseFriendCount();
    friendRepository.delete(friend);
  }


  // 나의 친구 목록 조회
  public List<FriendDto> getFriendList(Long loginUserId) {
    Member loginUser = memberRepository.findById(loginUserId)
        .orElseThrow(() -> new ServiceException(ErrorCode.MEMBER_NOT_FOUND));

    return loginUser.getFriends().stream()
        .map(friend -> FriendDto.fromAcceptedFriend(friend, loginUserId))
        .collect(Collectors.toList());
  }


  // 받은 친구 신청 목록 조회
  public List<FriendDto> getPendingRequests(Long loginUserId) {
    Member loginUser = memberRepository.findById(loginUserId)
        .orElseThrow(() -> new ServiceException(ErrorCode.MEMBER_NOT_FOUND));

    return loginUser.getPendingFriends().stream()
        .map(FriendDto::fromRequest)
        .collect(Collectors.toList());
  }


  // 보낸 친구 신청 목록 조회
  public List<FriendDto> getSentRequests(Long loginUserId) {
    Member loginUser = memberRepository.findById(loginUserId)
        .orElseThrow(() -> new ServiceException(ErrorCode.MEMBER_NOT_FOUND));

    return loginUser.getPendingRequests().stream()
        .map(FriendDto::fromRequest)
        .collect(Collectors.toList());
  }


  private void _validateFriendRequest(Member requester, Member receiver) {
    if (requester.getId().equals(receiver.getId())) {
      throw new ServiceException(ErrorCode.CANNOT_FRIEND_SELF);
    }

    if (receiver.getState() != MemberState.NORMAL) {
      throw new ServiceException(ErrorCode.INVALID_MEMBER_STATE);
    }

    // 양방향 친구 관계 체크
    Optional<Friend> friendship = friendRepository.findFriendshipBetween(requester, receiver);
    if (friendship.isPresent()) {
      Friend friend = friendship.get();
      if (friend.isAccepted()) {
        throw new ServiceException(ErrorCode.ALREADY_FRIEND);
      } else if (friend.isPending()) {
        if (friend.getRequester().equals(requester)) {
          throw new ServiceException(ErrorCode.ALREADY_REQUESTED);
        } else {
          throw new ServiceException(ErrorCode.ALREADY_RECEIVED_REQUEST);
        }
      }
    }
  }


}
