package com.ll.hfback.domain.member.member.service;

import com.ll.hfback.domain.member.auth.repository.AuthRepository;
import com.ll.hfback.domain.member.member.dto.MemberUpdateRequest;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.domain.member.member.repository.MemberRepository;
import com.ll.hfback.global.exceptions.ServiceException;
import com.ll.hfback.global.storage.FileStorageHandler;
import com.ll.hfback.global.storage.FileUploadRequest;
import com.ll.hfback.global.storage.FileUploadResult;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthRepository authRepository;
    private final FileStorageHandler fileStorageHandler;


    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    public Member getMember(String email) {
        return authRepository.findByEmail(email);
    }


    @Transactional
    public Member updateInfo(Member member, MemberUpdateRequest memberUpdateRequest) {
        member.updateInfo(memberUpdateRequest);
        return member;
    }

    @Transactional
    public Member updateProfileImage(Long memberId, MultipartFile file) {
        Member member = findById(memberId)
            .orElseThrow(() -> new ServiceException("updateProfileImage()", "회원을 찾을 수 없습니다."));

        FileUploadResult uploadResult = fileStorageHandler.handleFileUpload(
            FileUploadRequest.builder()
                .folderPath("member/")
                .file(file)
                .oldFilePath(member.getProfilePath())
                .deleteOldFile(true)
                .build()
        );

        if (uploadResult != null) {
            member.setProfilePath(uploadResult.getFileName());
        }

        return memberRepository.save(member);
    }


    @Transactional
    public Member resetToDefaultProfileImage(Long memberId) {
        Member member = findById(memberId)
            .orElseThrow(() -> new ServiceException("resetToDefaultProfileImage()", "회원을 찾을 수 없습니다."));

        if (!member.getProfilePath().equals("default.png")) {
            FileUploadResult result = fileStorageHandler.handleFileUpload(
                FileUploadRequest.builder()
                    .oldFilePath(member.getProfilePath())
                    .folderPath("member/")
                    .deleteOldFile(true)
                    .defaultFileName("default.png")
                    .build()
            );
            member.setProfilePath("default.png");
        }

        return memberRepository.save(member);
    }


    @Transactional
    public void banMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new ServiceException("banMember()", "회원을 찾을 수 없습니다."));

        member.setState(Member.MemberState.BANNED);
    }


    @Transactional
    public void deactivateMember(Long id) {
        memberRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(id + "번 사용자가 존재하지 않습니다."))
            .deactivate();
    }


    @Transactional
    public void restoreMember(Long id) {
        memberRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(id + "번 사용자가 존재하지 않습니다."))
            .restore();
    }
}
