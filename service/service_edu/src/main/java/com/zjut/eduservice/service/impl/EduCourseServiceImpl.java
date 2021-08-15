package com.zjut.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjut.eduservice.entity.*;
import com.zjut.eduservice.entity.vo.AddCourseVo;
import com.zjut.eduservice.entity.vo.CoursePublishVo;
import com.zjut.eduservice.entity.vo.CourseQueryVo;
import com.zjut.eduservice.entity.vo.CourseWebVo;
import com.zjut.eduservice.mapper.EduCourseMapper;
import com.zjut.eduservice.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjut.serviceBase.exceptionHandler.MyException;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-07-02
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    @Autowired
    private EduSubjectService eduSubjectService;
    @Autowired
    private EduChapterService eduChapterService;
    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private RedisTemplate redisTemplate;



    @Override
    public String addCourse(AddCourseVo addCourseVo) {

        // 将数据加入course表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(addCourseVo,eduCourse);
        // baseMapper就是该类对应的dao的mapper
        int insertCourse = baseMapper.insert(eduCourse);
        if(insertCourse <= 0){
            throw new MyException(20001,"course表添加失败");
        }

        // 两表主键关联
        String cId = eduCourse.getId();
        //将数据加入description表
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(addCourseVo.getDescription());
        eduCourseDescription.setId(cId);
        final boolean save = eduCourseDescriptionService.save(eduCourseDescription);
        if (save == false){
            throw new MyException(20001,"添加课程描述表失败");
        }

        return cId;

    }

    @Override
    public AddCourseVo findById(String id) {

        // 根据id查询course表信息
        final EduCourse eduCourse = baseMapper.selectById(id);
        // 查询description表信息(由course的id作为id)
        final EduCourseDescription courseDescription = eduCourseDescriptionService.getById(id);
        final AddCourseVo courseVo = new AddCourseVo();

        // 封装属性
        BeanUtils.copyProperties(eduCourse,courseVo);
        courseVo.setDescription(courseDescription.getDescription());

        return courseVo;

    }


    @Override
    public void updateCourse(AddCourseVo courseVo) {

        final EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseVo,eduCourse);
        final int update = baseMapper.update(eduCourse, null);
        if(update <= 0){
            throw new MyException(20001,"修改course表失败");
        }

        final EduCourseDescription eduCourseDescription = new EduCourseDescription();

        eduCourseDescription.setId(courseVo.getId());
        eduCourseDescription.setDescription(courseVo.getDescription());
        eduCourseDescriptionService.updateById(eduCourseDescription);

    }

    @Override
    public CoursePublishVo getPublishInfo(String id) {

        // 调用mapper接口的方法
        final CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);

        return publishCourseInfo;
    }

    @Override
    public void deleteCourse(String id) {

        // 删除小节
        eduVideoService.removeByCourseId(id);

        // 删除章节
        eduChapterService.remodeByCourseId(id);

        // 删除描述
        eduCourseDescriptionService.removeById(id);

        // 删除课程（逻辑删除）
        baseMapper.deleteById(id);

    }

    @Override
    public CourseWebVo getCourseInfo(String courseId) {
        // 调用更新浏览数的方法
        updatePageViewCount(courseId);
        CourseWebVo courseWebVo = baseMapper.getCourseInfoById(courseId);
        return courseWebVo;

    }

    @Override
    public void updatePageViewCount(String id) {
        // 先根据id获取课程信息
        EduCourse eduCourse = baseMapper.selectById(id);
        // 该课程的浏览次数加一
        eduCourse.setViewCount(eduCourse.getViewCount() + 1);
        // 更新信息
        baseMapper.updateById(eduCourse);
    }

    @Override
    public List<EduCourse> getListByTeacherId(String id) {

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",id);
        wrapper.orderByDesc("gmt_modified");
        List<EduCourse> courseList = baseMapper.selectList(wrapper);
        return courseList;
    }

    @Override
    public Map<String, Object> getCourseList(Page<EduCourse> coursePage, CourseQueryVo courseQueryVo) {
        // 判断前端是否有传入参数的值，进行条件查询
        System.out.println("******************************");
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(courseQueryVo.getSubjectParentId())){
            wrapper.eq("subject_parent_id",courseQueryVo.getSubjectParentId());
        }

        if(!StringUtils.isEmpty(courseQueryVo.getSubjectId())){
            wrapper.eq("subject_id",courseQueryVo.getSubjectId());
        }

        if(!StringUtils.isEmpty(courseQueryVo.getPriceSort())){
            wrapper.eq("price",courseQueryVo.getPriceSort());
        }

        if(!StringUtils.isEmpty(courseQueryVo.getGmtCreateSort())){
            wrapper.eq("gmt_create",courseQueryVo.getGmtCreateSort());
        }

        if(!StringUtils.isEmpty(courseQueryVo.getBuyCountSort())){
            wrapper.eq("buy_count",courseQueryVo.getBuyCountSort());
        }

        wrapper.orderByDesc("gmt_create");

        // 按照controller传入的分页方式进行查询数据的整理（page,limit）
        try{
            System.out.println("-----------------------------------");
            baseMapper.selectPage(coursePage,wrapper);

        }catch (Exception e){
            e.printStackTrace();
        }

        long total = coursePage.getTotal();
        List<EduCourse> records = coursePage.getRecords();
        long current = coursePage.getCurrent();
        long size = coursePage.getSize();
        long pages = coursePage.getPages();
        boolean hasPrevious = coursePage.hasPrevious();
        boolean hasNext = coursePage.hasNext();

        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("items",records);
        map.put("current",current);
        map.put("size",size);
        map.put("pages",pages);
        map.put("hasPrevious",hasPrevious);
        map.put("hasNext",hasNext);
        return map;



    }

    @Override
    public List<EduCourse> getHot() {


        List<EduCourse> redisList = (List<EduCourse>) redisTemplate.opsForValue().get("rCourseList");
        if (redisList !=null){
            // 缓存中有数据直接拿

            System.out.println("直接从缓存中拿热点课程数据");
            return redisList;

        }else {
            List<EduCourse> list = baseMapper.getHotCourses();
            // 将热点数据加入redis，下次查询时直接从缓存找
            redisTemplate.opsForValue().set("rCourseList",list);
            System.out.println("将热点课程信息加入缓存===================");
            return list;
        }

    }


}
