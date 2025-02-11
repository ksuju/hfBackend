package com.ll.hfback.domain.group.chatRoom.serviceImpl;

import com.ll.hfback.domain.festival.post.entity.Post;
import com.ll.hfback.domain.festival.post.repository.PostRepository;
import com.ll.hfback.domain.group.chat.entity.ChatRoomUser;
import com.ll.hfback.domain.group.chat.repository.ChatMessageRepository;
import com.ll.hfback.domain.group.chat.repository.ChatRoomUserRepository;
import com.ll.hfback.domain.group.chatRoom.dto.ChatRoomDto;
import com.ll.hfback.domain.group.chatRoom.dto.DetailChatRoomDto;
import com.ll.hfback.domain.group.chatRoom.entity.ChatRoom;
import com.ll.hfback.domain.group.chatRoom.form.CreateChatRoomForm;
import com.ll.hfback.domain.group.chatRoom.form.UpdateChatRoomForm;
import com.ll.hfback.domain.group.chatRoom.repository.ChatRoomRepository;
import com.ll.hfback.domain.group.chatRoom.service.ChatRoomService;
import com.ll.hfback.domain.member.alert.service.AlertEventPublisher;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.domain.member.member.repository.MemberRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final AlertEventPublisher alertEventPublisher;

    // 모든 게시글 조회
    @Override
    @Transactional
    public Page<DetailChatRoomDto> findAll(Pageable pageable) {
        Page<ChatRoom> chatRooms = chatRoomRepository.findAll(pageable);
        return chatRooms.map(this::convertToDetailChatRoomDto);
    }

    // 모임채팅방 검색
    @Override
    @Transactional
    public Page<DetailChatRoomDto> searchByKeyword(String keyword, Pageable pageable) {
        Page<ChatRoom> chatRooms = chatRoomRepository.findByRoomTitleContaining(keyword, pageable);
        return chatRooms.map(this::convertToDetailChatRoomDto);
    }

    // 해당 게시글의 모든 모임채팅방 조회
    @Override
    @Transactional(readOnly = true)
    public Page<DetailChatRoomDto> searchByFestivalId(String festivalId, Pageable pageable) {
        Page<ChatRoom> chatRooms = chatRoomRepository.findByPostFestivalId(festivalId, pageable);
        return chatRooms.map(this::convertToDetailChatRoomDto);
    }

    // ChatRoom을 ChatRoomDto로 변환하는 메서드
    private ChatRoomDto convertToChatRoomDto(ChatRoom chatRoom) {
        // festivalId를 기반으로 Festival 엔티티 조회
        String festivalName = postRepository.findByFestivalId(chatRoom.getPost().getFestivalId())
                .getFestivalName();
        // Create ChatRoomDto
        ChatRoomDto chatRoomDto = new ChatRoomDto(
                chatRoom.getMember().getId(),
                chatRoom.getId(),
                chatRoom.getRoomTitle(),
                chatRoom.getRoomContent(),
                festivalName,
                chatRoom.getRoomMemberLimit(),
                chatRoom.getJoinMemberIdNickNameList().size(),
                chatRoom.getCreateDate()
        );
        return chatRoomDto;
    }

    // DB에서 받아온 참여자명단과 대기자명단을 List<String>으로 변환하고, ChatRoom을 DetailChatRoomDto로 변환하는 메서드
    private DetailChatRoomDto convertToDetailChatRoomDto(ChatRoom chatRoom) {
        // DetailChatRoomDto 생성
        DetailChatRoomDto detailChatRoomDto = new DetailChatRoomDto(
                chatRoom.getMember().getId(),
                chatRoom.getId(),
                chatRoom.getMember().getNickname(),
                chatRoom.getRoomTitle(),
                chatRoom.getRoomContent(),
                chatRoom.getJoinMemberIdNickNameList(),
                chatRoom.getWaitingMemberIdNickNameList(),
                chatRoom.getRoomMemberLimit(),
                chatRoom.getJoinMemberIdNickNameList().size(),  // 참여자 수 전달
                chatRoom.getJoinMemberIdNickNameList().size(),  // 대기자 수 전달
                chatRoom.getCreateDate()
        );
        return detailChatRoomDto;
    }

    // 해당 게시글에 모임채팅방 생성
    @Override
    @Transactional
    public void createChatRoom(String festivalId, @Valid CreateChatRoomForm createChatRoomForm, Member loginUser) {
        Post post = postRepository.findByFestivalId(festivalId);

        // roomMemberLimit의 값이 유효한지 확인하기 (10-100)
        Long roomMemberLimit = createChatRoomForm.getRoomMemberLimit();
        if (roomMemberLimit == null || roomMemberLimit < 10 || roomMemberLimit > 100) {
            throw new IllegalArgumentException("채팅방 참여 인원수는 10-100 사이의 값이어야 합니다.");
        }

        // 사용자 검증 - 현재 로그인한 사용자의 ID를 가져오는 메서드
        Long currentUserId = loginUser.getId();
        String memberId = String.valueOf(currentUserId);
        String nickName = loginUser.getNickname();

        // 작성자의 이름을 참여자 명단에 추가 및 변환
        List<List<String>> joinMemberIdNickNameList = new ArrayList<>();
        joinMemberIdNickNameList.add(Arrays.asList(memberId, nickName)); // [['1', '강남']] 형태로 추가

        // 비어있는 대기자 명단 생성 및 변환
        List<List<String>> waitingMemberIdNickNameList = new ArrayList<>();

        ChatRoom chatRoom = ChatRoom.builder()
                .member(loginUser)
                .post(post)
                .roomTitle(createChatRoomForm.getRoomTitle())
                .roomContent(createChatRoomForm.getRoomContent())
                .roomMemberLimit(createChatRoomForm.getRoomMemberLimit())
                .roomState(0L)
                .joinMemberIdNickNameList(joinMemberIdNickNameList)
                .waitingMemberIdNickNameList(waitingMemberIdNickNameList)
                .build();

        chatRoomRepository.save(chatRoom);

        // 채팅방 멤버 테이블에 사용자를 참여자로 등록
        AddChatRoomUser(chatRoom, loginUser);

        // 유저가 참여중인 채팅방 리스트 불러옴
        List<String> joinRoomIdList = loginUser.getJoinRoomIdList();
        if (joinRoomIdList == null) {
            joinRoomIdList = new ArrayList<>();
            loginUser.setJoinRoomIdList(joinRoomIdList);
        }
        joinRoomIdList.add(String.valueOf(chatRoom.getId()));
        loginUser.setJoinRoomIdList(joinRoomIdList);

        memberRepository.save(loginUser);
    }

    // 해당 모임채팅방 수정(방장만 가능)
    @Override
    @Transactional
    public void updateChatRoom(String chatRoomId, @Valid UpdateChatRoomForm updateChatRoomForm, Member loginUser) {
        ChatRoom chatRoom = chatRoomRepository.findById(Long.valueOf(chatRoomId))
                .orElseThrow(() -> new IllegalArgumentException("해당 모임이 존재하지 않습니다."));

        // 사용자 검증 - 현재 로그인한 사용자의 ID를 가져오는 메서드
        Long currentUserId = loginUser.getId();
        if (!chatRoom.getMember().getId().equals(currentUserId)) {
            throw new IllegalStateException("모임 채팅방 수정 권한이 없습니다.");
        }

        chatRoom.setRoomTitle(updateChatRoomForm.getRoomTitle());
        chatRoom.setRoomContent(updateChatRoomForm.getRoomContent());
        chatRoom.setRoomMemberLimit(updateChatRoomForm.getRoomMemberLimit());
        chatRoomRepository.save(chatRoom);
    }

    // 해당 모임채팅방 삭제
    @Override
    @Transactional
    public void deleteChatRoom(String chatRoomId, Member loginUser) {
        ChatRoom chatRoom = chatRoomRepository.findById(Long.valueOf(chatRoomId))
                .orElseThrow(() -> new IllegalArgumentException("해당 모임이 존재하지 않습니다."));

        // 사용자 검증 - 현재 로그인한 사용자의 ID를 가져오는 메서드
        Long currentUserId = loginUser.getId();
        if (!chatRoom.getMember().getId().equals(currentUserId)) {
            throw new IllegalStateException("모임 채팅방 삭제 권한이 없습니다.");
        }

        // 채팅방 참여자 목록에서 각 참여자의 joinRoomIdList에서 해당 채팅방 제거
        List<List<String>> joinMemberIdNickNameList = chatRoom.getJoinMemberIdNickNameList();
        for (List<String> memberInfo : joinMemberIdNickNameList) {
            Member member = memberRepository.findById(Long.valueOf(memberInfo.get(0)))
                    .orElse(null);
            if (member != null) {
                // 유저가 참여중인 채팅방 리스트 불러옴
                List<String> joinRoomIdList = member.getJoinRoomIdList();
                joinRoomIdList.remove(chatRoomId);
                // JPA의 변경 감지(dirty checking)
                member.setJoinRoomIdList(joinRoomIdList);
                memberRepository.save(member);
            }
        }

        // 대기자 목록에서 각 대기자의 waitRoomIdList에서 해당 채팅방 제거
        List<List<String>> waitingMemberIdNickNameList = chatRoom.getWaitingMemberIdNickNameList();
        for (List<String> memberInfo : waitingMemberIdNickNameList) {
            Member member = memberRepository.findById(Long.valueOf(memberInfo.get(0)))
                    .orElse(null);
            if (member != null) {
                // 유저가 대기중인 채팅방 리스트 불러옴
                List<String> waitRoomIdList = member.getWaitRoomIdList();
                waitRoomIdList.remove(chatRoomId);
                // JPA의 변경 감지(dirty checking)
                member.setWaitRoomIdList(waitRoomIdList);
                memberRepository.save(member);
            }
        }

        // 채팅방 삭제 시 채팅방 참여자 모두 삭제 & 채팅방의 메시지 모두 삭제
        RemoveAllUser(Long.valueOf(chatRoomId));
        chatMessageRepository.removeAllByChatRoomId(Long.valueOf(chatRoomId));
        chatRoomRepository.delete(chatRoom);

        // 알림 발송
        alertEventPublisher.publishGroupDeletion(chatRoom);
    }

    // 해당 모임채팅방에 참여신청
    @Override
    @Transactional
    public void applyChatRoom(String chatRoomId, Member loginUser) {
        ChatRoom chatRoom = chatRoomRepository.findById(Long.valueOf(chatRoomId))
                .orElseThrow(() -> new IllegalArgumentException("해당 모임이 존재하지 않습니다."));

        // 사용자 검증 - 현재 로그인한 사용자의 ID를 가져오는 메서드
        Long currentUserId = loginUser.getId();
        String memberId = String.valueOf(currentUserId);
        String nickName = loginUser.getNickname();

        // 기존 참여자/대기자 명단을 변환하여 불러옴
        List<List<String>> joinMemberIdNickNameList = chatRoom.getJoinMemberIdNickNameList();
        List<List<String>> waitingMemberIdNickNameList = chatRoom.getWaitingMemberIdNickNameList();

        // 유저가 참여신청한 채팅방 리스트 불러옴
        List<String> waitRoomIdList = loginUser.getWaitRoomIdList();
        if (waitRoomIdList == null) {
            waitRoomIdList = new ArrayList<>();
            loginUser.setWaitRoomIdList(waitRoomIdList);
        }

        // 참여자 명단에서 현재 사용자의 ID가 존재하는지 확인
        boolean isUserInJoinList = joinMemberIdNickNameList.stream()
                .anyMatch(member -> member.get(0).equals(memberId));

        // 대기자 명단에서도 같은 방식으로 확인
        boolean isUserInWaitList = waitingMemberIdNickNameList.stream()
                .anyMatch(member -> member.get(0).equals(memberId));

        // 참여자/대기자 명단 등록여부 확인 및 사용자 ID 추가
        if (isUserInJoinList) {
            throw new IllegalStateException("이미 참여자 명단에 등록된 사용자입니다.");
        } else {
            if (!isUserInWaitList) {
                waitingMemberIdNickNameList.add(Arrays.asList(memberId, nickName));
                waitRoomIdList.add(chatRoomId);
            } else {
                throw new IllegalStateException("이미 대기자 명단에 등록된 사용자입니다.");
            }
        }

        // 변경된 대기자 명단을 다시 저장
        chatRoom.setWaitingMemberIdNickNameList(waitingMemberIdNickNameList);
        loginUser.setWaitRoomIdList(waitRoomIdList);
        chatRoomRepository.save(chatRoom); // 트랜잭션 종료 시 자동 저장
        memberRepository.save(loginUser);

        // 알림 발송
        alertEventPublisher.publishGroupApplication(chatRoom, loginUser);
    }

    // 해당 모임채팅방에 참여신청 취소
    @Override
    @Transactional
    public void cancelApplyChatRoom(String chatRoomId, Member loginUser) {
        ChatRoom chatRoom = chatRoomRepository.findById(Long.valueOf(chatRoomId))
                .orElseThrow(() -> new IllegalArgumentException("해당 모임이 존재하지 않습니다."));

        // 사용자 검증 - 현재 로그인한 사용자의 ID를 가져오는 메서드
        Long currentUserId = loginUser.getId();
        String memberId = String.valueOf(currentUserId);
        String nickName = loginUser.getNickname();

        // 기존 대기자 명단을 변환하여 불러옴
        List<List<String>> waitingMemberIdNickNameList = chatRoom.getWaitingMemberIdNickNameList();

        // 유저가 참여신청한 채팅방 리스트 불러옴
        List<String> waitRoomIdList = loginUser.getWaitRoomIdList();

        // 대기자 명단에서도 같은 방식으로 확인
        boolean isUserInWaitList = waitingMemberIdNickNameList.stream()
                .anyMatch(member -> member.get(0).equals(memberId));

        // 대기자 명단 등록여부 확인 및 사용자 ID 제거
        if (isUserInWaitList) {
            waitingMemberIdNickNameList.remove(Arrays.asList(memberId, nickName));
            waitRoomIdList.remove(chatRoomId);
        } else {
            throw new IllegalStateException("대기자 명단에 등록되지 않은 사용자입니다.");
        }

        // 변경된 대기자 명단을 다시 저장
        chatRoom.setWaitingMemberIdNickNameList(waitingMemberIdNickNameList);
        loginUser.setWaitRoomIdList(waitRoomIdList);
        chatRoomRepository.save(chatRoom); // 트랜잭션 종료 시 자동 저장
        memberRepository.save(loginUser);

        // 알림 발송
        alertEventPublisher.publishGroupApplicationCancel(chatRoom, loginUser);
    }

    // 해당 모임채팅방 참여신청 승인
    @Override
    @Transactional
    public void approveApplyChatRoom(String chatRoomId, String applyMemberId, Member loginUser) {
        ChatRoom chatRoom = chatRoomRepository.findById(Long.valueOf(chatRoomId))
                .orElseThrow(() -> new IllegalArgumentException("해당 모임이 존재하지 않습니다."));
        Member applyMember = memberRepository.findById(Long.valueOf(applyMemberId))
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        // 사용자 검증 - 현재 로그인한 사용자의 ID를 가져오는 메서드
        Long currentUserId = loginUser.getId();
        if (!chatRoom.getMember().getId().equals(currentUserId)) {
            throw new IllegalStateException("모임 참여신청 승인 권한이 없습니다.");
        }

        // 참여 신청한 유저의 닉네임 정보
        String applyNickName = applyMember.getNickname();

        // 기존 참여자/대기자 명단을 변환하여 불러옴
        List<List<String>> joinMemberIdNickNameList = chatRoom.getJoinMemberIdNickNameList();
        List<List<String>> waitingMemberIdNickNameList = chatRoom.getWaitingMemberIdNickNameList();

        // 유저가 참여신청한/참여중인 채팅방 리스트 불러옴
        List<String> waitRoomIdList = applyMember.getWaitRoomIdList();
        List<String> joinRoomIdList = applyMember.getJoinRoomIdList();
        if (joinRoomIdList == null) {
            joinRoomIdList = new ArrayList<>();
            applyMember.setJoinRoomIdList(joinRoomIdList);
        }

        // 대기자 명단에서도 같은 방식으로 확인
        boolean isUserInWaitList = waitingMemberIdNickNameList.stream()
                .anyMatch(member -> member.get(0).equals(applyMemberId));

        // 참여자 수가 제한 인원수를 넘으면 안됨
        if (chatRoom.getRoomMemberLimit() == (long)joinMemberIdNickNameList.size()){
            throw new IllegalStateException("참여자 수가 제한 인원수에 도달하였습니다.");
        } else {
            // 참여자/대기자 명단 등록여부 확인 및 사용자 ID 추가/제거
            if (isUserInWaitList) {
                waitingMemberIdNickNameList.remove(Arrays.asList(applyMemberId, applyNickName));
                joinMemberIdNickNameList.add(Arrays.asList(applyMemberId, applyNickName));
                waitRoomIdList.remove(chatRoomId);
                joinRoomIdList.add(chatRoomId);

                // 채팅방 멤버 테이블에 가입 승인된 유저를 참여자로 등록
                AddChatRoomUser(chatRoom, applyMember);

                // 알림 발송
                alertEventPublisher.publishGroupApproval(chatRoom, applyMember);
            } else {
                throw new IllegalStateException("대기자 명단에 등록되지 않은 사용자입니다.");
            }
        }
    }

    // 해당 모임채팅방 참여신청 거절
    @Override
    @Transactional
    public void refuseApplyChatRoom(String chatRoomId, String applyMemberId, Member loginUser) {
        ChatRoom chatRoom = chatRoomRepository.findById(Long.valueOf(chatRoomId))
                .orElseThrow(() -> new IllegalArgumentException("해당 모임이 존재하지 않습니다."));
        Member applyMember = memberRepository.findById(Long.valueOf(applyMemberId))
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        // 사용자 검증 - 현재 로그인한 사용자의 ID를 가져오는 메서드
        Long currentUserId = loginUser.getId();
        if (!chatRoom.getMember().getId().equals(currentUserId)) {
            throw new IllegalStateException("모임 참여신청 거절 권한이 없습니다.");
        }

        // 참여 신청한 유저의 닉네임 정보
        String applyNickName = applyMember.getNickname();

        // 기존 대기자 명단을 변환하여 불러옴
        List<List<String>> waitingMemberIdNickNameList = chatRoom.getWaitingMemberIdNickNameList();

        // 참여신청한 유저의 채팅방 리스트 불러옴
        List<String> waitRoomIdList = applyMember.getWaitRoomIdList();

        // 대기자 명단에서도 같은 방식으로 확인
        boolean isUserInWaitList = waitingMemberIdNickNameList.stream()
                .anyMatch(member -> member.get(0).equals(applyMemberId));

        // 대기자 명단 등록여부 확인 및 사용자 ID 제거
        if (isUserInWaitList) {
            waitingMemberIdNickNameList.remove(Arrays.asList(applyMemberId, applyNickName));
            waitRoomIdList.remove(chatRoomId);

            alertEventPublisher.publishGroupApplicationRejection(chatRoom, applyMember);
        } else {
            throw new IllegalStateException("대기자 명단에 등록되지 않은 사용자입니다.");
        }
    }

    // 해당 모임채팅방의 참여자 강퇴
    @Override
    @Transactional
    public void unqualifyChatRoom(String chatRoomId, String unqualifyMemberId, Member loginUser) {
        ChatRoom chatRoom = chatRoomRepository.findById(Long.valueOf(chatRoomId))
                .orElseThrow(() -> new IllegalArgumentException("해당 모임이 존재하지 않습니다."));
        Member unqualifyMember = memberRepository.findById(Long.valueOf(unqualifyMemberId))
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        // 사용자 검증 - 현재 로그인한 사용자의 ID를 가져오는 메서드
        Long currentUserId = loginUser.getId();
        if (!chatRoom.getMember().getId().equals(currentUserId)) {
            throw new IllegalStateException("참여자 강퇴 권한이 없습니다.");
        }
        if (String.valueOf(currentUserId).equals(unqualifyMemberId)){
            throw new IllegalStateException("방장은 스스로 강퇴할 수 없습니다.");
        }

        // 강퇴할 유저의 닉네임 정보
        String unqualifyNickName = unqualifyMember.getNickname();

        // 기존 참여자 명단을 변환하여 불러옴
        List<List<String>> joinMemberIdNickNameList = chatRoom.getJoinMemberIdNickNameList();

        // 유저가 참여중인 채팅방 리스트 불러옴
        List<String> joinRoomIdList = unqualifyMember.getJoinRoomIdList();

        // 참여자 명단에서 강퇴할 사용자의 ID가 존재하는지 확인
        boolean isUserInJoinList = joinMemberIdNickNameList.stream()
                .anyMatch(member -> member.get(0).equals(unqualifyMemberId));

        // 참여자 명단 등록여부 확인 및 사용자 ID 제거
        if (!isUserInJoinList) {
            throw new IllegalStateException("참여자 명단에 등록되지 않은 사용자입니다.");
        } else {
            joinMemberIdNickNameList.remove(Arrays.asList(unqualifyMemberId, unqualifyNickName));
            joinRoomIdList.remove(chatRoomId);
        }

        // 채팅방 멤버 테이블에 사용자로 등록된 참여자 삭제
        RemoveChatRoomUser(chatRoom, unqualifyMember);

        // 알림 발송
        alertEventPublisher.publishGroupKickToTarget(chatRoom, unqualifyMember);  // 강퇴 대상에게
        alertEventPublisher.publishGroupKickToMembers(chatRoom, unqualifyMember);  // 그룹 전체에게
    }

    // 해당 모임채팅방 나가기(방장이 나가는 경우 해당 모임채팅방 삭제)
    @Override
    @Transactional
    public void leaveChatRoom(String chatRoomId, Member loginUser) {
        ChatRoom chatRoom = chatRoomRepository.findById(Long.valueOf(chatRoomId))
                .orElseThrow(() -> new IllegalArgumentException("해당 모임이 존재하지 않습니다."));

        // 사용자 검증 - 현재 로그인한 사용자의 ID를 가져오는 메서드
        Long currentUserId = loginUser.getId();
        String memberId = String.valueOf(currentUserId);
        String nickName = loginUser.getNickname();

        // 기존 참여자 명단을 변환하여 불러옴
        List<List<String>> joinMemberIdNickNameList = chatRoom.getJoinMemberIdNickNameList();

        // 유저가 참여중인 채팅방 리스트 불러옴
        List<String> joinRoomIdList = loginUser.getJoinRoomIdList();

        // 현재 로그인한 사용자의 ID가 참여자리스트에 존재하는지 확인
        boolean isUserInJoinList = joinMemberIdNickNameList.stream()
                .anyMatch(member -> member.get(0).equals(memberId));

        // 방장이 나가면 모임채팅방 삭제
        if (currentUserId.equals(chatRoom.getMember().getId())) {
            joinRoomIdList.remove(chatRoomId);
            // ChatRoom 삭제
            deleteChatRoom(chatRoomId, loginUser);
        } else {
            // 참여자 명단 등록여부 확인 및 사용자 ID 제거
            if (!isUserInJoinList) {
                throw new IllegalStateException("참여자 명단에 등록되지 않은 사용자입니다.");
            } else {
                joinMemberIdNickNameList.remove(Arrays.asList(memberId, nickName));
                joinRoomIdList.remove(chatRoomId);
                // 영속성 컨텍스트 문제 방지 - 변경사항 즉시 DB 반영
                loginUser.setJoinRoomIdList(joinRoomIdList);
                memberRepository.save(loginUser);
                // 채팅방 멤버 테이블에 사용자로 등록된 참여자 삭제
                RemoveChatRoomUser(chatRoom, loginUser);
            }
        }
    }

    // 해당 모임채팅방에서 참여자에게 방장권한 위임
    @Override
    @Transactional
    public void delegateChatRoom(String chatRoomId, String delegateMemberId, Member loginUser) {
        ChatRoom chatRoom = chatRoomRepository.findById(Long.valueOf(chatRoomId))
                .orElseThrow(() -> new IllegalArgumentException("해당 모임이 존재하지 않습니다."));
        Member delegateMember = memberRepository.findById(Long.valueOf(delegateMemberId))
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        // 사용자 검증 - 현재 로그인한 사용자의 ID를 가져오는 메서드
        Long currentUserId = loginUser.getId();
        if (!chatRoom.getMember().getId().equals(currentUserId)) {
            throw new IllegalStateException("방장권한 위임 권한이 없습니다.");
        }

        // 기존 참여자 명단을 변환하여 불러옴
        List<List<String>> joinMemberIdNickNameList = chatRoom.getJoinMemberIdNickNameList();

        // 참여자 명단에서 현재 사용자의 ID가 존재하는지 확인
        boolean isUserInJoinList = joinMemberIdNickNameList.stream()
                .anyMatch(member -> member.get(0).equals(delegateMemberId));

        // 참여자 명단 등록여부 확인 및 방장을 입력받은 사용자 ID로 변경
        if (!isUserInJoinList) {
            throw new IllegalStateException("참여자 명단에 등록되지 않은 사용자입니다.");
        } else {
            chatRoom.setMember(delegateMember);

            // 알림 발송
            alertEventPublisher.publishGroupDelegateOwner(chatRoom, delegateMember);
        }
    }

    // 채팅방 멤버 테이블에 사용자를 참여자로 등록 OOO
    @Transactional
    public void AddChatRoomUser(ChatRoom chatRoom, Member member) {
        ChatRoomUser chatRoomUser = ChatRoomUser.builder()
                .chatRoom(chatRoom)
                .member(member)
                .build();

        chatRoomUserRepository.save(chatRoomUser);
    }
    
    // 채팅방 멤버 테이블에 사용자로 등록된 참여자 삭제 (한명)
    @Transactional
    public void RemoveChatRoomUser(ChatRoom chatRoom, Member member) {
        ChatRoomUser chatRoomUser = chatRoomUserRepository.findByChatRoomIdAndMemberId(chatRoom.getId(), member.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        chatRoomUserRepository.delete(chatRoomUser);
    }
    
    // 채팅방 삭제 시 채팅방 참여자 모두 삭제
    @Transactional
    public void RemoveAllUser(Long chatRoomId) {
        chatRoomUserRepository.deleteAllByChatRoomId(chatRoomId);
    }
}
