package com.bupt.ecommercestreamingsystem.sys.service;

import com.bupt.ecommercestreamingsystem.sys.entity.Comments;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jayden
 * @since 2023-05-25
 */
public interface ICommentsService extends IService<Comments> {

    void addComment(Integer userId, Integer productId, String content);

    List<?> getCommentsByProductId(Integer productId);
}
