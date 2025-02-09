package com.ll.hfback.domain.group.chat.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * packageName    : com.ll.hfback.domain.group.chat.service
 * fileName       : ChatS3Service
 * author         : sungjun
 * date           : 2025-02-07
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-02-07        kyd54       최초 생성
 */
public interface ChatS3Service {
    String fileUpload(Long chatRoomId, MultipartFile file) throws IOException;
}
