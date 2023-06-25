package com.bupt.ecommercestreamingsystem.sys.service.impl;

import com.bupt.ecommercestreamingsystem.sys.entity.LiveRooms;
import com.bupt.ecommercestreamingsystem.sys.entity.Users;
import com.bupt.ecommercestreamingsystem.sys.mapper.LiveRoomsMapper;
import com.bupt.ecommercestreamingsystem.sys.service.ILiveRoomsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jayden
 * @since 2023-05-25
 */
@Service
public class LiveRoomsServiceImpl extends ServiceImpl<LiveRoomsMapper, LiveRooms> implements ILiveRoomsService {

    @Autowired
    private RedisTemplate redisTemplate;

    // 创建直播间
    @Override
    public Map<String,Object> createLiveRoom(Integer ownerId, String name, String description) {
        LiveRooms liveRooms = new LiveRooms();
        liveRooms.setOwnerId(ownerId);
        liveRooms.setName(name);
        liveRooms.setDescription(description);
        liveRooms.setTimestamp(LocalDateTime.now());
        this.save(liveRooms);
        Map<String,Object> data = new HashMap<>();
        data.put("liveRoomId",liveRooms.getId());
        data.put("ownerId",liveRooms.getOwnerId());
        data.put("name",liveRooms.getName());
        data.put("description",liveRooms.getDescription());
        data.put("timestamp",liveRooms.getTimestamp());
        return data;
    }

    // 删除直播间
    @Override
    public String deleteLiveRoom(Integer liveRoomId) {
        LiveRooms liveRooms = this.getById(liveRoomId);
        if (liveRooms != null){
            this.removeById(liveRoomId);
            return "删除直播间成功";
        }
        // 清除对应redis
        String key = "live_room_" + liveRoomId + ":";
        redisTemplate.delete(key);
        String maxkey = "liveRoom:" + liveRoomId + ":max";
        redisTemplate.delete(maxkey);

        return "直播间不存在";
    }

    // 进入直播间
    @Override
    public String enterLiveRoom(Integer liveRoomId, Integer userId) {
        // 判断直播间是否存在
        LiveRooms liveRooms = this.getById(liveRoomId);
        if (liveRooms == null){
            return "直播间不存在";
        }

        // 判断用户是否已经进入过直播间，利用redis
        String key= "live_room_" + liveRoomId+":";
        Boolean isMember = redisTemplate.opsForSet().isMember(key, userId);
        if (isMember){
            return "用户已经进入过直播间";
        }

        // 未进入就存到redis中直播间信息里（存集合）
        redisTemplate.opsForSet().add(key,userId);

        // 利用redis获取直播间在线人数
        int count = redisTemplate.opsForSet().size(key).intValue();

        // 获取历史最大人数
        String maxKey = "liveRoom:" + liveRoomId + ":max";
        Integer maxCount = (Integer) redisTemplate.opsForValue().get(maxKey);
        if (maxCount == null || count > maxCount){
            redisTemplate.opsForValue().set(maxKey, count);
            maxCount = count;
        }

        return "进入直播间成功";
    }

    // 退出直播间
    @Override
    public String exitLiveRoom(Integer liveRoomId, Integer userId) {
        // 判断直播间是否存在
        LiveRooms liveRooms = this.getById(liveRoomId);
        if (liveRooms == null){
            return "直播间不存在";
        }

        // 判断用户是否已经进入过直播间，利用redis
        String key= "live_room_" + liveRoomId+":";
        Boolean isMember = redisTemplate.opsForSet().isMember(key, userId);
        if (!isMember){
            return "用户未进入过直播间";
        }

        // 进入就从redis中直播间信息里删除，退出直播间
        redisTemplate.opsForSet().remove(key,userId);

        return "退出直播间成功";
    }

    // 获取在线用户数量
    @Override
    public int getOnlineUsers(Integer liveRoomId) {
        // 判断直播间是否存在
        LiveRooms liveRooms = this.getById(liveRoomId);
        if (liveRooms == null){
            return -1;
        }

        // 利用redis获取直播间在线人数
        int count = redisTemplate.opsForSet().size("live_room_" + liveRoomId + ":").intValue();

        // 获取历史最大人数
        String maxKey = "liveRoom:" + liveRoomId + ":max";
        Integer maxCount = (Integer) redisTemplate.opsForValue().get(maxKey);
        if (maxCount == null || count > maxCount){
            redisTemplate.opsForValue().set(maxKey, count);
            maxCount = count;
        }

        return count;
    }

    // 获取历史最大在线人数
    @Override
    public int getMaxOnlineUsers(Integer liveRoomId) {
        String maxKey = "liveRoom:" + liveRoomId + ":max";
        Integer maxCount = (Integer) redisTemplate.opsForValue().get(maxKey);
        if (maxCount == null){
            return -1;
        }
        return maxCount;
    }
}
