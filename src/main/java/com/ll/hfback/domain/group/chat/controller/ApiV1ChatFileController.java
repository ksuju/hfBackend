package com.ll.hfback.domain.group.chat.controller;

import com.ll.hfback.domain.group.chat.service.ChatS3Service;
import com.ll.hfback.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * packageName    : com.ll.hfback.domain.group.chat.controller
 * fileName       : ApiV1ChatFileController
 * author         : sungjun
 * date           : 2025-02-07
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-02-07        kyd54       최초 생성
 */
@RestController
@RequestMapping("/api/v1/chatRooms/{chatRoom-id}")
@RequiredArgsConstructor
public class ApiV1ChatFileController {

    private final ChatS3Service chatS3Service;
    @PostMapping("/files/upload")
    public RsData<String> fileUpload(
            @PathVariable("chatRoom-id") Long chatRoomId,
            @RequestParam("file") MultipartFile file) {
        try {
            return new RsData<String>("200", "파일 업로드 성공", chatS3Service.fileUpload(chatRoomId, file));
        } catch (IOException e) {
            return new RsData<String>("501", "파일 업로드 실패, 5MB 이상의 파일은 업로드 할 수 없습니다.");
        } catch (IllegalArgumentException e) {
            return new RsData<String>("502", "파일 업로드 실패, 지원하지 않는 파일 형식입니다. jpg, jpeg, png, gif 파일만 업로드 가능합니다.");
        }
    }
}
