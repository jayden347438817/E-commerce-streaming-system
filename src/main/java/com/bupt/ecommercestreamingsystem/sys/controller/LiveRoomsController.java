package com.bupt.ecommercestreamingsystem.sys.controller;

import com.bupt.ecommercestreamingsystem.common.vo.Result;
import com.bupt.ecommercestreamingsystem.sys.service.ILiveRoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jayden
 * @since 2023-05-25
 */
@RestController
@RequestMapping("/live_rooms")
public class LiveRoomsController {

    @Autowired
    private ILiveRoomsService liveRoomsService;

    // 创建直播间
    @PutMapping("")
    public Result<?> createLiveRoom(@RequestParam("ownerId") Integer ownerId,
                                    @RequestParam("name") String name,
                                    @RequestParam("description") String description){
        return Result.success(liveRoomsService.createLiveRoom(ownerId,name,description));
    }

    // 删除直播间
    @DeleteMapping("/{live_room_id}")
    public Result<?> deleteLiveRoom(@PathVariable("live_room_id") Integer liveRoomId){
        return Result.success(liveRoomsService.deleteLiveRoom(liveRoomId));
    }

    // 进入直播间
    @PostMapping("/{live_room_id}/enter")
    public Result<?> enterLiveRoom(@PathVariable("live_room_id") Integer liveRoomId,
                                   @RequestParam("user_id") Integer userId){
        return Result.success(liveRoomsService.enterLiveRoom(liveRoomId,userId));
    }

    // 退出直播间
    @PostMapping("/{live_room_id}/exit")
    public Result<?> exitLiveRoom(@PathVariable("live_room_id") Integer liveRoomId,
                                   @RequestParam("user_id") Integer userId){
        return Result.success(liveRoomsService.exitLiveRoom(liveRoomId,userId));
    }

    // （拓展）获取当前在线用户数
    @GetMapping("/{live_room_id}/users/online")
    public Result<?> getOnlineUsers(@PathVariable("live_room_id") Integer liveRoomId){
        int num = liveRoomsService.getOnlineUsers(liveRoomId);
        return Result.success(num,"获取当前在线用户数成功");
    }

    // （拓展）获取历史最大在线用户数
    @GetMapping("/{live_room_id}/users/max")
    public Result<?> getMaxOnlineUsers(@PathVariable("live_room_id") Integer liveRoomId){
        int num = liveRoomsService.getMaxOnlineUsers(liveRoomId);
        return Result.success(num,"获取历史最大在线用户数成功");
    }
}
