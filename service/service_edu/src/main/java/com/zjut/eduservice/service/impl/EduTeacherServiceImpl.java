package com.zjut.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjut.eduservice.entity.EduCourse;
import com.zjut.eduservice.entity.EduTeacher;
import com.zjut.eduservice.mapper.EduTeacherMapper;
import com.zjut.eduservice.service.EduCourseService;
import com.zjut.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-06-27
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, Object> pageListWeb(Page<EduTeacher> pageTeacher) {

        // 根据sort字段给名师排序
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        // 升序排顺序，来的早的资历越老排前面
        wrapper.orderByAsc("sort");
        // 根据条件重新查该分页信息
        baseMapper.selectPage(pageTeacher,wrapper);
        List<EduTeacher> records = pageTeacher.getRecords();
        long total = pageTeacher.getTotal();
        long current = pageTeacher.getCurrent();
        long size = pageTeacher.getSize();
        long pages = pageTeacher.getPages();
        boolean hasNext = pageTeacher.hasNext();
        boolean hasPrevious = pageTeacher.hasPrevious();
        Map<String, Object> map = new HashMap<>();
        map.put("items",records);
        map.put("total",total);
        map.put("current",current);
        map.put("size",size);
        map.put("pages",pages);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);
        return map;

    }

    @Override
    public List<EduTeacher> getOlds() {

        // 先去缓存中找
        List<EduTeacher> teacherList = (List<EduTeacher>) redisTemplate.opsForValue().get("rTeacherList");
        if (teacherList != null){
            return teacherList;
        }else {
            List<EduTeacher> list = baseMapper.getTeacherList();
            redisTemplate.opsForValue().set("rTeacherList",list);
            return list;
        }



    }


}
