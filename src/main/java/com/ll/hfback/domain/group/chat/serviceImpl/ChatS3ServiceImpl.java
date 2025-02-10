package com.ll.hfback.domain.group.chat.serviceImpl;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ll.hfback.domain.group.chat.entity.ChatMessage;
import com.ll.hfback.domain.group.chat.repository.ChatMessageRepository;
import com.ll.hfback.domain.group.chat.service.ChatS3Service;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

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

    private static final long MAX_FILE_SIZE = 5_242_880; // 5MB

    private final ChatMessageRepository chatMessageRepository;

    // S3 파일 URL 가져오기
    public static String getS3FileUrl(String fileName) {
        return "https://" + BUCKET_NAME + ".s3." + REGION + ".amazonaws.com/" + fileName;
    }

    // S3에 파일 업로드 후 URL 가져오기 (채팅방에 이미지 출력을 위함)
    @Transactional
    public String fileUpload(Long chatRoomId, MultipartFile file) throws IOException {
        try {
            // 파일 검증 (확장자, 파일 크기)
            String extension = FileValidation(file);

            // UUID로 고유한 파일명 생성
            String uuid = UUID.randomUUID().toString();
            String fileName = "chatRooms/" + chatRoomId + "/" + uuid + extension;

            // PutObjectRequest 객체를 생성
            PutObjectRequest putObjectRequest = getPutObjectRequest(file, fileName);

            // 파일을 S3에 업로드
            s3Client.putObject(putObjectRequest);

            logger.info("파일 업로드 성공");

            return getS3FileUrl(fileName);  // S3 파일 URL 반환
        } catch (IOException e) {
            logger.error("5MB 이상의 파일은 업로드 할 수 없습니다: IOException" + e);
            throw new IOException(e);
        } catch (IllegalArgumentException e) {
            logger.error("확장자 검증에서 에러: IllegalArgumentException" + e);
            throw new IllegalArgumentException(e);
        } catch (SdkClientException e) {
            logger.error("파일 업로드 실패: SdkClientException" + e);
            throw new SdkClientException(e);
        }
    }

    // 파일 검증 (확장자, 파일 크기) 메서드
    @NotNull
    private static String FileValidation(MultipartFile file)
            throws IOException, IllegalArgumentException {
        // 파일 크기 검증
        if (file.getSize() > MAX_FILE_SIZE) { // 5MB
            throw new IOException("파일 업로드 실패, 5MB 이상의 파일은 업로드 할 수 없습니다.");
        }
        // 파일 확장자 추출
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();

        // 허용된 파일 확장자 검증
        if (!extension.matches("\\.(jpg|jpeg|png|gif)$")) {
            throw new IllegalArgumentException("지원하지 않는 파일 형식입니다. jpg, jpeg, png, gif 파일만 업로드 가능합니다.");
        }

        // MIME 타입 검증
        String contentType = file.getContentType();
        if (contentType != null && !contentType.matches("^(image/(jpeg|png|gif))$")) {
            throw new IllegalArgumentException("올바르지 않은 파일 형식입니다.");
        }

        return extension;
    }

    // S3에 저장할 파일의 PutObjectRequest 객체를 생성하는 메서드
    @NotNull
    private static PutObjectRequest getPutObjectRequest(MultipartFile file, String fileName) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType()); // MIME 타입 설정

        // PutObjectRequest 객체 생성
        return new PutObjectRequest(
                BUCKET_NAME, // 버킷 이름
                fileName,    // 파일 경로
                file.getInputStream(), // 파일 InputStream
                metadata     // 메타데이터
        );
    }

    // 파일 삭제
    @Transactional
    public void fileDelete(Long chatRoomId, String fileName) {
        try {
            // S3 URL에서 파일 키(경로) 추출
            String fileKey = fileName.substring(fileName.indexOf(".com/") + 5);

            // S3에서 파일 삭제
            s3Client.deleteObject(BUCKET_NAME, fileKey);

            // 해당 채팅 삭제
            ChatMessage chatMessage = chatMessageRepository.findByChatMessageContent(fileName);
            chatMessageRepository.delete(chatMessage);

            logger.info("파일 삭제 성공");
        } catch (Exception e) {
            logger.error("파일 삭제 실패: SdkClientException" + e);
            throw new SdkClientException(e);
        }
    }
}
