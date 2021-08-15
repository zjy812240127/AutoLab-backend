package com.zjut.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjut.commonutils.JwtUtils;
import com.zjut.commonutils.R;
import com.zjut.commonutils.UcenterMemberOrder;
import com.zjut.eduservice.client.UserClient;
import com.zjut.eduservice.entity.EduComment;
import com.zjut.eduservice.service.EduCommentService;
import com.zjut.serviceBase.exceptionHandler.MyException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-08-06
 */
@RestController
@RequestMapping("/eduservice/comment")
public class EduCommentController {

    @Autowired
    private EduCommentService eduCommentService;

    @Autowired
    private UserClient userClient;

    @ApiOperation(value = "根据课程id分页查询课程的评论")
    @GetMapping("/findComment/{courseId}/{current}/{limit}")
    public R findComment(
            @ApiParam(name = "courseId", value = "要查询评论的课程的id", required = true)
            @PathVariable String courseId,
            @ApiParam(name = "current", value = "分页当前页", required = true)
            @PathVariable long current,
            @ApiParam(name = "limit", value = "分页查询每页记录数", required = true)
            @PathVariable long limit){

        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.orderByDesc("gmt_create");
        Page<EduComment> page = new Page<>(current,limit);
        eduCommentService.page(page,wrapper);
        long commentTotal = page.getTotal();
        List<EduComment> commentList = page.getRecords();
        boolean hasNext = page.hasNext();
        long pages = page.getPages();
        long pageCurrent = page.getCurrent();
        long size = page.getSize();
        boolean hasPrevious = page.hasPrevious();

        Map map = new HashMap();
        map.put("total",commentTotal);
        map.put("list",commentList);
        map.put("hasNext",hasNext);
        map.put("pages",pages);
        map.put("current",pageCurrent);
        map.put("size",size);
        map.put("hasPrevious",hasPrevious);
        return R.ok().data(map);

    }

    @ApiOperation(value = "根据课程id添加课程评论")
    @PostMapping("/addComment")
    public R addComment(@RequestBody EduComment eduComment, HttpServletRequest request){

        // 从header中获取token，然后解析获取用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)){
            throw new MyException(20001,"请先进行登录");
        }
        System.out.println("要添加评论的用户的id为：" + memberId);
        // 远程调用ucenter查询用户的其余信息加入到eduComment中
        UcenterMemberOrder userInfo = userClient.getUserInfo(memberId);
        String nickname = userInfo.getNickname();
        String avatar = userInfo.getAvatar();
        System.out.println("用户的头像：" + avatar + "用户的nickname：" + nickname);
        eduComment.setAvatar(avatar);
        eduComment.setMemberId(memberId);
        eduComment.setNickname(nickname);
        // 添加评论
        eduCommentService.addComment(eduComment);
        return R.ok();

    }

}

