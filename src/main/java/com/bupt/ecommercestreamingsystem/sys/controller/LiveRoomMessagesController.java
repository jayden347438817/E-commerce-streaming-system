package com.bupt.ecommercestreamingsystem.sys.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jayden
 * @since 2023-05-25
 */
@Controller
@RequestMapping("/liveRoomMessages")
public class LiveRoomMessagesController {
    @GetMapping("/1")
    public String getLiveRoomMessages(){
        return "chat";
    }

}
