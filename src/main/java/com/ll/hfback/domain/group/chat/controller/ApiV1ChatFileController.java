package com.ll.hfback.domain.group.chat.controller;

import com.ll.hfback.domain.group.chat.service.ChatS3Service;
import com.ll.hfback.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public RsData<String> handleFileUpload(
            @PathVariable("chatRoom-id") Long chatRoomId,
            @RequestParam("file") MultipartFile file) {
        // 파일 크기 검증
        if (file.getSize() > 5_242_880) { // 5MB
            return new RsData<String>("501","5MB 보다 큰 파일은 업로드 할 수 없습니다.");
        }

        try {
            return new RsData<String>("200","파일 업로드 성공", chatS3Service.uploadFile(chatRoomId, file));
        } catch (Exception e) {
            return new RsData<String>("500","파일 업로드 실패" + e);
        }
    }
}
