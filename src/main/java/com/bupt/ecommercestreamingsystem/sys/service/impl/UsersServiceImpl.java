package com.bupt.ecommercestreamingsystem.sys.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bupt.ecommercestreamingsystem.sys.entity.Users;
import com.bupt.ecommercestreamingsystem.sys.mapper.UsersMapper;
import com.bupt.ecommercestreamingsystem.sys.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jayden
 * @since 2023-05-25
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {
    @Autowired
    private RedisTemplate redisTemplate;

    // 登录
    @Override
    public Map<String, Object> login(Users user) {
        // 根据用户名密码查询，不为空就生成token
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Users::getUsername,user.getUsername())
                .eq(Users::getPassword,user.getPassword());
        Users loginUser = this.baseMapper.selectOne(wrapper);
        if (loginUser != null){
            // 生成token,UUID(终极方案JWT)
            String key = "user:" + UUID.randomUUID();
            // 存入redis
            loginUser.setPassword(null);
            redisTemplate.opsForValue().set(key,loginUser,30, TimeUnit.MINUTES);// 30分钟过期
            // 返回token
            Map<String, Object> data = new HashMap<>();
            data.put("token",key);
            return data;
        }
        return null;
    }

    // 获取用户信息
    @Override
    public Map<String, Object> getUserInfo(String token) {
        // 根据token获取用户信息,redis
        Object obj = redisTemplate.opsForValue().get(token);
        if (obj != null){
            // 反序列化（用fastjson）
            Users loginUser = JSON.parseObject(JSON.toJSONString(obj),Users.class);
            Map<String, Object> data = new HashMap<>();
            data.put("id",loginUser.getId());
            data.put("username",loginUser.getUsername());
            data.put("userType",loginUser.getUserType());
            return data;
        }
        return null;
    }

    // 注销
    @Override
    public void logout(String token) {
        redisTemplate.delete(token);
    }

    // 注册
    @Override
    public boolean register(Users user) {
        //根据用户名查询
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();//条件构造器
        wrapper.eq(Users::getUsername,user.getUsername());//where username = ? （是一个条件）

        Users existUser = this.baseMapper.selectOne(wrapper);//按照wrapper条件查询user表，返回查询得到的user对象
        //结果为空，插入数据
        if (existUser == null) {
            int insert = this.baseMapper.insert(user);//插入数据，返回插入的条数
            return insert > 0;
        }
        return false;
    }
}
