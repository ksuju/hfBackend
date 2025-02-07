package com.ll.hfback.domain.group.chat.serviceImpl;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ll.hfback.domain.group.chat.service.ChatS3Service;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * packageName    : com.ll.hfback.domain.group.chat.serviceImpl
 * fileName       : ChatS3ServiceImpl
 * author         : sungjun
 * date           : 2025-02-07
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-02-07        kyd54       최초 생성
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ChatS3ServiceImpl implements ChatS3Service {
    private static final String BUCKET_NAME = "hf-chat";
    private static final String REGION = "ap-northeast-2";
    private final AmazonS3 s3Client;
    private final Logger logger = LoggerFactory.getLogger(ChatS3ServiceImpl.class.getName());

    public static String getS3FileUrl(String fileName) {
        return "https://" + BUCKET_NAME + ".s3." + REGION + ".amazonaws.com/" + fileName;
    }
    @Transactional
    public String uploadFile(Long chatRoomId, MultipartFile file) throws IOException {
        try {
            String fileName = "chatRooms/" + chatRoomId + "/" + file.getOriginalFilename();

            // ObjectMetadata 객체 생성
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType()); // MIME 타입 설정

            // PutObjectRequest 객체 생성
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    BUCKET_NAME, // 버킷 이름
                    fileName,    // 파일 경로
                    file.getInputStream(), // 파일 InputStream
                    metadata     // 메타데이터
            );

            // 파일을 S3에 업로드
            s3Client.putObject(putObjectRequest);

            logger.info("파일 업로드 성공");

            return getS3FileUrl(fileName);  // S3 파일 URL 반환
        } catch (IOException e) {
            logger.info("파일 업로드 실패: IOException");
            throw new RuntimeException(e);
        } catch (SdkClientException e) {
            logger.info("파일 업로드 실패: SdkClientException");
            throw new RuntimeException(e);
        }
    }
}
