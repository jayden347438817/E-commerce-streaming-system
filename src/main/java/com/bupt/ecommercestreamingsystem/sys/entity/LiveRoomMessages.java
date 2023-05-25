package com.bupt.ecommercestreamingsystem.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Jayden
 * @since 2023-05-25
 */
@TableName("live_room_messages")
public class LiveRoomMessages implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer liveRoomId;

    private Integer userId;

    private String message;

    private LocalDateTime timestamp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getLiveRoomId() {
        return liveRoomId;
    }

    public void setLiveRoomId(Integer liveRoomId) {
        this.liveRoomId = liveRoomId;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "LiveRoomMessages{" +
            "id=" + id +
            ", liveRoomId=" + liveRoomId +
            ", userId=" + userId +
            ", message=" + message +
            ", timestamp=" + timestamp +
        "}";
    }
}
