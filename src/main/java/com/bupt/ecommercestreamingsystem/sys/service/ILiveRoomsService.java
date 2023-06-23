package com.bupt.ecommercestreamingsystem.sys.service;

import com.bupt.ecommercestreamingsystem.sys.entity.LiveRooms;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jayden
 * @since 2023-05-25
 */
public interface ILiveRoomsService extends IService<LiveRooms> {

    String createLiveRoom(Integer ownerId, String name, String description);

    String deleteLiveRoom(Integer liveRoomId);


    String enterLiveRoom(Integer liveRoomId, Integer userId);

    String exitLiveRoom(Integer liveRoomId, Integer userId);

    int getOnlineUsers(Integer liveRoomId);

    int getMaxOnlineUsers(Integer liveRoomId);
}
