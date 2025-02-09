package com.ll.hfback.domain.member.member.service;

import com.ll.hfback.domain.member.member.dto.MemberUpdateRequest;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.domain.member.member.entity.Member.MemberState;
import com.ll.hfback.domain.member.member.repository.MemberRepository;
import com.ll.hfback.global.exceptions.ErrorCode;
import com.ll.hfback.global.exceptions.ServiceException;
import com.ll.hfback.global.storage.FileStorageHandler;
import com.ll.hfback.global.storage.FileUploadRequest;
import com.ll.hfback.global.storage.FileUploadResult;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final FileStorageHandler fileStorageHandler;
    private final PasswordEncoder passwordEncoder;


    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public List<Member> findAllByState(MemberState state) {
        return memberRepository.findAllByState(state);
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    /*
    //게시글 작성자 확인 용도
    public Member getEmail(String nickName) {
        Optional<Member> member = this.memberRepository.findByEmail(nickName);
        if (member.isPresent()) {
            return member.get();
        } else {
            throw new ServiceException(ErrorCode.MEMBER_NOT_FOUND);
        }
    }*/

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
            .orElseThrow(() -> new ServiceException(ErrorCode.MEMBER_NOT_FOUND));
    }


    @Transactional
    public Member updateInfo(Member member, MemberUpdateRequest memberUpdateRequest) {
        Member modifiedMember = memberRepository.findById(member.getId())
            .orElseThrow(() -> new ServiceException(ErrorCode.MEMBER_NOT_FOUND));

        modifiedMember.updateInfo(memberUpdateRequest);
        return modifiedMember;
    }

    @Transactional
    public Member updateProfileImage(Long memberId, MultipartFile file) {
        Member member = findById(memberId)
            .orElseThrow(() -> new ServiceException(ErrorCode.MEMBER_NOT_FOUND));

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
            .orElseThrow(() -> new ServiceException(ErrorCode.MEMBER_NOT_FOUND));

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
            .orElseThrow(() -> new ServiceException(ErrorCode.MEMBER_NOT_FOUND));

        member.setState(Member.MemberState.BANNED);
    }


    @Transactional
    public void deactivateMember(Long memberId) {
        memberRepository.findById(memberId)
            .orElseThrow(() -> new ServiceException(ErrorCode.MEMBER_NOT_FOUND))
            .deactivate();
    }


    @Transactional
    public void restoreMember(Long memberId) {
        memberRepository.findById(memberId)
            .orElseThrow(() -> new ServiceException(ErrorCode.MEMBER_NOT_FOUND))
            .restore();
    }

    @Transactional
    public void addPassword(Long memberId, @NotBlank String password) {
        memberRepository.findById(memberId)
            .orElseThrow(() -> new ServiceException(ErrorCode.MEMBER_NOT_FOUND))
            .setPassword(passwordEncoder.encode(password));
    }

    @Transactional
    public void disconnectSocialAccount(Long memberId, String upperProvider) {
        memberRepository.findById(memberId)
            .orElseThrow(() -> new ServiceException(ErrorCode.MEMBER_NOT_FOUND))
            .disconnectSocialAccount(upperProvider);
    }

}
