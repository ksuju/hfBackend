package com.ll.hfback.domain.group.room.controller;

import com.ll.hfback.domain.group.room.response.ResponseRoom;
import com.ll.hfback.domain.group.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/{fesId}/rooms")
    public void createRoom(@PathVariable String fesId,
                           @RequestBody ResponseRoom responseRoom) {
        roomService.createRoom(fesId, responseRoom);
    }

    @GetMapping("/room")
    @ResponseBody
    public String room(){

        String room = "방";

        return "room";
    }

}
