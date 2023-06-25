package com.bupt.ecommercestreamingsystem.sys.controller;

import com.bupt.ecommercestreamingsystem.common.vo.Result;
import com.bupt.ecommercestreamingsystem.sys.entity.Users;
import com.bupt.ecommercestreamingsystem.sys.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jayden
 * @since 2023-05-25
 */
@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private IUsersService usersService;

    // 获取用户信息
    @Cacheable(value = "user",key = "#token")
    @GetMapping("/info")
    public Result<Map<String,Object>> getUserInfo(@RequestParam("token") String token){
        // 根据token获取用户信息,redis
        Map<String,Object> data = usersService.getUserInfo(token);
        if(data != null){
            return Result.success(data,"获取用户信息成功");
        }
        return Result.fail(20003,"登录信息无效，请重新登录");
    }

    // 注销
    @CacheEvict(value = "user", key = "#token")
    @PostMapping("/logout")
    public Result<?> logout(@RequestParam("token") String token){
        usersService.logout(token);
        return Result.success(null,"退出成功");
    }

    // 登录
    @PostMapping("/login")
    public Result<Map<String,Object>> login(Users user){
        Map<String,Object> data = usersService.login(user);
        if (data != null){
            return Result.success(data,"登录成功");
        }
        return Result.fail(20002,"用户名或密码错误");
    }

    // 注册
    @PostMapping("/register")
    public Result<?> register(@RequestBody Users user){
        boolean flag = usersService.register(user);
        if(flag){
            return Result.success("注册成功");
        }
        return Result.fail(20003,"注册失败，用户可能已存在");
    }


}
