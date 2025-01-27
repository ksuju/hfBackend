package com.ll.hfback.domain.group.chatRoom.serviceImpl;

import com.ll.hfback.domain.group.chatRoom.dto.ChatRoomDto;
import com.ll.hfback.domain.group.chatRoom.dto.DetailChatRoomDto;
import com.ll.hfback.domain.group.chatRoom.entity.ChatRoom;
import com.ll.hfback.domain.group.chatRoom.form.CreateChatRoomForm;
import com.ll.hfback.domain.group.chatRoom.form.UpdateChatRoomForm;
import com.ll.hfback.domain.group.chatRoom.repository.ChatRoomRepository;
import com.ll.hfback.domain.group.chatRoom.service.ChatRoomService;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.domain.member.member.repository.MemberRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    // 해당 게시글의 모든 모임 조회
    @Override
    @Transactional(readOnly = true)
    public List<ChatRoomDto> searchByFestivalId(String festivalId) {
        List<ChatRoom> chatRooms = chatRoomRepository.findByFestivalId(festivalId);
        return chatRooms.stream()
                .map(this::convertToChatRoomDto)  // convert ChatRoom to ChatRoomDto
                .collect(Collectors.toList());
    }

    // 해당 게시글의 모임 상세 조회
    @Override
    @Transactional(readOnly = true)
    public Optional<DetailChatRoomDto> searchById(Long id, String memberId) {
        Optional<ChatRoom> chatRoom = chatRoomRepository.findById(id);
        return chatRoom
                // joinMemberList에 memberId 포함 여부 확인
                .filter(room -> room.getJoinMemberIdList().contains(memberId))
                .map(this::convertToDetailChatRoomDto);
    }

    // DB에서 받아온 참여자명단을 List<String>으로 변환하고, ChatRoom을 ChatRoomDto로 변환하는 메서드
    @Override
    @Transactional
    public ChatRoomDto convertToChatRoomDto(ChatRoom chatRoom) {
        // Create ChatRoomDto
        ChatRoomDto chatRoomDto = new ChatRoomDto(
                chatRoom.getMember().getId(),
                chatRoom.getId(),
                chatRoom.getRoomTitle(),
                chatRoom.getRoomContent(),
                chatRoom.getRoomMemberLimit(),
                chatRoom.getJoinMemberIdList().size()
        );

        return chatRoomDto;
    }

    // DB에서 받아온 참여자명단과 대기자명단을 List<String>으로 변환하고, ChatRoom을 DetailChatRoomDto로 변환하는 메서드
    @Override
    @Transactional
    public DetailChatRoomDto convertToDetailChatRoomDto(ChatRoom chatRoom) {
        // 기존 참여자 ID 리스트를 가져옴
        List<String> joinMemberIdList = chatRoom.getJoinMemberIdList();

        // ID를 사용하여 각 Member 엔티티를 조회하고 닉네임 리스트를 생성
        List<String> joinMemberNicknames = joinMemberIdList.stream()
                .map(id -> memberRepository.findById(Long.valueOf(id))
                        .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 존재하지 않습니다.")).getNickname())
                .toList();

        // DetailChatRoomDto 생성
        DetailChatRoomDto detailChatRoomDto = new DetailChatRoomDto(
                chatRoom.getMember().getId(),
                chatRoom.getId(),
                chatRoom.getMember().getNickname(),
                chatRoom.getRoomTitle(),
                chatRoom.getRoomContent(),
                joinMemberNicknames,  // 닉네임 리스트 전달
                chatRoom.getRoomMemberLimit(),
                joinMemberNicknames.size()  // 참여자 수 전달
        );

        return detailChatRoomDto;
    }

    // 해당 게시글에 모임 생성
    @Override
    @Transactional
    public void createChatRoom(String festivalId, @Valid CreateChatRoomForm createChatRoomForm) {
        // roomMemberLimit의 값이 유효한지 확인하기 (10-100)
        Long roomMemberLimit = createChatRoomForm.getRoomMemberLimit();
        if (roomMemberLimit == null || roomMemberLimit < 10 || roomMemberLimit > 100) {
            throw new IllegalArgumentException("채팅방 참여 인원수는 10-100 사이의 값이어야 합니다.");
        }

        // 작성자의 이름을 참여자 명단에 추가 및 변환
        List<String> joinMemberIdList = new ArrayList<>();
        joinMemberIdList.add(String.valueOf(createChatRoomForm.getMember().getId())); // Member 객체의 이름을 추가

        // 비어있는 대기자 명단 생성 및 변환
        List<String> waitingMemberIdList = new ArrayList<>();

        ChatRoom chatRoom = ChatRoom.builder()
                .festivalId(festivalId)
                .member(createChatRoomForm.getMember())
                .roomTitle(createChatRoomForm.getRoomTitle())
                .roomContent(createChatRoomForm.getRoomContent())
                .roomMemberLimit(createChatRoomForm.getRoomMemberLimit())
                .roomState(0L)
                .joinMemberIdList(joinMemberIdList)
                .waitingMemberIdList(waitingMemberIdList)
                .build();

        chatRoomRepository.save(chatRoom);
    }

    // 해당 모임채팅방 수정
    @Override
    @Transactional
    public void updateChatRoom(Long chatRoomId, @Valid UpdateChatRoomForm updateChatRoomForm) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("해당 모임이 존재하지 않습니다."));
        chatRoom.setRoomTitle(updateChatRoomForm.getRoomTitle());
        chatRoom.setRoomContent(updateChatRoomForm.getRoomContent());
        chatRoom.setRoomMemberLimit(updateChatRoomForm.getRoomMemberLimit());
        chatRoomRepository.save(chatRoom);
    }

    // 해당 모임채팅방 삭제
    @Override
    @Transactional
    public void deleteChatRoom(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("해당 모임이 존재하지 않습니다."));
        chatRoomRepository.delete(chatRoom);
    }

    // 해당 모임채팅방에 참여신청
    @Override
    @Transactional
    public void applyChatRoom(Long chatRoomId, String memberId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("해당 모임이 존재하지 않습니다."));

        // 기존 참여자/대기자 명단을 변환하여 불러옴
        List<String> joinMemberIdList = chatRoom.getJoinMemberIdList();
        List<String> waitingMemberIdList = chatRoom.getWaitingMemberIdList();

        // 참여자/대기자 명단 등록 여부 확인 및 사용자 ID 추가
        if (joinMemberIdList.contains(memberId)) {
            throw new IllegalStateException("이미 참여자 명단에 등록된 사용자입니다.");
        } else {
            // 대기자 명단에 사용자 이름 추가
            if (!waitingMemberIdList.contains(memberId)) {
                waitingMemberIdList.add(memberId);
            } else {
                throw new IllegalStateException("이미 대기자 명단에 등록된 사용자입니다.");
            }
        }

        // 변경된 대기자 명단을 다시 저장
        chatRoom.setWaitingMemberIdList(waitingMemberIdList);
        chatRoomRepository.save(chatRoom); // 트랜잭션 종료 시 자동 저장
    }
}
