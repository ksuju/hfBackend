package com.ll.hfback.domain.group.chat.search.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.time.LocalDateTime;

/**
 * packageName    : com.ll.hfback.domain.group.chat.search.document
 * fileName       : ChatMessageDoc
 * author         : sungjun
 * date           : 2025-02-10
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-02-10        kyd54       최초 생성
 */
@Getter
@Setter
@Builder
@Setting(settingPath = "/elasticsearch/settings.json")
@Mapping(mappingPath = "/elasticsearch/mappings.json")
@Document(indexName = "chat_message")
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDoc {
    @Id
    private Long chatMessageId;

    @Field(name = "chat_message_content")
    private String chatMessageContent; // 메시지 내용

    @Field(name = "create_date")
    private LocalDateTime createDate; // 작성 시간

    @Field(name = "chat_room_id")
    private Long chatRoomId; // 채팅방 ID

    @Field(name = "nickname")
    private String nickname; // 작성자 닉네임
    
    @Field(name = "email")
    private String email; // 작성자 이메일

    @Field(name = "original_file_name")
    private String originalFileName; // 파일 첨부 시 원본 파일 이름
}
