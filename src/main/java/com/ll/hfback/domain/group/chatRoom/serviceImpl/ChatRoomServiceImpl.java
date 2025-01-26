package com.ll.hfback.domain.group.chatRoom.serviceImpl;

import com.ll.hfback.domain.group.chatRoom.converter.StringListConverter;
import com.ll.hfback.domain.group.chatRoom.dto.ChatRoomDto;
import com.ll.hfback.domain.group.chatRoom.dto.DetailChatRoomDto;
import com.ll.hfback.domain.group.chatRoom.entity.ChatRoom;
import com.ll.hfback.domain.group.chatRoom.form.CreateChatRoomForm;
import com.ll.hfback.domain.group.chatRoom.repository.ChatRoomRepository;
import com.ll.hfback.domain.group.chatRoom.service.ChatRoomService;
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
    public Optional<DetailChatRoomDto> searchById(Long id) {
        Optional<ChatRoom> chatRoom = chatRoomRepository.findById(id);
        return chatRoom.map(this::convertToDetailChatRoomDto);  // convert ChatRoom to ChatRoomDto
    }

    // DB에서 받아온 참여자명단을 List<String>으로 변환하고, ChatRoom을 ChatRoomDto로 변환하는 메서드
    @Override
    @Transactional
    public ChatRoomDto convertToChatRoomDto(ChatRoom chatRoom) {
        StringListConverter converter = new StringListConverter();

        // joinMemberList는 null이 아님
        chatRoom.setJoinMemberList(converter.convertToEntityAttribute(String.join(",", chatRoom.getJoinMemberList())));

        // Create ChatRoomDto
        ChatRoomDto chatRoomDto = new ChatRoomDto(
                chatRoom.getId(),
                chatRoom.getRoomTitle(),
                chatRoom.getRoomContent(),
                chatRoom.getRoomMemberLimit(),
                chatRoom.getJoinMemberList().size()
        );

        return chatRoomDto;
    }

    // DB에서 받아온 참여자명단과 대기자명단을 List<String>으로 변환하고, ChatRoom을 DetailChatRoomDto로 변환하는 메서드
    @Override
    @Transactional
    public DetailChatRoomDto convertToDetailChatRoomDto(ChatRoom chatRoom) {
        StringListConverter converter = new StringListConverter();

        // joinMemberList는 null이 아님
        chatRoom.setJoinMemberList(converter.convertToEntityAttribute(String.join(",", chatRoom.getJoinMemberList())));

        // waitingMemberList는 null이 가능함
        if (chatRoom.getWaitingMemberList() != null && !chatRoom.getWaitingMemberList().isEmpty()) {
            chatRoom.setWaitingMemberList(converter.convertToEntityAttribute(String.join(",", chatRoom.getWaitingMemberList())));
        } else {
            chatRoom.setWaitingMemberList(List.of());
        }

        // Create DetailChatRoomDto
        DetailChatRoomDto detailChatRoomDto = new DetailChatRoomDto(
                chatRoom.getId(),
                chatRoom.getMember().getNickname(),
                chatRoom.getRoomTitle(),
                chatRoom.getRoomContent(),
                chatRoom.getJoinMemberList(),
                chatRoom.getRoomMemberLimit(),
                chatRoom.getJoinMemberList().size()
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

        // StringListConverter 사용
        StringListConverter converter = new StringListConverter();

        // 작성자의 이름을 참여자 명단에 추가 및 변환
        List<String> joinMemberList = new ArrayList<>();
        joinMemberList.add(createChatRoomForm.getMember().getNickname()); // Member 객체의 이름을 추가
        converter.convertToDatabaseColumn(joinMemberList);

        // 비어있는 대기자 명단 생성 및 변환
        List<String> waitingMemberList = new ArrayList<>();
        converter.convertToDatabaseColumn(waitingMemberList);

        ChatRoom chatRoom = ChatRoom.builder()
                .festivalId(festivalId)
                .member(createChatRoomForm.getMember())
                .roomTitle(createChatRoomForm.getRoomTitle())
                .roomContent(createChatRoomForm.getRoomContent())
                .roomMemberLimit(createChatRoomForm.getRoomMemberLimit())
                .roomState(0L)
                .joinMemberList(joinMemberList)
                .waitingMemberList(waitingMemberList)
                .build();

        chatRoomRepository.save(chatRoom);
    }
}
