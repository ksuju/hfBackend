package com.ll.hfback.domain.group.room.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RoomController {

    @GetMapping("/room")
    @ResponseBody
    public String room(){

        String room = "방";

        return "room";
    }

}
