package com.ll.hfback.domain.board.comment.controller;

import com.ll.hfback.domain.board.comment.entity.BoardComment;
import com.ll.hfback.domain.board.comment.service.BoardCommentService;
import com.ll.hfback.domain.board.notice.entity.Board;
import com.ll.hfback.domain.board.notice.service.BoardService;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.domain.member.member.service.MemberService;
import com.ll.hfback.global.rsData.RsData;
import com.ll.hfback.global.webMvc.LoginUser;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.SQLOutput;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/boardComment")
public class ApiV1BoardCommentController {

    private final BoardService boardService;
    private final BoardCommentService boardCommentService;
    private final MemberService memberService;

    //댓글 생성 dto RequestBody는 키벨류라서 dto로 키벨류 맞춰줘야 json 중첩오류가 안남
    @Data
    public static class CreateBoardComment {
        String content;
    }

    //댓글 생성
    @PreAuthorize("isAuthenticated")
    @PostMapping("/create/{boardId}")
    public RsData<BoardComment> create(@PathVariable("boardId") Long boardId, @RequestBody CreateBoardComment createBoardComment, @LoginUser Member member){
        Board board = this.boardService.view(boardId);
        BoardComment boardComment = this.boardCommentService.create(boardId, createBoardComment.getContent(), member);
        System.out.println(boardComment);
        return new RsData<>("200","댓글을 성공적으로 생성했습니다.", boardComment);

    }
    //댓글 수정 dto
    @Data
    public static class ModifyBoardComment {
        String content;
    }

    //댓글 수정
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/modify/{boardCommentId}")
    public RsData<BoardComment> modify(@PathVariable("boardCommentId")Long boardCommentId, @RequestBody ModifyBoardComment modifyBoardComment, Principal principal){
        BoardComment boardComment = this.boardCommentService.getComment(boardCommentId);
        Member member  = this.memberService.findByEmail(principal.getName());
        BoardComment MdfBdComment = boardCommentService.modify(boardComment,modifyBoardComment.getContent(), member);
        return new RsData<>("200","댓글을 성공적으로 수정했습니다.",MdfBdComment);
    }


    //댓글 삭제
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/delete/{boardCommentId}")
    public RsData<Void> deleteBoardComment(@PathVariable("boardCommentId")Long boardCommentId,Principal principal){
        BoardComment boardComment =this.boardCommentService.getComment(boardCommentId);
        Member member  = this.memberService.findByEmail(principal.getName());
        this.boardCommentService.delete(boardComment, member);
        return new RsData<>("200","댓글을 성공적으로 삭제했습니다.");
    }
}
