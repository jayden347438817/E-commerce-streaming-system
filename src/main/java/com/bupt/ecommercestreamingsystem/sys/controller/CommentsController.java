package com.bupt.ecommercestreamingsystem.sys.controller;

import com.bupt.ecommercestreamingsystem.common.vo.Result;
import com.bupt.ecommercestreamingsystem.sys.service.ICommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jayden
 * @since 2023-05-25
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {
    @Autowired
    private ICommentsService commentsService;

    // 添加评论
    @PostMapping("")
    public Result<?> addComment(@RequestParam("userId") Integer userId,
                                @RequestParam("productId") Integer productId,
                                @RequestParam("content") String content){
        commentsService.addComment(userId,productId,content);
        return Result.success("评论成功");
    }

    // 获取商品评论
    @GetMapping("/{productId}")
    public Result<?> getCommentsByProductId(@PathVariable("productId") Integer productId){
        List<?> comments = commentsService.getCommentsByProductId(productId);
        if (comments == null){
            return Result.fail(20004,"该商品评论为空");
        }
        return Result.success(comments,"获取评论成功");
    }
}
