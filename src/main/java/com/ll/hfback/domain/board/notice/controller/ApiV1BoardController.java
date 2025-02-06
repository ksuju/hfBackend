package com.ll.hfback.domain.board.notice.controller;

import com.ll.hfback.domain.board.notice.entity.Board;
import com.ll.hfback.domain.board.notice.service.BoardService;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.domain.member.member.service.MemberService;
import com.ll.hfback.global.rsData.RsData;
import com.ll.hfback.global.webMvc.LoginUser;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/boards")
public class ApiV1BoardController {

    private final BoardService BoardService;
    //private final MemberService memberService;

    //게시글 목록 리스트
    @GetMapping
    public RsData<Page<Board>> list(@RequestParam(value = "page", defaultValue = "0") int page){
        Page<Board> paging = this.BoardService.list(page);
        return new RsData<>("200","게시글 리스트를 성공적으로 호출했습니다.",paging);
    }

    //게시글 상세페이지
    @GetMapping("/{boardId}")
    public RsData<Board> view(@PathVariable("boardId") Long boardId){
        Board board =this.BoardService.view(boardId);
        return new RsData<>("200","게시글 상세 페이지를 성공적으로 호출했습니다.",board);
    }

    //게시글 생성 dto
    public record CreateBoard(@NotBlank String title,@NotBlank String content){
    }

    //게시글 생성
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public RsData<Board> create(@Valid @RequestBody CreateBoard createBoard, @LoginUser Member member){
        Board board =BoardService.create(createBoard.content,createBoard.title,member);
        return new RsData<>("200","게시글을 성공적으로 생성했습니다.",board);
    }



    //게시글 수정 dto
    public record ModifyBoard(@NotBlank String title, @NotBlank String content){

    }
    //게시글 수정
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{boardId}")
    public RsData<Board> modify(@PathVariable("boardId") Long boardId,@Valid @RequestBody ModifyBoard modifyBoard, @LoginUser Member member){
        //Board board = this.BoardService.view(id);
        //return this.BoardService.update(boardId, modifyBoard.title, modifyBoard.content, member);
        Board board = this.BoardService.view(boardId);
        Board updateBoard = this.BoardService.update(board, modifyBoard.title, modifyBoard.content,member);
        return new RsData<>("200","게시글을 성공적으로 수정했습니다.",updateBoard);
    }

    //게시글 삭제
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{boardId}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public RsData<Void> delete(@PathVariable("boardId") Long boardId, @LoginUser Member member){
        this.BoardService.delete(boardId);
        return new RsData<>("200","게시글을 성공적으로 삭제했습니다.");
    }
}
