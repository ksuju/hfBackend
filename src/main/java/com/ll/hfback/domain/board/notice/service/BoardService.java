package com.ll.hfback.domain.board.notice.service;


import com.ll.hfback.domain.board.notice.entity.Board;
import com.ll.hfback.domain.board.notice.repository.BoardRepository;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.domain.member.member.service.MemberService;
import com.ll.hfback.global.exceptions.ErrorCode;
import com.ll.hfback.global.exceptions.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberService memberService;

    //게시글 목록 리스트
    public Page<Board> list(int page){
        Pageable pageable = PageRequest.of(page,10);
        return this.boardRepository.findAll(pageable);
    }

    //게시글 상세페이지
    public Board view(Long id) {
        Optional<Board> board = this.boardRepository.findById(id);
        if(board.isPresent()){
            return board.get();
        } else {
            throw new ServiceException(ErrorCode.BOARD_NOT_FOUND);
        }
    }

    //게시글 생성
    public Board create(String title, String content, Member admin){

        if (admin == null){
            throw new RuntimeException("Member not found");
        }

        Board board = new Board();
        board.setTitle(title);
        board.setContent(content);
        board.setAuthor(admin);
        Board saveBoard = this.boardRepository.save(board);
        return  saveBoard;
    }

    //게시글 수정
    public Board update(Board board, String title, String Content, Member admin){

        if (admin == null){
            throw new RuntimeException("Member not found");
        }
        /*
        Board board = new Board();
        Optional<Board> optionalBoard = this.boardRepository.findById(id);
        if(optionalBoard.isEmpty()){
            new RuntimeException("err");
        }
        Board board = optionalBoard.get();*/

        Board b = boardRepository.findById(board.getId())
                .orElseThrow(() -> new ServiceException(ErrorCode.BOARD_NOT_FOUND));

        b.setTitle(title);
        b.setContent(Content);
        b.setModifyDate(LocalDateTime.now());
        b.setAuthor(admin);

        Board saveBoard = this.boardRepository.save(b);
        return saveBoard;
    }


    //게시글 삭제
    public void delete(Long id){
        /*Optional<Board> board = this.boardRepository.findById(id);
        if(!board.isPresent()) {
            throw new ServiceException(ErrorCode.BOARD_NOT_FOUND);
        }*/
        //위 코드의 간단한 방법
        Board board = boardRepository.findById(id)
                //객체가 없을때 예외를 던져준다
                .orElseThrow(() -> new ServiceException(ErrorCode.BOARD_NOT_FOUND));
        this.boardRepository.delete(board);
        /*if(board != null){
            this.boardRepository.delete(board);
        }else {
            throw new RuntimeException("Board not found");
        }*/
    }
}
