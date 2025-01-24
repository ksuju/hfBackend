package com.ll.hfback.domain.group.room.controller;

import com.ll.hfback.domain.group.room.entity.Room;
import com.ll.hfback.domain.group.room.response.ResponseRoom;
import com.ll.hfback.domain.group.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/Posts")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    // 해당 게시글의 모든 모임 조회
    @GetMapping("/rooms/{festivalId}")
    public List<Room> getRooms(@PathVariable("festivalId") String festivalId) {
        List<Room> rooms = roomService.searchByFestivalId(festivalId);

        return rooms;
    }

    // 해당 게시글의 모임 상세 조회
    @GetMapping("/room/{id}")
    public Room getRoom(@PathVariable("id") Long id) {
        Room room = roomService.searchById(id);

        return room;
    }

    // 해당 게시글에 모임 생성
//    @PostMapping("/room/{festivalId}")


}
