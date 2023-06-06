package com.bupt.ecommercestreamingsystem.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bupt.ecommercestreamingsystem.sys.entity.Comments;
import com.bupt.ecommercestreamingsystem.sys.mapper.CommentsMapper;
import com.bupt.ecommercestreamingsystem.sys.service.ICommentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jayden
 * @since 2023-05-25
 */
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements ICommentsService {

    @Override
    public void addComment(Integer userId, Integer productId, String content) {
        Comments comments = new Comments();
        comments.setUserId(userId);
        comments.setProductId(productId);
        comments.setContent(content);
        LocalDateTime localDateTime = LocalDateTime.now();
        comments.setTimestamp(localDateTime);
        this.save(comments);
    }

    @Override
    public List<?> getCommentsByProductId(Integer productId) {
        LambdaQueryWrapper<Comments> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comments::getProductId,productId);
        return this.list(wrapper);
    }
}
