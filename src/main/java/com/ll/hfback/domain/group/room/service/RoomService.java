package com.ll.hfback.domain.group.room.service;

import com.ll.hfback.domain.festival.post.entity.Post;
import com.ll.hfback.domain.festival.post.repository.PostRepository;
import com.ll.hfback.domain.group.chat.entity.Chat;
import com.ll.hfback.domain.group.chat.repository.ChatRepository;
import com.ll.hfback.domain.group.room.entity.Room;
import com.ll.hfback.domain.group.room.repository.RoomRepository;
import com.ll.hfback.domain.group.room.response.ResponseRoom;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.domain.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * packageName    : com.ll.hfback.domain.group.room.service
 * fileName       : RoomService
 * author         : sungjun
 * date           : 2025-01-22
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-01-22        kyd54       최초 생성
 */
@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final ChatRepository chatRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public void createRoom(Long fesId, ResponseRoom responseRoom) {

        // fesId에 해당하는 공연 가져옴 
        Post Post = postRepository.findById(fesId).get();
        
        // memberId에 해당하는 사용자(방장) 가져옴 fix: 하드코딩 수정 해야함
        Member member = memberRepository.findById(responseRoom.getMemberId()).get();

        // Room 객체 만들기 (Room 엔티티)
        Room room = Room.builder()
                .post(Post)
                .member(member)
                .roomTitle(responseRoom.getTitle())
                .roomContent(responseRoom.getContent())
                .roomMemberLimit(responseRoom.getMemberLimit())
                .build();

        // 모임 생성 = 채팅방 생성 Chat에 Room 저장
        Chat chat = Chat.builder()
                .room(room)
                .build();
        room.setChat(chat); // Room에 Chat 저장

        // 저장
        chatRepository.save(chat);
        roomRepository.save(room);
    }
}
